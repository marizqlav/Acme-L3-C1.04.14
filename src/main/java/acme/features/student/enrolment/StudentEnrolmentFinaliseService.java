/*
 * StudentEnrolmentShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Student student;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findOneEnrolmentById(enrolmentId);
		student = enrolment == null ? null : enrolment.getStudent();
		status = enrolment != null && enrolment.isDraftMode() && super.getRequest().getPrincipal().hasRole(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "lowerNibble", "holderName");

	}
	public static boolean esNumeroTarjetaValido(final String numeroTarjetaBase) {
		final String numeroTarjeta = numeroTarjetaBase.replace(" ", "").replace("-", "");
		if (numeroTarjeta == null || numeroTarjeta.isEmpty())
			return false;
		if (!numeroTarjeta.matches("\\d+"))
			return false;
		int suma = 0;
		boolean alternar = false;
		for (int i = numeroTarjeta.length() - 1; i >= 0; i--) {
			int digito = numeroTarjeta.charAt(i) - '0';
			if (alternar) {
				digito *= 2;
				if (digito > 9)
					digito = digito % 10 + 1;
			}
			suma += digito;
			alternar = !alternar;
		}
		return suma % 10 == 0;
	}

	public static boolean checkDateFormat(final String input) {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
		sdf.setLenient(false);
		try {
			sdf.parse(input);
			return true;
		} catch (final ParseException e) {
			return false;
		}
	}

	public static boolean isFutureDate(final String input) {
		final SimpleDateFormat sdfNew = new SimpleDateFormat("MM/yy");
		sdfNew.setLenient(false);
		try {
			return MomentHelper.isPresentOrFuture(sdfNew.parse(input));
		} catch (final Exception e) {
			return false;
		}
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
		if (object.getHolderName() == null || object.getHolderName().isEmpty())
			super.state(false, "holderName", "student.enrolment.error.holder.name.empty");

		if (object.getLowerNibble() == null || object.getLowerNibble().isEmpty())
			super.state(false, "lowerNibble", "student.enrolment.error.credit.card.number.empty");
		else if (object.getLowerNibble().replace(" ", "").replace("-", "").length() != 16 || !StudentEnrolmentFinaliseService.esNumeroTarjetaValido(object.getLowerNibble()))
			super.state(false, "lowerNibble", "student.enrolment.error.credit.card.number.not.valid");

		if (super.getRequest().getData("expireDate", String.class) == null || super.getRequest().getData("expireDate", String.class).isEmpty())
			super.state(false, "expireDate", "student.enrolment.error.expire.date.empty");
		else if (!StudentEnrolmentFinaliseService.checkDateFormat(super.getRequest().getData("expireDate", String.class)))
			super.state(false, "expireDate", "student.enrolment.error.expire.date.not.valid");
		else if (!StudentEnrolmentFinaliseService.isFutureDate(super.getRequest().getData("expireDate", String.class)))
			super.state(false, "expireDate", "student.enrolment.error.expire.date.expired");

		if (super.getRequest().getData("cvc", String.class) == null || super.getRequest().getData("cvc", String.class).isEmpty())
			super.state(false, "cvc", "student.enrolment.error.cvc.empty");
		else if (super.getRequest().getData("cvc", String.class).length() != 3 || !super.getRequest().getData("cvc", String.class).matches("\\d{3}"))
			super.state(false, "cvc", "student.enrolment.error.cvc.not.valid");

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);
		object.setLowerNibble(object.getLowerNibble().replace(" ", "").replace("-", "").substring(12));

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;
		final String expireDate = "";
		final String cvc = "";
		tuple = super.unbind(object, "lowerNibble", "holderName");
		tuple.put("expireDate", expireDate);
		tuple.put("cvc", cvc);

		super.getResponse().setData(tuple);
	}
}
