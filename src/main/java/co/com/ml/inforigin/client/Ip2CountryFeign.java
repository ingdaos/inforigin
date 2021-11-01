package co.com.ml.inforigin.client;

import co.com.ml.inforigin.client.dto.IpCountryInfoFeignDto;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ingda
 */
@FeignClient(name = "Ip2CountryFeign", url = "127.0.0.1")
public interface Ip2CountryFeign {

    @GetMapping
    public IpCountryInfoFeignDto getInfo(URI uri, @RequestParam("access_key") String accessKey);

}
