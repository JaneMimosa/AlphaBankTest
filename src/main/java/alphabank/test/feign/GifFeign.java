package alphabank.test.feign;

import alphabank.test.model.Gif;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "Gif", url = "https://api.giphy.com/")
public interface GifFeign {
    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Gif getGif(URI baseUrl);
}
