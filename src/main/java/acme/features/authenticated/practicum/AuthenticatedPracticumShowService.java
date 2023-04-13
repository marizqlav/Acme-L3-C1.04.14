
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository pracRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.pracRepository.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {

		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "abstractPracticum", "someGoals");
		tuple.put("company", object.getCompany().getName());

		super.getResponse().setData(tuple);

	}

}
