
package acme.features.company.sessionPracticum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicum.Practicum;
import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumShowService extends AbstractService<Company, SessionPracticum> {

	@Autowired
	protected CompanySessionPracticumRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int sessionPracticumId;
		Practicum practicum;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticumBySessionPracticumId(sessionPracticumId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionPracticum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;
		final Collection<Practicum> practica;
		final SelectChoices choices;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		practica = this.repository.findManyPracticumByCompanyId(companyId);
		choices = SelectChoices.from(practica, "code", object.getPracticum());
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractSessionPracticum", "startDate", "finishDate");

		tuple.put("addendum", object.isAddendum());
		tuple.put("draftMode", object.getPracticum().isDraftMode());
		tuple.put("draftModeSession", object.isDraftModeSession());
		tuple.put("practicum", choices.getSelected().getKey());
		tuple.put("practica", choices);

		final int selectedId = Integer.parseInt(choices.getSelected().getKey());
		final Practicum selectedPracticum = this.repository.findPracticumById(selectedId);

		tuple.put("practicum.code", selectedPracticum.getCode());

		super.getResponse().setData(tuple);
	}

}
