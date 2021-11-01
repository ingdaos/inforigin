package co.com.ml.inforigin.controller;

import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.exception.InfoNotFoundException;
import co.com.ml.inforigin.service.IpInfoService;
import co.com.ml.inforigin.utilities.Paths;
import javax.naming.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ingda
 */
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(Paths.IP_INFO)
public class IpInfoController {

    @Autowired
    private IpInfoService ipInfoService;

    /**
     * Método encargado de obtener información por dirección IP
     *
     * @param ip
     * @return
     * @throws javax.naming.ConfigurationException
     * @throws co.com.ml.inforigin.exception.InfoNotFoundException
     */
    @GetMapping
    public ResponseEntity<ResponseDto> ipInfo(@RequestParam String ip) throws ConfigurationException, InfoNotFoundException {
        log.info("getIpInfo -> {}", ip);
        return new ResponseEntity<>(ipInfoService.getIpInfo(ip), HttpStatus.OK);
    }

}
