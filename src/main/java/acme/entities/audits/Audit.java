
package acme.entities.audits;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import acme.entities.audits.AuditingRecords;

@Entity
@Getter
@Setter
public class Audit extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Column(unique = true)
	String	code;

	@Size(max = 101)
	@NotBlank
	String	conclusion;

	@Size(max = 101)
	@NotBlank
	String	strongPoints;

	@Size(max = 101)
	@NotBlank
	String	weakPoints;
	
	Boolean draftMode = true;

	//Relation with course
	
	@OneToMany(mappedBy = "audit")
	private List<AuditingRecords> auditingRecords;

	@Transient
	public String mark() {
		Map<String, Long> marks = auditingRecords.stream()
			.map(x -> x.mark)
			.collect(Collectors.groupingBy(x -> x, Collectors.counting()));
		Long max = marks.values().stream().max(Long::compare).get();
		List<String> finalMarks = marks.entrySet().stream()
			.filter(x -> x.getValue() != max)
			.map(x -> x.getKey())
			.collect(Collectors.toList());
		Random random = new Random();
	    Integer r = random.ints(0, finalMarks.size())
	      .findFirst()
	      .getAsInt();
	    return finalMarks.get(r);
	}
}
