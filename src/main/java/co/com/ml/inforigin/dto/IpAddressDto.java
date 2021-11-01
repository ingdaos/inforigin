package co.com.ml.inforigin.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author ingda
 */
@Data
@ToString
public class IpAddressDto implements Serializable {

    @NotEmpty
    private String ipAddress;

}
