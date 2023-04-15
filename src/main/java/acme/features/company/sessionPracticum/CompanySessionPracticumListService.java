
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumListService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		//		boolean status;
		//
		//		status = super.getRequest().hasData("practicumId", int.class);

		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		//		boolean status;
		//		int practicumId;
		//		Practicum practicum;
		//
		//		practicumId = super.getRequest().getData("practicumId", int.class);
		//		practicum = this.repository.findOnePracticumById(practicumId);
		//		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<SessionPracticum> objects;
		int practicumId;

		practicumId = super.getRequest().getData("practicumId", int.class);
		objects = this.repository.findSessionPracticumsByPracticumId(practicumId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSessionPracticum", "practicum.title");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<SessionPracticum> objects) {
		assert objects != null;
		int practicumId;
		Practicum practicum;
		boolean showCreate;
		practicumId = super.getRequest().getData("practicumId", int.class);
		practicum = this.repository.findOnePracticumById(practicumId);
		showCreate = practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setGlobal("practicumId", practicumId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}

}
