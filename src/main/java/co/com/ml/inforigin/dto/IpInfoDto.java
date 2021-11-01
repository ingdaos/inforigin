package co.com.ml.inforigin.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ingda
 */
@Data
@ToString
public class IpInfoDto implements Serializable {

    private String countryCode;
    private String countryName;
    private String currencyCode;
    private String baseRate;
    private Double rate;

}
