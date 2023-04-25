
package acme.features.company.companyDashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyDashboardRepository extends AbstractRepository {

	//	@Query("select c from Company c where c.userAccount.id = :userAccountId")
	//	Company findCompanyByUserAccountId(int userAccountId);
	//
	//	@Query("select count(sp) from SessionPracticum sp where sp.practicum.company.id = :companyId")
	//	Integer findCountSession(int companyId);
	//
	//	@Query("select avg(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId")
	//	Double findAverageSessionLength(int companyId);
	//
	//	@Query("select stddev(datediff(sp.finishDate,sp.startDate)) from SessionPracticum  sp where sp.practicum.company.id = :companyId")
	//	Double findDeviationSessionLength(int companyId);
	//
	//	@Query("select min(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId")
	//	Double findMinimumSessionLength(int companyId);
	//
	//	@Query("select max(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId")
	//	Double findMaximumSessionLength(int companyId);
	//
	//	@Query("select avg((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId and sp.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	//	Double findAveragePracticaLength(int companyId);
	//
	//	@Query("select stddev((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId and sp.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	//	Double findDeviationPracticaLength(int companyId);
	//
	//	@Query("select min((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId and sp.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	//	Double findMinimumPracticaLength(int companyId);
	//
	//	@Query("select max((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = :companyId and sp.practicum.id = p.id)) from Practicum p where p.company.id = :companyId")
	//	Double findMaximumPracticaLength(int companyId);
	//
	//	@Query("select count(p) from Practicum p where p.company.id = :companyId")
	//	Integer findCountPractica(int companyId);
	//
	//	@Query("SELECT FUNCTION('MONTH', sp.startDate), COUNT(sp) FROM SessionPracticum sp WHERE sp.practicum.company.id = :companyId GROUP BY FUNCTION('MONTH', sp.startDate) ORDER BY COUNT(sp) DESC")
	//	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);

	@Query("select c from Company c where c.userAccount.id = ?1")
	Company findCompanyByUserAccountId(int userAccountId);

	@Query("select count(sp) from SessionPracticum sp where sp.practicum.company.id = ?1")
	int findCountSession(int companyId);

	@Query("select COALESCE(avg(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findAverageSessionLength(int companyId);

	@Query("select COALESCE(stddev(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findDeviationSessionLength(int companyId);

	@Query("select COALESCE(min(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findMinimumSessionLength(int companyId);

	@Query("select COALESCE(max(datediff(sp.finishDate,sp.startDate)), 0.0) from SessionPracticum sp where sp.practicum.company.id = ?1")
	double findMaximumSessionLength(int companyId);

	@Query("select COALESCE(avg((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findAveragePracticaLength(int companyId);

	@Query("select COALESCE(stddev((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findDeviationPracticaLength(int companyId);

	@Query("select COALESCE(min((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findMinimumPracticaLength(int companyId);

	@Query("select COALESCE(max((select sum(datediff(sp.finishDate,sp.startDate)) from SessionPracticum sp where sp.practicum.company.id = ?1 and sp.practicum.id = p.id)), 0.0) from Practicum p where p.company.id = ?1")
	double findMaximumPracticaLength(int companyId);

	@Query("select count(p) from Practicum p where p.company.id = ?1")
	int findCountPractica(int companyId);

	@Query("SELECT FUNCTION('MONTH', sp.startDate), COUNT(sp) FROM SessionPracticum sp WHERE sp.practicum.company.id = ?1 GROUP BY FUNCTION('MONTH', sp.startDate) ORDER BY COUNT(sp) DESC")
	List<Object[]> findTotalNumberOfPracticaByMonth(int companyId);
}
