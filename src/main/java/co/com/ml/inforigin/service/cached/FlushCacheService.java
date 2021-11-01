package co.com.ml.inforigin.service.cached;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Tareas programadas para limpiar memoria caché según configuración
 *
 * @author ingda
 */
@Service
public class FlushCacheService {

    @Autowired
    private DataFixerCachedService dataFixerCachedService;

    @Autowired
    private IpInfoCachedService ipInfoCachedService;

    @Autowired
    private CountryInfoCachedService countryInfoCachedService;

    @Scheduled(fixedDelayString = "${fixerio.evic.ms}", initialDelayString = "${fixerio.evic.ms}")
    public void flushRatesByCurrencyCode() {
        dataFixerCachedService.evictRatesByCurrencyCode();
    }

    @Scheduled(fixedDelayString = "${ipapi.evict.ms}", initialDelayString = "${ipapi.evict.ms}")
    public void evictCountriesByIp() {
        ipInfoCachedService.evictCountriesByIp();
    }

    @Scheduled(fixedDelayString = "${restcountries.evict.ms}", initialDelayString = "${restcountries.evict.ms}")
    public void evictCountriesByCode() {
        countryInfoCachedService.evictCountriesByCode();
    }

}
