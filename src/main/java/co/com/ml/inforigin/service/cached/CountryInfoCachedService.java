package co.com.ml.inforigin.service.cached;

import co.com.ml.inforigin.client.RestCountriesFeign;
import co.com.ml.inforigin.client.dto.RestCountriesInfoFeignDto;
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
 * Clase encargada de gestionar la comunicación para obtener información de un
 * país en especifico. Los servicios almacenan resultados en caché para evitar
 * el uso excesivo de las API. el tiempo de la información almacenada en caché
 * es configurable y administrada por {@link FlushCacheService}
 *
 * @author ingda
 */
@Slf4j
@Service
public class CountryInfoCachedService {

    private static final String CACHE_PREFIX = "CountryInfo_";

    @Value("${restcountries.url}")
    private String restcountriesUrl;

    @Autowired
    private RestCountriesFeign restCountriesFeign;

    /**
     * Método encargado de elaborar la llave para almacenar datos en caché
     *
     * @param code
     * @return
     */
    public static String getCacheKey(String code) {
        return CACHE_PREFIX.concat(code);
    }

    /**
     * Obtiene información de una país por su código ISO. Prefijo en memoria:
     * CountryByCode::CountryInfo_
     *
     * @param code
     * @return
     * @throws ConfigurationException
     */
    @Cacheable(cacheNames = "CountryByCode",
            key = "T(co.com.ml.inforigin.service.cached.CountryInfoCachedService).getCacheKey(#code)",
            unless = "#result != null && #result.status != null &&  #result.status != 200")
    public RestCountriesInfoFeignDto getCountryByCode(String code) throws ConfigurationException {
        log.debug("Obteniendo información país por código {}", code);
        URI uri = getUriCountryByCode(code);
        return restCountriesFeign.getInfo(uri);
    }

    /**
     * Método encargado de borrar todas las entradas en caché de información
     * país por código ISO
     */
    @CacheEvict(allEntries = true, value = "CountryByCode")
    public void evictCountriesByCode() {
        log.info("Borrando caché CountryByCode");
    }

    /**
     * URI para obtener información del país por código
     *
     * @param ip
     * @return
     */
    private URI getUriCountryByCode(String code) throws ConfigurationException {
        URI uri = null;
        try {
            String url = restcountriesUrl.concat(code);
            uri = new URI(url);
        } catch (URISyntaxException ex) {
            log.error("Error obteniendo uri CountryByCode", ex);
            throw new ConfigurationException("Please check restcountries URL");
        }
        return uri;
    }

}
