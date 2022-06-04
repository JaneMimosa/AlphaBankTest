package alphabank.test.feign;

import alphabank.test.model.Exchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "ExchangeCourse", url = "https://openexchangerates.org/api/")
public interface ExchangeFeign {
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Exchange getExchange(URI baseUrl);
}
