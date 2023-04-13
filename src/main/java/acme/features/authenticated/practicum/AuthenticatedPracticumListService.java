
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository pracRepository;


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
		Collection<Practicum> objects;
		int id;
		id = super.getRequest().getData("id", int.class);
		objects = this.pracRepository.findPracticaByCourseId(id);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title");

		super.getResponse().setData(tuple);
	}
}
