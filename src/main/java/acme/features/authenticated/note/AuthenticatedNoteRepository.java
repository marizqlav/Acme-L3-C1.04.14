
package acme.features.authenticated.note;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.notes.Note;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select n from Note n where n.instantiationMoment > :monthAgo")
	Collection<Note> findRecentNotes(Date monthAgo);

	@Query("select n from Note n")
	Collection<Note> findAllNotes();

	@Query("select n from Note n where n.id = :id")
	Note findNoteById(Integer id);

}
