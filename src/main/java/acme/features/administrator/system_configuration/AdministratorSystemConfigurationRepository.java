
package acme.features.administrator.system_configuration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.datatypes.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("select ad from Administrator ad where ad.id = :id")
	Administrator findOneAdministratorById(int id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
