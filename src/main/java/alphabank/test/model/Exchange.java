package alphabank.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class Exchange {
    private String disclaimer;
    private String license;
    private String base;
    private Map<String, Double> rates;

}
