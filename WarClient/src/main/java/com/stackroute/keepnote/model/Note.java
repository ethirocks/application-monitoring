package com.stackroute.keepnote.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * The class "Note" will be acting as the data model for the note Table in the database. Please
 * note that this class is annotated with @Entity annotation. Hibernate will scan all package for
 * any Java objects annotated with the @Entity annotation. If it finds any, then it will begin the
 * process of looking through that particular Java object to recreate it as a table in your database.
 */
@Entity
@Table(name = "Note")
public class Note {

	@Id @Column(name = "Id")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
			int noteId;

	@Column(name = "TITLE")
	String noteTitle;

	@Column(name = "CONTENT")
	String noteContent;

	@Column(name = "STATUS")
	String noteStatus;

	@Column(name = "DATE")
	LocalDateTime localDate;


	public Note() {

	}

	public Note(int i, String title, String content, String status, LocalDateTime localDate) {
		this.noteId = i;
		this.noteTitle = title;
		this.noteContent = content;
		this.noteStatus = status;
		this.localDate = localDate;
	}

	public int getNoteId() {

		return noteId;
	}

	public String getNoteTitle() {

		return noteTitle;
	}

	public String getNoteContent() {

		return noteContent;
	}

	public String getNoteStatus() {

		return noteStatus;
	}

	public LocalDateTime getLocalDate() {

		return localDate;
	}

	public void setNoteId(int parseInt) {
		this.noteId = parseInt;

	}

	public void setNoteTitle(String parameter) {
		this.noteTitle = parameter;

	}

	public void setNoteContent(String parameter) {
		this.noteContent = parameter;
	}

	public void setNoteStatus(String parameter) {
		this.noteStatus = parameter;
	}

	public void setCreatedAt(LocalDateTime now) {
		this.localDate = now;
	}

	@Override
	public String toString(){
		return "Note id = " + noteId + ", content" + noteContent + ", status" + noteStatus + ", title" + noteTitle;
	}

}