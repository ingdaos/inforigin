package co.com.ml.inforigin.service.cached;

import co.com.ml.inforigin.client.DataFixerFeign;
import co.com.ml.inforigin.client.dto.FixerRatesDto;
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
 * Clase encargada de realizar comunicación con FIXER Foreign exchange rates and
 * currency conversion JSON API. Los servicios almacenan resultados en caché
 * para evitar el uso excesivo de las API. el tiempo de la información
 * almacenada en caché es configurable y administrada por
 * {@link FlushCacheService}
 *
 * @author ingda
 */
@Slf4j
@Service
public class DataFixerCachedService {

    private static final String CACHE_PREFIX = "DataFixer_";

    @Value("${fixerio.access.key}")
    private String accessKey;

    @Value("${fixerio.url}")
    private String fixerioUrl;

    @Autowired
    private DataFixerFeign dataFixerFeign;

    /**
     * Método encargado de elaborar la llave para almacenar datos en caché fixer
     *
     * @param currencyCode
     * @return
     */
    public static String getCacheKey(String currencyCode) {
        return CACHE_PREFIX.concat(currencyCode);
    }

    /**
     * Obtiene tasa de cambio por código moneda. Prefijo en memoria:
     * RatesByCurrencyCode::DataFixer_
     *
     * @param currencyCode
     * @return
     * @throws ConfigurationException
     */
    @Cacheable(cacheNames = "RatesByCurrencyCode",
            key = "T(co.com.ml.inforigin.service.cached.DataFixerCachedService).getCacheKey(#currencyCode)",
            unless = "#result == null || #result.success == false")
    public FixerRatesDto getRatesByCurrencyCode(String currencyCode) throws ConfigurationException {
        log.debug("Obteniendo información moneda {}", currencyCode);
        URI uri = getUriRatesByCurrencyCode();
        return dataFixerFeign.getRates(uri, accessKey, currencyCode);
    }

    /**
     * Método encargado de borrar todas las entradas en caché de información
     * monedas por país
     */
    @CacheEvict(allEntries = true, value = "RatesByCurrencyCode")
    public void evictRatesByCurrencyCode() {
        log.info("Borrando caché RatesByCurrencyCode");
    }

    /**
     * URI para obtener información monedas por país
     *
     * @param ip
     * @return
     */
    private URI getUriRatesByCurrencyCode() throws ConfigurationException {
        try {
            return new URI(fixerioUrl);
        } catch (URISyntaxException ex) {
            log.error("Error obteniendo uri RatesByCurrencyCode", ex);
            throw new ConfigurationException("Please check fixerio URL");
        }
    }

}
