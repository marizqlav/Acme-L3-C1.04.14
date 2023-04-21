package acme.features.authenticated.audit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditListByCourseService extends AbstractService<Authenticated, Audit> {

    @Autowired
    AuthenticatedAuditRepository repo;

    @Override
    public void check() {
      super.getResponse().setChecked(true);
    }

    @Override
    public void authorise() {
      super.getResponse().setAuthorised(true);
    }

    @Override
    public void load() {
      List<Audit> audits = new ArrayList<>();

      if (super.getRequest().hasData("course")) {
        final Integer courseId = super.getRequest().getData("course", int.class);
  
        audits = this.repo.findAllAuditsByCourse(courseId);
      } else {
        audits = this.repo.findAllAuditsByCourse(repo.findAllCourses().get(0).getId());
      }

      super.getBuffer().setData(audits);
    }

    @Override
    public void unbind(final Audit audit) {
      assert audit != null;

      Tuple tuple;

      tuple = super.unbind(audit, "code", "conclusion", "strongPoints", "weakPoints");

      if (!audit.getDraftMode()) {
        tuple.put("draftMode", "XXXXXX");
      } else {
        tuple.put("draftMode", "");
      }

      super.getResponse().setData(tuple);
    }

    @Override
    public void unbind(final Collection<Audit> audits) {
      assert audits != null;

      List<Course> courses = repo.findAllCourses();

      Course course = courses.get(0);
      if (super.getRequest().hasData("course")) {
        course = repo.findCourse(super.getRequest().getData("course", int.class));
      }

      SelectChoices choices = SelectChoices.from(courses, "title", course);

      super.getResponse().setGlobal("course", choices.getSelected().getKey());
      super.getResponse().setGlobal("courses", choices);

    }

}
