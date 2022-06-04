package alphabank.test.controllers;

import alphabank.test.feign.ExchangeFeign;
import alphabank.test.feign.GifFeign;
import alphabank.test.model.Data;
import alphabank.test.model.Exchange;
import alphabank.test.model.Gif;
import alphabank.test.services.ExchangeService;
import alphabank.test.services.GifService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static alphabank.test.services.ExchangeService.getExchangeURI;
import static alphabank.test.services.GifService.getGifURI;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ExchangeControllerTest {
    @Value("${exchange.server}") private String exServer;
    @Value("${exchange.app_id}") private String exchangeAppID;
    @Value("${exchange.base}") private String curBase;
    @Value("${gif.server}") private String gifServer;
    @Value("${gif.app_id}") private String gifApiID;
    @Value("${gif.rich}") private String rich;
    @Value("${gif.broken}") private String broken;

    Map<String, Double> exMap = new HashMap<>();
    {
        exMap.put("GBP", 1.77);
        exMap.put("RUB", 77.6);
    }
    Exchange exchange = new Exchange("disclaimer", "license", "USD", exMap);
    Exchange exchange1 = new Exchange("disclaimer", "license", "USD", exMap);

    Data testData = new Data(
            "gg85uBnucTSnyBDsAb",
            "love and hip hop money GIF by VH1",
            "g",
            "https://giphy.com/embed/gg85uBnucTSnyBDsAb",
            "https://giphy.com/gifs/vh1-gg85uBnucTSnyBDsAb"
    );
    Gif testGif = new Gif(testData);

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeFeign exchangeFeign;
    @MockBean
    private GifFeign gifFeign;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    @BeforeEach
    void beforeEach() {
        URI exToday = getExchangeURI(exServer, exchangeAppID, curBase, LocalDateTime.now());
        URI exYest = getExchangeURI(exServer,exchangeAppID,curBase, LocalDateTime.now().minusDays(1));
        URI gifURI = getGifURI(gifServer, gifApiID, broken);

        when(exchangeFeign.getExchange(exToday)).thenReturn(exchange);
        when(exchangeFeign.getExchange(exYest)).thenReturn(exchange1);
        when(gifFeign.getGif(gifURI)).thenReturn(testGif);
    }

    @Test
    void getGif() throws Exception {
        perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testGif)));
    }

    @Test
    void getGifAsJSON() throws Exception {
        perform(MockMvcRequestBuilders.get("/gbp"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(testGif)));
    }
}
