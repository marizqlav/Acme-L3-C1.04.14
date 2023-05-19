/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.rate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.components.Rate;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyRateRepository extends AbstractRepository {

	@Query("select r from Rate r where r.currency1 = :currency1 and r.currency2 = :currency2")
	Rate findRate(String currency1, String currency2);
}
