package co.com.ml.inforigin.controller;

import co.com.ml.inforigin.dto.IpAddressDto;
import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.service.BlacklistService;
import co.com.ml.inforigin.utilities.Paths;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(Paths.BLACKLIST)
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @GetMapping(Paths.ALLOWED)
    public ResponseEntity<ResponseDto> allowed(@RequestParam String ip) {
        log.info("isAllowed -> {}", ip);
        return new ResponseEntity<>(blacklistService.allowed(ip), HttpStatus.OK);
    }

    @PostMapping(Paths.BAN_IP)
    public ResponseEntity<ResponseDto> banIp(@Valid @RequestBody IpAddressDto request) {
        log.info("banIp -> {}", request);
        return new ResponseEntity<>(blacklistService.banIp(request), HttpStatus.CREATED);
    }

    @PostMapping(Paths.ALLOW_IP)
    public ResponseEntity<ResponseDto> allowIp(@Valid @RequestBody IpAddressDto request) {
        log.info("allowIp -> {}", request);
        return new ResponseEntity<>(blacklistService.allowIp(request), HttpStatus.OK);
    }

}
