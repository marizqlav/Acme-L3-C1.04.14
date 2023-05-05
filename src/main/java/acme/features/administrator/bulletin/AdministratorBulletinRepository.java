
package acme.features.administrator.bulletin;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletin.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBulletinRepository extends AbstractRepository {

	@Query("SELECT b FROM Bulletin b")
	List<Bulletin> findAllBulletins();

	@Query("SELECT b FROM Bulletin b WHERE b.id =:id")
	Bulletin findBulletinById(Integer id);
}
