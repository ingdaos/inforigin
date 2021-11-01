package co.com.ml.inforigin.controller;

import co.com.ml.inforigin.utilities.Paths;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ingda
 */
@RestController
@RequestMapping(Paths.UTILITIES)
public class UtilitiesController {

    @GetMapping(Paths.DATE)
    public Date getDate() {
        return new Date();
    }
}
