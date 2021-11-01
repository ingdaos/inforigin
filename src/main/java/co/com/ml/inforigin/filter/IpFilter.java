package co.com.ml.inforigin.filter;

import co.com.ml.inforigin.dto.ResponseDto;
import co.com.ml.inforigin.service.BlacklistService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Filtro encargado de rechazar peticiones de ip bloqueadas
 *
 * @author ingda
 */
@Slf4j
@Component
public class IpFilter implements Filter {

    public static final String IP_HEADER = "X-Forwarded-For";

    private final BlacklistService blackListService;

    public IpFilter(BlacklistService blackListService) {
        this.blackListService = blackListService;
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        log.debug("Validando IP");

        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;

        String ip = obtenerIp(request);
        if (this.blackListService.isAllowed(ip)) {
            log.debug("Ip con permisos para consultar " + ip);
            fc.doFilter(sr, sr1);
        } else {
            log.warn("Ip no permitida " + ip);
            ResponseDto dto = new ResponseDto();
            dto.setError(true);
            dto.setErrorDescription("No tiene permisos para realizar la consulta");
            String jsonResp = new ObjectMapper().writeValueAsString(dto);

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(jsonResp);
            response.flushBuffer();
        }
    }

    /**
     * Mètodo encargado de obtener la direcciòn IP de donde se realiza la
     * peticiòn
     *
     * @param request
     * @return
     */
    private String obtenerIp(HttpServletRequest request) {
        try {
            String headerIp = request.getHeader(IP_HEADER);
            if (headerIp != null && !headerIp.isEmpty()) {
                return headerIp;
            } else {
                return request.getRemoteAddr();
            }
        } catch (Exception ex) {
            log.error("Error procesando validación IP", ex);
        }
        return null;
    }

}
