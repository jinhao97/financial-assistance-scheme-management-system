package sg.gov.financial.assistance.scheme.assignment.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleBudgetApplicationException(ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getHttpStatus(), e.getMessage(), e.getTimestamp()));
    }

}
