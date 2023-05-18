
package acme.features.assistant.sessionTutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantSessionTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("SELECT s FROM SessionTutorial s WHERE s.id = :id")
	SessionTutorial findSessionById(int id);

	@Query("SELECT s FROM SessionTutorial s WHERE s.tutorial.id = :id")
	Collection<SessionTutorial> findSessionsOfTutorial(int id);

	@Query("SELECT s FROM SessionTutorial s")
	Collection<SessionTutorial> findAllSessions();

	/*
	 * @Query("SELECT s FROM SessionTutorial s WHERE s.draftMode = true")
	 * List<SessionTutorial> findSessionsOnDraftMode();
	 * 
	 * @Query("SELECT s FROM SessionTutorial s WHERE s.draftMode = false")
	 * List<SessionTutorial> findPublishedSessions();
	 */
}
