package alphabank.test.services;

import alphabank.test.model.Exchange;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ExchangeService {
    public static URI getExchangeURI(String exServer, String exchangeAppID, String curBase, LocalDateTime date) {
        StringBuilder builderURL = new StringBuilder();
        if (date.toLocalDate().isEqual(LocalDate.now())) {
            builderURL
                    .append(exServer)
                    .append("latest.json?app_id=")
                    .append(exchangeAppID)
                    .append("&base=")
                    .append(curBase.toUpperCase());
            return URI.create(builderURL.toString());
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        builderURL
                .append(exServer)
                .append("historical/")
                .append(timeFormatter.format(date))
                .append(".json?app_id=")
                .append(exchangeAppID)
                .append("&base=")
                .append(curBase.toUpperCase());
        return URI.create(builderURL.toString());
    }
    public static boolean compareExchangeRate(Exchange exchOne, Exchange exchTwo, String currency) {
        return exchOne.getRates().get(currency.toUpperCase()) > exchTwo.getRates().get(currency.toUpperCase());
    }
}
