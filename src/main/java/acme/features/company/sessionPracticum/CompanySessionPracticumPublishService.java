
package acme.features.company.sessionPracticum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.sessionPracticum.SessionPracticum;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanySessionPracticumPublishService extends AbstractService<Company, SessionPracticum> {

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
		SessionPracticum object;
		Principal principal;
		int sessionPracticumId;

		sessionPracticumId = super.getRequest().getData("id", int.class);
		object = this.repository.findSessionPracticumById(sessionPracticumId);
		principal = super.getRequest().getPrincipal();

		status = object.getPracticum().getCompany().getId() == principal.getActiveRoleId();

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
	public void bind(final SessionPracticum object) {
		assert object != null;
		super.bind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
	}

	@Override
	public void validate(final SessionPracticum object) {
		assert object != null;
	}

	@Override
	public void perform(final SessionPracticum object) {
		assert object != null;

		object.getPracticum().setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final SessionPracticum object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "title", "abstractSessionPracticum", "startDate", "finishDate", "link");
		tuple.put("practicumId", super.getRequest().getData("practicumId", int.class));
		tuple.put("draftMode", object.getPracticum().getDraftMode());
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}

}
