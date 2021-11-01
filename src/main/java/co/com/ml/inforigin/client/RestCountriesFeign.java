package co.com.ml.inforigin.client;

import co.com.ml.inforigin.client.dto.RestCountriesInfoFeignDto;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author ingda
 */
@FeignClient(name = "RestCountriesFeign", url = "127.0.0.1")
public interface RestCountriesFeign {

    @GetMapping
    public RestCountriesInfoFeignDto getInfo(URI uri);
    
}
