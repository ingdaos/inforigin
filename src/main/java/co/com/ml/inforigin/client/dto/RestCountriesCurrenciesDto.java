package co.com.ml.inforigin.client.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ingda
 */
@Data
@ToString
public class RestCountriesCurrenciesDto implements Serializable {

    private String code;
    private String name;
    private String symbol;

}
