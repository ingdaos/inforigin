package co.com.ml.inforigin.client.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ingda
 */
@Data
@ToString
public class RestCountriesInfoFeignDto implements Serializable {

    private int status = 200;
    private String name;
    private String capital;
    private List<RestCountriesCurrenciesDto> currencies;

}
