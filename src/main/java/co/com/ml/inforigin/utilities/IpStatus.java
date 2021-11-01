package co.com.ml.inforigin.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Estados permitidos de las IP en blacklist
 *
 * @author ingda
 */
@AllArgsConstructor
public enum IpStatus {

    ALLOWED("ALLOWED"),
    BANNED("BANNED");

    @Getter
    private final String value;

}
