package com.stackroute.keepnote.controller;

import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */
@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 *
	 * 1. display the list of existing notes from the persistence data. Each note
	 * should contain Note Id, title, content, status and created date.
	 * 2. Add a new note which should contain the note id, title, content and status.
	 * 3. Delete an existing note
	 * 4. Update an existing note
	 *
	 */

	/*
	 * Autowiring should be implemented for the NoteDAO.
	 * Create a Note object.
	 *
	 */
	@Autowired
	private NoteDAO noteDAO;

	public NoteController(NoteDAO noteDAO){
		this.noteDAO = noteDAO;
	}

	/*
	 * Define a handler method to read the existing notes from the database and add
	 * it to the ModelMap which is an implementation of Map, used when building
	 * model data for use with views. it should map to the default URL i.e. "/index"
	 */
	@RequestMapping(value = "/")
	public String getAllNotes(ModelMap model){
		model.addAllAttributes(noteDAO.getAllNotes());
		return "index";
	}

	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing messages. Hence, reading
	 * note has to be done here again and the retrieved notes object should be sent
	 * back to the view using ModelMap This handler method should map to the URL
	 * "/add".
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addValues(@RequestParam("noteTitle") String noteTitle, @RequestParam("noteContent") String noteContent,
							@RequestParam("noteStatus") String noteStatus){

		Note note = new Note();

		note.setNoteContent(noteContent);
		note.setNoteStatus(noteStatus);
		note.setNoteTitle(noteTitle);
		note.setCreatedAt(LocalDateTime.now());
		if(noteDAO.saveNote(note) == true){
			return "redirect:/";
		}else {
			return "index";
		}
	}

	/*
	 * Define a handler method which will read the NoteId from request parameters
	 * and remove an existing note by calling the deleteNote() method of the
	 * NoteRepository class.This handler method should map to the URL "/delete".
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("noteId")int noteId){
		if(noteDAO.deleteNote(noteId)){
			return "redirect:/";
		}
		return "index";
	}

	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/update".
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ModelMap model, @RequestParam("noteId")int NoteId, @RequestParam("noteTitle") String NoteTitle,
						 @RequestParam("noteContent") String NoteContent, @RequestParam("noteStatus") String NoteStatus){
		Note note = new Note(NoteId, NoteTitle, NoteContent, NoteStatus, LocalDateTime.now());
		noteDAO.UpdateNote(note);
		model.addAttribute("list", noteDAO.getAllNotes());
		return "redirect:/";
	}

}