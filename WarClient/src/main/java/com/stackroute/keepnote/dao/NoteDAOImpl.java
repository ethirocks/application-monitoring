package com.stackroute.keepnote.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.stackroute.keepnote.model.Note;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database
 * 					transaction. The database transaction happens inside the scope of a persistence
 * 					context.
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	//@Autowired
	//private SessionFactory sessionFactory;
	@PersistenceContext
	private EntityManager sessionFactory;
	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */

	public NoteDAOImpl(EntityManager sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {
		//Session session = sessionFactory.;
		//session.save(note);
		//session.flush();
		sessionFactory.persist(note);
		sessionFactory.flush();
		return true;
	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		//Session session = sessionFactory.getCurrentSession();
		//session.delete(getNoteById(noteId));
		//session.flush();
		sessionFactory.remove(getNoteById(noteId));
		sessionFactory.flush();
		return true;
	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		//String hql = "FROM Note ORDER BY DATE DESC";
		//Session session = sessionFactory.getCurrentSession();
		//Query query = session.createQuery(hql);
		//List<Note> results = query.list();
		//session.flush();
		return sessionFactory.createQuery("FROM Note ORDER BY DATE DESC").getResultList();
		//return results;

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId) {
		//String hql = "FROM Note WHERE Id = " + noteId;
		//Session session = sessionFactory.getCurrentSession();
		//Query query = session.createQuery(hql);
		List<Note> answer = sessionFactory.createQuery("FROM Note WHERE Id = " + noteId).getResultList();
		return answer.get(0);

	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
	    /*String hql = "UPDATE Note set TITLE = :notetitle, CONTENT = :content, STATUS=:status, DATE=:date WHERE Id = " +    note.getNoteId();
	    Session session = sessionFactory.getCurrentSession();
	    Query query = session.createQuery(hql);
        query.setParameter("notetitle", note.getNoteTitle());
        query.setParameter("content", note.getNoteContent());
        query.setParameter("status", note.getNoteStatus());
	    query.setParameter("date", note.getLocalDate());*/
		sessionFactory.merge(note);
		return true;
	}

}
