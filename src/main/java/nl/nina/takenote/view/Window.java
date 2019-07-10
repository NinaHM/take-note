package nl.nina.takenote.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.annotation.PostConstruct;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import nl.nina.takenote.model.Note;

public class Window extends JFrame {

	private static final long serialVersionUID = 2L;

	private JList<Note> noteJList;
	private DefaultListModel<Note> noteListModel;
	private JPanel mainPanel, textPanel, searchPanel;
	private JMenuBar menuBar;
	private JMenu file, edit, window;
	private JMenuItem delete, newFile, save, exit, cut, copy, paste;
	private JLabel searchLabel;
	private JScrollPane scrollPaneText, scrollPaneList;
	private JTextPane textPane;
	private JTextArea titleTextField;
	private JTextField searchTextField;
	private JButton searchButton, clearButton;
	
	@PostConstruct
	private void init() {
		createFrame();
		addMainPanel();
		addMenu();
		addSearchBar();
		addNoteList();
		addTextPanel();
		setVisible(true);
	}

	private void createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		setTitle("Take Note");
		setLocationRelativeTo(null);
	}

	private void addMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 5));
		add(mainPanel);
	}
	
	private void addMenu() {
		file = new JMenu("File");
		edit = new JMenu("Edit");
		window = new JMenu("Window");

		newFile = new JMenuItem("New");
		save = new JMenuItem("Save");
		delete = new JMenuItem("Delete");
		exit = new JMenuItem("Exit");
		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");

		menuBar = new JMenuBar();

		file.add(newFile);
		file.add(save);
		file.add(delete);
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		window.add(exit);

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(window);

		setJMenuBar(menuBar);
	}
	
	private void addSearchBar() {
		searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		searchLabel = new JLabel("Search:");
		searchPanel.add(searchLabel);
		
		searchTextField = new JTextField(30);
		searchPanel.add(searchTextField);

		searchButton = new JButton("Search");
		searchPanel.add(searchButton);
		
		clearButton = new JButton("Clear");
		searchPanel.add(clearButton);

		mainPanel.add(searchPanel, BorderLayout.PAGE_START);
	}

	private void addNoteList() {
		noteListModel = new DefaultListModel<Note>();
		noteJList = new JList<Note>(noteListModel);
		noteJList.setFixedCellHeight(30);
		noteJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPaneList = new JScrollPane(noteJList);
		scrollPaneList.setPreferredSize(new Dimension(200, 200));
		scrollPaneList.setBorder(new EmptyBorder(0, 10, 0, 0));
		mainPanel.add(scrollPaneList, BorderLayout.LINE_START);
	}
	
	private void addTextPanel() {
		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout(5, 5));
		createTitleTextArea();
		createTextPane();
		mainPanel.add(textPanel, BorderLayout.CENTER);
	}
	
	private void createTextPane() {
		textPane = new JTextPane();
		textPane.setMargin(new Insets(10, 10, 10, 10));
		scrollPaneText = new JScrollPane(textPane);
		textPanel.add(scrollPaneText, BorderLayout.CENTER);
	}
	
	private void createTitleTextArea() {
		titleTextField = new JTextArea("Enter title", 1, 1);
		titleTextField.setMargin(new Insets(10, 10, 10, 10));
		titleTextField.setFont(new Font("Open Sans", Font.BOLD, 15));
		textPanel.add(titleTextField, BorderLayout.PAGE_START);
	}

	public void addNote(Note note) {
		if(note != null) {
			noteListModel.addElement(note);
			notesAvailable(true);
		}
	}

	public void addNote(int index, Note note) {
		if(note != null) {
			noteListModel.add(index, note);
			noteJList.setSelectedValue(note, true);
			notesAvailable(true);
		}
	}
	
	public void deleteNote(Note note) {
		if(note != null) {
			noteListModel.removeElement(note);
			noteJList.setSelectedIndex(0);
			if(noteListModel.isEmpty()) {
				notesAvailable(false);
			}
		}
	}
	
	public void addText(String title, String content) {
		titleTextField.setText(title);
		textPane.setText(content);
	}
	
	public void notesAvailable(boolean available) {
		if(!available) {
			titleTextField.setText("");
			textPane.setText("No notes");
		}
		titleTextField.setEditable(available);
		textPane.setEditable(available);
	}

	public JList<Note> getNoteJList() {
		return noteJList;
	}

	public DefaultListModel<Note> getNoteListModel() {
		return noteListModel;
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public JMenuItem getNewFile() {
		return newFile;
	}

	public JMenuItem getSave() {
		return save;
	}

	public JMenuItem getExit() {
		return exit;
	}

	public JMenuItem getCut() {
		return cut;
	}

	public JMenuItem getCopy() {
		return copy;
	}

	public JMenuItem getPaste() {
		return paste;
	}

	public JTextPane getTextPane() {
		return textPane;
	}

	public JTextArea getTitleTextField() {
		return titleTextField;
	}

	public JTextField getSearchTextField() {
		return searchTextField;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JButton getClearButton() {
		return clearButton;
	}
}
	

