
package acme.features.administrator.banner;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerRepository extends AbstractRepository {


	@Query("SELECT count(b) FROM Banner b")
	Integer countBanners();

	@Query("SELECT b FROM Banner b")
	List<Banner> findManyBanners(PageRequest pageRequest);

	default Banner findRandomBanner() {
		Banner res = null;		
		Integer count = this.countBanners();

		if (count == 0) {
			return res;
		}

		for (Integer i = 0; i < count; i += 10) {
			ThreadLocalRandom random = ThreadLocalRandom.current();
			Integer r = random.nextInt(0, count);

			PageRequest page = PageRequest.of((int) (Math.floor(r / 10) * 10), 10);

			List<Banner> pageBanners = this.findManyBanners(page).stream()
				.filter(x -> x.isDisplayActive())
				.collect(Collectors.toList());

			if (!pageBanners.isEmpty()) {
				ThreadLocalRandom random2 = ThreadLocalRandom.current();
				Integer n = random2.nextInt(0, pageBanners.size());
	
				res = pageBanners.get(n);
				return res;
			}
		}

		return res;
	}
}
