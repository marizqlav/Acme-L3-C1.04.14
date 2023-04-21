
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanySessionPracticumRepository extends AbstractRepository {

	@Query("select p from Practicum p where p.id = :companyId")
	Practicum findPracticumById(int companyId);

	@Query("select sp from SessionPracticum sp where sp.id = :id")
	SessionPracticum findSessionPracticumById(int id);

	@Query("select sp.practicum from SessionPracticum sp where sp.id = :id")
	Practicum findPracticumBySessionPracticumId(int id);

	@Query("select sp from SessionPracticum sp where sp.practicum.id = :practicumId")
	Collection<SessionPracticum> findSessionPracticumsByPracticumId(int practicumId);

	@Query("select sp from SessionPracticum sp where sp.practicum.company.id = :id")
	Collection<SessionPracticum> findSessionPracticumByCompanyId(int id);

	@Query("select sp from SessionPracticum sp where sp.practicum.id = :id and sp.addendum = true")
	Collection<SessionPracticum> findAddendumSessionPracticumByPracticumId(int id);

	@Query("select p from Practicum p where p.company.id = :id and p.draftMode = true")
	Collection<Practicum> findManyPrivatePracticumByCompanyId(int id);

	@Query("select p from Practicum p where p.company.id = :id and p.draftMode = false")
	Collection<Practicum> findManyPublishedPracticumByCompanyId(int id);

	@Query("select c from Company c where c.id = :id")
	Company findCompanyById(int id);

	@Query("select p from Practicum p")
	Collection<Practicum> findAllPracticum();

	@Query("select p from Practicum p where p.company.id = :id")
	Collection<Practicum> findManyPracticumByCompanyId(int id);
}
