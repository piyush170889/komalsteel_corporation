package co.in.replete.komalindustries.exception;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.ResponseMessage;

@ControllerAdvice
public class BaseExceptionHandler {
	
  @Autowired
  Properties responseMessageProperties;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public BaseWrapper processValidationError(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    FieldError error = result.getFieldError();

    return processFieldError(error);
  }

  private BaseWrapper processFieldError(FieldError error) {
    ResponseMessage responseMessage = null;
    BaseWrapper response = null;
    if (error != null) {
      String msg = responseMessageProperties.getProperty(error.getDefaultMessage());
      responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.toString(), msg);
      response = new BaseWrapper(responseMessage);
    }
    return response;
  }
  
}
