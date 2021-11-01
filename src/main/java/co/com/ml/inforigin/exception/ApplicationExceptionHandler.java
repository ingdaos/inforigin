package co.com.ml.inforigin.exception;

import co.com.ml.inforigin.dto.ResponseDto;
import java.util.Objects;
import javax.naming.ConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author ingda
 */
@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final String UNEXPECTED_ERROR = "Unexpected error";
    private static final String BAD_REQUEST_ERROR = "Bad request";

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleExceptionInternalServer(Exception ex) {
        log.error(UNEXPECTED_ERROR, ex);
        ResponseDto errorResp = getErrorResponse(UNEXPECTED_ERROR);
        return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error(BAD_REQUEST_ERROR, ex);
        ResponseDto errorResp = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(BAD_REQUEST_ERROR, ex);
        String errDesc;
        if (Objects.nonNull(ex) && Objects.nonNull(ex.getAllErrors())) {
            errDesc = String.valueOf(ex.getAllErrors());
        } else {
            errDesc = BAD_REQUEST_ERROR;
        }
        ResponseDto errorResp = getErrorResponse(errDesc);
        return new ResponseEntity<>(errorResp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleConfigurationException(ConfigurationException ex) {
        log.error(UNEXPECTED_ERROR, ex);
        ResponseDto errorResp = getErrorResponse(ex.getExplanation());
        return new ResponseEntity<>(errorResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleInfoNotFoundException(InfoNotFoundException ex) {
        log.error(UNEXPECTED_ERROR, ex);
        ResponseDto errorResp = getErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResp, HttpStatus.NOT_FOUND);
    }

    /**
     * Método encargado de generar plantilla de respuesta error con una
     * descripción
     *
     * @param description
     * @return
     */
    private ResponseDto getErrorResponse(String description) {
        ResponseDto errorResp = new ResponseDto();
        errorResp.setError(true);
        errorResp.setErrorDescription(description);
        return errorResp;
    }

}
