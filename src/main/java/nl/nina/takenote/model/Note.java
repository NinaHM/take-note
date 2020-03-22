package nl.nina.takenote.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class Note implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;
	private String content;
	
	public Note() {
		this("Enter title", "Enter text");
	}
	
	public Note(String name, String content) {
		this.title = name;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return title;
	}
}
