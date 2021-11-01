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
public class IpStatusDto implements Serializable{

    private String ipAddress;
    private String status;

}
