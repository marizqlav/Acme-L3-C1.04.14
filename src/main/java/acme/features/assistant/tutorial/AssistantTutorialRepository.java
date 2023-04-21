
package acme.features.assistant.tutorial;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.SessionTutorial;
import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.draftMode = 1")
	List<Tutorial> findTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :assistant_id")
	List<Tutorial> findTutorialsOfAssistant(int assistant_id);

	@Query("SELECT a FROM Assistant a WHERE a.id = :id")
	Assistant findAssistantById(int id);

	@Query("SELECT s FROM SessionTutorial s WHERE s.tutorial = :tutorial")
	List<SessionTutorial> findSessionTutorialsOfTutorial(Tutorial tutorial);

	Tutorial findFirstByOrderByCodeDesc();
}
