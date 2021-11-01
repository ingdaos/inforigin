package co.com.ml.inforigin.dto;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author ingda
 */
@Data
public class ResponseDto implements Serializable {

    private Object result;
    private boolean error;
    private String errorDescription;

}
