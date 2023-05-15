
package acme.entities.audits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditService extends AbstractService<Auditor, Audit> {

	@Autowired
	AuditRepository repo;

	/*
	 * public String getAuditMark(final Audit audit) {
	 * final Map<String, Long> marks = this.repo.getAuditingRecords(audit).stream().map(x -> x.mark).collect(Collectors.groupingBy(x -> x, Collectors.counting()));
	 * 
	 * final Long max = marks.values().stream().max(Long::compare).get();
	 * 
	 * final List<String> finalMarks = marks.entrySet().stream().filter(x -> x.getValue() != max).map(x -> x.getKey()).collect(Collectors.toList());
	 * 
	 * final Random random = new Random();
	 * final Integer r = random.ints(0, finalMarks.size()).findFirst().getAsInt();
	 * return finalMarks.get(r);
	 * }
	 */
}
