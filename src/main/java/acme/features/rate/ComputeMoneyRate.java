
package acme.features.rate;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import acme.components.ExchangeRate;
import acme.components.Rate;
import acme.forms.MoneyExchange;
import acme.framework.components.datatypes.Money;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.StringHelper;

@Component("ComputeMoneyRate")
public class ComputeMoneyRate {

	@Autowired
	protected AnyRateRepository repository;


	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange result;
		RestTemplate api;
		ExchangeRate record;
		String sourceCurrency;
		Date finalDate;
		Double sourceAmount, targetAmount, rate;
		Money target;

		try {
			api = new RestTemplate();

			sourceCurrency = source.getCurrency();
			sourceAmount = source.getAmount();
			final Rate localRate = this.repository.findRate(sourceCurrency, targetCurrency);
			if (localRate != null && !MomentHelper.isLongEnough(localRate.getDate(), MomentHelper.getCurrentMoment(), 24, ChronoUnit.HOURS)) {
				rate = localRate.getRate();
				targetAmount = rate * sourceAmount;
				finalDate = localRate.getDate();
			} else if (localRate != null) {

				record = api.getForObject( //
					"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
					ExchangeRate.class, //
					sourceCurrency, //
					targetCurrency //
				);
				localRate.setCurrency1(sourceCurrency);
				localRate.setCurrency2(targetCurrency);
				localRate.setRate(record.getRates().get(targetCurrency));
				localRate.setDate(MomentHelper.getCurrentMoment());

				this.repository.save(localRate);

				assert record != null;
				rate = record.getRates().get(targetCurrency);
				targetAmount = rate * sourceAmount;
				finalDate = record.getDate();
			} else {
				record = api.getForObject( //
					"https://api.exchangerate.host/latest?base={0}&symbols={1}", //
					ExchangeRate.class, //
					sourceCurrency, //
					targetCurrency //
				);
				final Rate newLocalRate = new Rate();
				newLocalRate.setCurrency1(sourceCurrency);
				newLocalRate.setCurrency2(targetCurrency);
				newLocalRate.setRate(record.getRates().get(targetCurrency));
				newLocalRate.setDate(MomentHelper.getCurrentMoment());

				this.repository.save(newLocalRate);

				assert record != null;
				rate = record.getRates().get(targetCurrency);
				targetAmount = rate * sourceAmount;
				finalDate = record.getDate();
			}

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			result = new MoneyExchange();
			result.setSource(source);
			result.setTargetCurrency(targetCurrency);
			result.setDate(finalDate);
			result.setTarget(target);
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}
}
