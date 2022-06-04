package alphabank.test.controllers;

import alphabank.test.feign.ExchangeFeign;
import alphabank.test.feign.GifFeign;
import alphabank.test.model.Exchange;
import alphabank.test.model.Gif;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static alphabank.test.services.ExchangeService.compareExchangeRate;
import static alphabank.test.services.ExchangeService.getExchangeURI;
import static alphabank.test.services.GifService.getGifURI;

@AllArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ExchangeController {

    @Value("${exchange.server}") private String exServer;
    @Value("${exchange.app_id}") private String exchangeAppID;
    @Value("${exchange.base}") private String curBase;
    @Value("${gif.server}") private String gifServer;
    @Value("${gif.app_id}") private String gifApiID;
    @Value("${gif.rich}") private String rich;
    @Value("${gif.broken}") private String broken;

    private final ExchangeFeign exchangeFeign;
    private final GifFeign gifFeign;
    @GetMapping
    public Gif getGif(){
        return getGifAsJSON("RUB");
    }

    @GetMapping("/{currency}")
    public Gif getGifAsJSON(@PathVariable String currency) {

        Exchange exchangeToday = exchangeFeign.getExchange(getExchangeURI(exServer, exchangeAppID, curBase, LocalDateTime.now()));
        Exchange exchangeYesterday = exchangeFeign.getExchange(getExchangeURI(exServer,exchangeAppID,curBase,LocalDateTime.now().minusDays(1)));
        String tag = getGifTag(compareExchangeRate(exchangeToday,exchangeYesterday,currency));
        Gif gif = gifFeign.getGif(getGifURI(gifServer, gifApiID, tag));
        return gif;
    }

    public String getGifTag(boolean b){
        return b?rich:broken;
    }
}
