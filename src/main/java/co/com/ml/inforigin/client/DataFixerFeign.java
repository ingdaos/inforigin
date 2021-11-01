package co.com.ml.inforigin.client;

import co.com.ml.inforigin.client.dto.FixerRatesDto;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ingda
 */
@FeignClient(name = "DataFixerFeign", url = "127.0.0.1")
public interface DataFixerFeign {

    @GetMapping
    public FixerRatesDto getRates(URI uri, @RequestParam("access_key") String accessKey, @RequestParam("symbols") String currencyCodes);

}
