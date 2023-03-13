
package acme.entities.bulletin;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.data.AbstractEntity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BulletinRepository extends AbstractRepository {

	@Override
	@Query("SELECT b FROM Bulletin b")
	List<AbstractEntity> findAll();

	@Query("SELECT b FROM Bulletin b WHERE b.id = :bulletinId")
	Bulletin findById(int bulletinId);

}
