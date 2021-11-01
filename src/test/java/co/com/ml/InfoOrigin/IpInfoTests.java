package co.com.ml.InfoOrigin;

import co.com.ml.inforigin.InfoOriginApplication;
import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.utilities.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author ingda
 */
@ContextConfiguration(classes = InfoOriginApplication.class)
public class IpInfoTests extends InfoOriginApplicationTest {
    
    public static final String IP_INFO_TEST = "13.227.26.123";

    @Test
    public void testInfoIP() {
        String url = getInfoIPUrl(IP_INFO_TEST);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Forwarded-For", "192.168.0.1");
        ResponseEntity<String> exchange = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        Assertions.assertNotNull(exchange);
        Assertions.assertNotNull(exchange.getBody());
        ResponseDto responseDto = getResponseDto(exchange.getBody());
        System.out.println(responseDto);
    }

    /**
     * Obtener URL info IP
     *
     * @param path
     * @return
     */
    private String getInfoIPUrl(String ip) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://127.0.0.1:");
        sb.append(String.valueOf(port));
        sb.append(contextPath);
        sb.append(Paths.IP_INFO);
        if (ip != null) {
            sb.append("?ip=");
            sb.append(ip);
        }
        return sb.toString();
    }

}
