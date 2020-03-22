package nl.nina.takenote.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import nl.nina.takenote.model.Note;
import nl.nina.takenote.view.Window;

public class Controller {

	private static final String FILE_NAME = "notes.dat";
	private Window window;
	private List<Note> notes;
	private Note selectedNote;

	@Autowired
	private ObjectFactory<Note> noteFactory;

	public Controller(Window window) {
		this.window = window;
	}

	@PostConstruct
	private void init() {
		notes = new ArrayList<Note>();
		readFile();
		addActionListeners();
	}

	private void addActionListeners() {
		// List of notes
		window.getNoteJList().addListSelectionListener(e -> listListener(e));
		window.getNoteJList().setSelectedIndex(0);

		// File menu items
		window.getNewFile().addActionListener(e -> createNote());
		window.getDelete().addActionListener(e -> deleteNote());
		window.getSave().addActionListener(e -> save());

		// Edit menu items
		window.getCut().addActionListener(e -> window.getTextPane().cut());
		window.getCopy().addActionListener(e -> window.getTextPane().copy());
		window.getPaste().addActionListener(e -> window.getTextPane().paste());

		// Window menu items
		window.getExit().addActionListener(e -> System.exit(0));

		// Search bar Buttons
		window.getClearButton().addActionListener(e -> clearSearchBar());
		window.getSearchButton().addActionListener(e -> search());
	}

	private void listListener(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (selectedNote != null) {
				saveNote(selectedNote);
			}
			
			Note note = window.getNoteJList().getSelectedValue();
			if (note != null) {
				selectedNote = note;
				window.addText(note.getTitle(), note.getContent());
				window.getTextPane().setCaretPosition(0);
			}
		}
	}

	private void createNote() {
		Note note = noteFactory.getObject();
		notes.add(0, note);
		window.addNote(0, note);
	}

	private void deleteNote() {
		Note noteToDelete = window.getNoteJList().getSelectedValue();
		if (noteToDelete != null) {
			notes.remove(noteToDelete);
			window.deleteNote(noteToDelete);
		}
	}

	private void search() {
		resetJList();

		String toFind = window.getSearchTextField().getText().toLowerCase();
		boolean found = false;
		for (Note note : notes) {
			String title = note.getTitle().toLowerCase();
			String content = note.getContent().toLowerCase();
			if (!(title.contains(toFind)) && !(content.contains(toFind))) {
				window.getNoteListModel().removeElement(note);
			} else {
				found = true;
			}
		}

		if (found) {
			window.getNoteJList().setSelectedIndex(0);
		} else {
			selectedNote = null;
			window.notesAvailable(false);
		}
	}

	private void clearSearchBar() {
		window.getSearchTextField().setText("");
		resetJList();
	}

	private void resetJList() {
		window.getNoteListModel().clear();

		for (Note note : notes) {
			window.addNote(note);
		}
		window.getNoteJList().setSelectedIndex(0);
	}

	private void save() {
		saveNote();
		saveFile();
	}

	private void saveNote() {
		Note note = window.getNoteJList().getSelectedValue();
		saveNote(note);
	}

	private void saveNote(Note note) {
		if (note != null) {
			note.setTitle(window.getTitleTextField().getText());
			note.setContent(window.getTextPane().getText());
		}
	}

	private void saveFile() {
		try (ObjectOutputStream noteFile = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(FILE_NAME)))) {
			for (Note note : notes) {
				noteFile.writeObject(note);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readFile() {
		File file = new File(FILE_NAME);
		if (file.exists()) {
			try (ObjectInputStream noteFile = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(FILE_NAME)))) {
				boolean eof = false;
				while (!eof) {
					try {
						Note note = (Note) noteFile.readObject();
						notes.add(note);
						window.addNote(note);
					} catch (EOFException e) {
						eof = true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
