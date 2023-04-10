
package acme.components;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
