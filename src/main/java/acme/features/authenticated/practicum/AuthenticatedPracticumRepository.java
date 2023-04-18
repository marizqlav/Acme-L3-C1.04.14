
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.course.id = :id and p.draftMode = false")
	Collection<Practicum> findPracticasByCourseId(int id);

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

}
