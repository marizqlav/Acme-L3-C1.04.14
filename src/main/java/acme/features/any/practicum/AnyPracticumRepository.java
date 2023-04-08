
package acme.features.any.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p left join fetch p.course r where p.draftMode = true")
	Collection<Practicum> findAllPracticumWithCourse();

	@Query("select p from Practicum p where p.id = :id")
	Practicum findPracticumById(int id);

	@Query("select c from Company c")
	Practicum findCompanyById(int id);

}
