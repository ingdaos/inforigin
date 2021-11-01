package co.com.ml.inforigin.service.cached;

import co.com.ml.inforigin.client.Ip2CountryFeign;
import co.com.ml.inforigin.client.dto.IpCountryInfoFeignDto;
import java.net.URI;
import java.net.URISyntaxException;
import javax.naming.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Clase encargada de gestionar la comunicación para obtener información del
 * país a partir de una dirección IP. Los servicios almacenan resultados en caché
 * para evitar el uso excesivo de las API. el tiempo de la información
 * almacenada en caché es configurable y administrada por
 * {@link FlushCacheService}
 *
 * @author ingda
 */
@Slf4j
@Service
public class IpInfoCachedService {

    private static final String CACHE_PREFIX = "IpInfo_";

    @Value("${ipapi.access.key}")
    private String accessKey;

    @Value("${ipapi.url}")
    private String ipapiUrl;

    @Autowired
    private Ip2CountryFeign ip2ConutryFeign;

    /**
     * Método encargado de elaborar la llave para almacenar datos en caché
     *
     * @param ip
     * @return
     */
    public static String getCacheKey(String ip) {
        return CACHE_PREFIX.concat(ip);
    }

    /**
     * Obtiene código país por IP. Prefijo en memoria: CountryByIP::IpInfo_
     *
     * @param ip
     * @return
     * @throws javax.naming.ConfigurationException
     */
    @Cacheable(cacheNames = "CountryByIP",
            key = "T(co.com.ml.inforigin.service.cached.IpInfoCachedService).getCacheKey(#ip)",
            unless = "#result != null && #result.success != null &&  #result.success == false")
    public IpCountryInfoFeignDto getCountryByIP(String ip) throws ConfigurationException {
        log.debug("Obteniendo información país ip2Country {}", ip);
        URI uri = getUriIpCountry(ip);
        return ip2ConutryFeign.getInfo(uri, accessKey);
    }

    /**
     * Método encargado de borrar todas las entradas en caché de información
     * país por IP
     */
    @CacheEvict(allEntries = true, value = "CountryByIP")
    public void evictCountriesByIp() {
        log.info("Borrando caché CountryByIP");
    }

    /**
     * URI para obtener información del país por IP
     *
     * @param ip
     * @return
     */
    private URI getUriIpCountry(String ip) throws ConfigurationException {
        URI uri = null;
        try {
            String url = ipapiUrl.concat(ip);
            uri = new URI(url);
        } catch (URISyntaxException ex) {
            log.error("Error obteniendo uri CountryByIP", ex);
            throw new ConfigurationException("Please check ip2Country URL");
        }
        return uri;
    }

}
