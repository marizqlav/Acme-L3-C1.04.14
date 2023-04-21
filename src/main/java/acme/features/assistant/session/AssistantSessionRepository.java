
package acme.features.assistant.session;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.SessionTutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantSessionRepository extends AbstractRepository {

	@Query("SELECT s FROM SessionTutorial s WHERE s.id = :id")
	SessionTutorial findSessionById(int id);

	@Query("SELECT s FROM SessionTutorial s")
	List<SessionTutorial> findSessions();
}
