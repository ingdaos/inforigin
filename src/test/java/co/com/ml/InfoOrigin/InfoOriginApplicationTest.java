package co.com.ml.InfoOrigin;

import co.com.ml.inforigin.InfoOriginApplication;
import co.com.ml.inforigin.dto.ResponseDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author ingda
 */
@ContextConfiguration(classes = InfoOriginApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfoOriginApplicationTest {

    protected static final String IP = "127.0.0.1";

    private final Gson gson = new Gson();

    @LocalServerPort
    protected int port;

    @Value("${server.servlet.contextPath}")
    protected String contextPath;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Autowired
    protected ModelMapper mapper;

    /**
     * Obtiene mensaje estandar
     *
     * @param response
     * @return
     */
    protected ResponseDto getResponseDto(String response) {
        Assertions.assertNotNull(response);
        ResponseDto dto = gson.fromJson(response, ResponseDto.class);
        Assertions.assertNotNull(dto);
        return dto;
    }

}
