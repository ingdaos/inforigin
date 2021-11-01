package co.com.ml.inforigin.client.dto;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author ingda
 */
@Data
public class IpCountryInfoFeignDto implements Serializable {

    private boolean success = true;
    private String ip;
    private String type;
    private String continent_code;
    private String continent_name;
    private String country_code;
    private String country_name;
    private String region_code;
    private String region_name;
    private String city;
    private String zip;
    private Double latitude;
    private Double longitude;

}
