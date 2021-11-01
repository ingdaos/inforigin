package co.com.ml.InfoOrigin;

import co.com.ml.inforigin.dto.IpAddressDto;
import co.com.ml.inforigin.dto.IpStatusDto;
import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.utilities.IpStatus;
import co.com.ml.inforigin.utilities.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BlacklistTests extends InfoOriginApplicationTest {

    /**
     * Test encargado de analizar resultados de los servicios blacklist
     */
    @Test
    public void testBlackList() {
        bloquearIp();
        consultarIpBloqueada();
        desbloquearIp();
    }

    /**
     * Bloquear IP
     */
    public void bloquearIp() {
        System.out.println("bloquearIp");
        String url = getBlacklistUrl(Paths.BAN_IP, false);
        IpAddressDto request = getRequest();
        String resp = testRestTemplate.postForObject(url, request, String.class);
        ResponseDto responseDto = getResponseDto(resp);
        assertsBlacklist(responseDto, IpStatus.BANNED.getValue());
    }

    /**
     * Consulta IP bloqueada
     */
    public void consultarIpBloqueada() {
        System.out.println("consultarIpBloqueada");
        String url = getBlacklistUrl(Paths.ALLOWED, true);
        String resp = testRestTemplate.getForObject(url, String.class);
        ResponseDto responseDto = getResponseDto(resp);
        Assertions.assertFalse(responseDto.isError());
        Assertions.assertNotNull(responseDto.getResult());
        Assertions.assertFalse((boolean) responseDto.getResult());
    }

    /**
     * Desbloquear IP
     */
    public void desbloquearIp() {
        System.out.println("desbloquearIp");
        String url = getBlacklistUrl(Paths.ALLOW_IP, false);
        IpAddressDto request = getRequest();
        String resp = testRestTemplate.postForObject(url, request, String.class);
        ResponseDto responseDto = getResponseDto(resp);
        assertsBlacklist(responseDto, IpStatus.ALLOWED.getValue());
    }

    /**
     * Validaciones para bloquear o desbloquear IP
     *
     * @param dto
     * @param status
     */
    public void assertsBlacklist(ResponseDto dto, String status) {
        Assertions.assertFalse(dto.isError());
        Assertions.assertNotNull(dto.getResult());
        IpStatusDto ipStatus = mapper.map(dto.getResult(), IpStatusDto.class);
        Assertions.assertEquals(IP, ipStatus.getIpAddress());
        Assertions.assertEquals(status, ipStatus.getStatus());
    }

    /**
     * Obtiene dto con la dirección IP
     *
     * @return
     */
    private IpAddressDto getRequest() {
        IpAddressDto req = new IpAddressDto();
        req.setIpAddress(IP);
        return req;
    }

    /**
     * URL para realizar pruebas servicios blacklist
     *
     * @param path
     * @param setIp
     * @return
     */
    private String getBlacklistUrl(String path, boolean setIp) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://127.0.0.1:");
        sb.append(String.valueOf(port));
        sb.append(contextPath);
        sb.append(Paths.BLACKLIST);
        sb.append(path);
        if (setIp) {
            sb.append("?ip=");
            sb.append(IP);
        }
        return sb.toString();
    }

}
