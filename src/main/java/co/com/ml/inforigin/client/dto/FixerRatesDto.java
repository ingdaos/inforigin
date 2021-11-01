package co.com.ml.inforigin.client.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ingda
 */
@Data
@ToString
public class FixerRatesDto implements Serializable {

    private boolean success;
    private String base;
    private Map<String, Double> rates;

}
