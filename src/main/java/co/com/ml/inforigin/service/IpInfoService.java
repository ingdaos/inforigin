package co.com.ml.inforigin.service;

import co.com.ml.inforigin.client.dto.FixerRatesDto;
import co.com.ml.inforigin.client.dto.IpCountryInfoFeignDto;
import co.com.ml.inforigin.client.dto.RestCountriesInfoFeignDto;
import co.com.ml.inforigin.dto.IpInfoDto;
import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.exception.InfoNotFoundException;
import co.com.ml.inforigin.service.cached.CountryInfoCachedService;
import co.com.ml.inforigin.service.cached.DataFixerCachedService;
import co.com.ml.inforigin.service.cached.IpInfoCachedService;
import java.util.Objects;
import javax.naming.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ingda
 */
@Slf4j
@Service
public class IpInfoService {

    @Autowired
    private IpInfoCachedService infoCachedService;

    @Autowired
    private CountryInfoCachedService countryInfoCachedService;

    @Autowired
    private DataFixerCachedService dataFixerCachedService;

    /**
     * Método encargado de obtener información a partir de una dirección IP
     *
     * @param ip
     * @return
     * @throws ConfigurationException
     * @throws InfoNotFoundException
     */
    public ResponseDto getIpInfo(String ip) throws ConfigurationException, InfoNotFoundException {
        ResponseDto resp = new ResponseDto();
        IpInfoDto info = new IpInfoDto();

        //Obtener código ISO del país a partir de la IP
        IpCountryInfoFeignDto countryFromIP = infoCachedService.getCountryByIP(ip);
        if (Objects.isNull(countryFromIP) || !countryFromIP.isSuccess()) {
            throw new InfoNotFoundException("No se logró ubicar dirección IP " + ip);
        }
        String countryCode = countryFromIP.getCountry_code();

        //Obtener código moneda del país
        RestCountriesInfoFeignDto country = countryInfoCachedService.getCountryByCode(countryCode);
        String currencyCode = Objects.nonNull(country.getCurrencies()) ? country.getCurrencies().get(0).getCode() : null;
        if (Objects.isNull(currencyCode)) {
            throw new InfoNotFoundException("No se logró obtener moneda del pais " + countryCode);
        }

        //Obtener tasa cambio, por deecto euros
        FixerRatesDto rates = dataFixerCachedService.getRatesByCurrencyCode(currencyCode);
        String base = Objects.nonNull(rates) && rates.isSuccess() ? rates.getBase() : null;
        Double rate = null;
        if (Objects.nonNull(rates) && Objects.nonNull(rates.getRates()) && rates.getRates().containsKey(currencyCode)) {
            rate = rates.getRates().get(currencyCode);
        }

        info.setCountryCode(countryCode);
        info.setCountryName(country.getName());
        info.setCurrencyCode(currencyCode);
        info.setBaseRate(base);
        info.setRate(rate);

        resp.setResult(info);
        return resp;
    }

}
