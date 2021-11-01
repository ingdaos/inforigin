package co.com.ml.inforigin.service;

import co.com.ml.inforigin.dto.IpAddressDto;
import co.com.ml.inforigin.dto.IpStatusDto;
import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.entity.IpInfoEntity;
import co.com.ml.inforigin.utilities.IpStatus;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ml.inforigin.repository.IpInfoRepo;

/**
 *
 * @author ingda
 */
@Slf4j
@Service
public class BlacklistService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IpInfoRepo ipInfoRepo;

    /**
     * Método encargado de consultar si una dirección IP se encuentra en lista
     * negra
     *
     * @param ip
     * @return
     */
    public ResponseDto allowed(String ip) {
        ResponseDto resp = new ResponseDto();
        resp.setResult(isAllowed(ip));
        resp.setError(false);
        return resp;
    }

    /**
     * Consulta si una IP se encuentra permitida
     *
     * @param ip
     * @return
     */
    public boolean isAllowed(String ip) {
        String status = ipInfoRepo.getStatus(ip);
        return Objects.isNull(status) || IpStatus.ALLOWED.getValue().equals(status);
    }

    /**
     * Método encargado de agregar una dirección IP a lista negra
     *
     * @param request
     * @return
     */
    public ResponseDto banIp(IpAddressDto request) {
        return processIp(request, IpStatus.BANNED);
    }

    /**
     * Método encargado de quitar restricción de una dirección IP de la lista
     * negra
     *
     * @param request
     * @return
     */
    public ResponseDto allowIp(IpAddressDto request) {
        return processIp(request, IpStatus.ALLOWED);
    }

    /**
     * Método encargado de registrar IP en lista negra o dar nuevamente permisos
     * de acuerdo al estado recibido
     *
     * @param request Petición con dirección IP
     * @param status Estado en lista negra
     * @return
     */
    private ResponseDto processIp(IpAddressDto request, IpStatus status) {
        ResponseDto resp = new ResponseDto();

        IpInfoEntity entity = ipInfoRepo.findByIpAddress(request.getIpAddress());
        if (Objects.isNull(entity)) {
            entity = mapper.map(request, IpInfoEntity.class);
        }
        entity.setStatus(status.getValue());
        ipInfoRepo.save(entity);

        IpStatusDto info = mapper.map(entity, IpStatusDto.class);
        resp.setResult(info);
        resp.setError(false);
        return resp;
    }

}
