package com.kajisaab.ecommerce.core.exception;

import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles any BadRequest Exception thrown from the system.
     * @param e Exception
     * @return Bad Request exception response.
     */
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> badRequestException(BadRequestException e){
        int badRequest = HttpStatus.BAD_REQUEST.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        System.out.println("responseObject = " + responseObject);
        ApiException apiException = new ApiException(
                badRequest,
                HttpStatus.BAD_REQUEST,
                responseObject
        );

        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(badRequest));
    }

    /**
     * Handles any unhandled error thrown during the jwt verification.
     * @param e ClaimJwtException
     * @return Forbidden Response exception response.
     */
    @ExceptionHandler(value = {ClaimJwtException.class})
    public ResponseEntity<Object> invalidTokenException(ClaimJwtException e){
        logger.error(e.getMessage());
        int forbidden = HttpStatus.FORBIDDEN.value();
        Object responseObject = new GenerateErrorMessage("Token invalid");
        ApiException apiException = new ApiException(
                forbidden,
                HttpStatus.FORBIDDEN,
                responseObject
        );

        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(forbidden));
    }

    /**
     * Handle the expired token exception.
     * @param e ExpiredJwtException
     * @return Unauthorized Response
     */
    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> expiredTokenExceptionHandler(ExpiredJwtException e){
        logger.error(e.getMessage());
        int unauthorized = HttpStatus.UNAUTHORIZED.value();
        Object responseObject = new GenerateErrorMessage("Token Expired");
        ApiException apiException = new ApiException(
                unauthorized,
                HttpStatus.UNAUTHORIZED,
                responseObject
        );

        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(unauthorized));
    }

    /**
     * Typically used for the validation exception
     * @param e MethodArgumentNot validException
     * @return error.
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException e){
        logger.error(e.getMessage());
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    /**
     * Handles the server crash exception
     * @param e IllegalStateException
     * @return Internal Server Error Response
     */
    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<Object> handleInternalServerException(IllegalStateException e){
        logger.error(e.getMessage());
        int internalServerError = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Object responseObject = new GenerateErrorMessage("Internal Server Error");
        ApiException apiException = new ApiException(
                internalServerError,
                HttpStatus.INTERNAL_SERVER_ERROR,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(internalServerError));
    }

    /**
     * Handle the Illegal Argument Exception
     * @param e IllegalArgumentException
     * @return BadRequest.
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e){
        logger.error(e.getMessage());
        int badRequest = HttpStatus.BAD_REQUEST.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        ApiException apiException = new ApiException(
                badRequest,
                HttpStatus.BAD_REQUEST,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(badRequest));
    }

    /**
     * Handle the sql constraint violation exception
     * @param e SQLIntegrityConstraintViolationException
     * @return Bad Request
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLException(SQLIntegrityConstraintViolationException e){
        logger.error(e.toString());
        int badRequest = HttpStatus.BAD_REQUEST.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        ApiException apiException = new ApiException(
                badRequest,
                HttpStatus.BAD_REQUEST,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(badRequest));
    }

    /**
     * Handle the invalid argument injection in sql
     */
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<Object> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        logger.error(e.toString());
        int badRequest = HttpStatus.BAD_REQUEST.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        ApiException apiException = new ApiException(
                badRequest,
                HttpStatus.BAD_REQUEST,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(badRequest));

    }

    /**
     * Handle Malformed Jwt Exception
     * @param e MalformedJwtException
     * @return Unauthorized response
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Object> handleInvalidJwtToken(MalformedJwtException e){
        logger.error(e.getMessage());
        int unauthorizedRequest = HttpStatus.UNAUTHORIZED.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        ApiException apiException = new ApiException(
                unauthorizedRequest,
                HttpStatus.UNAUTHORIZED,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(unauthorizedRequest));
    }

    /**
     * Handle any conversion failed exception
     * @param e ConverterNotFoundException
     * @return BadRequest
     */
    @ExceptionHandler(ConverterNotFoundException.class)
    public ResponseEntity<Object> handleJpaConverterException(ConverterNotFoundException e){
        logger.error(e.getMessage());
        int unauthorizedRequest = HttpStatus.BAD_REQUEST.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        ApiException apiException = new ApiException(
                unauthorizedRequest,
                HttpStatus.BAD_REQUEST,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(unauthorizedRequest));
    }

    /**
     * Handle any null pointer exception
     * @param e NullPointerException
     * @return Internal Server Error
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e){
        logger.error(e.getMessage());
        int internalServerCrash = HttpStatus.INTERNAL_SERVER_ERROR.value();
        Object responseObject = new GenerateErrorMessage(e.getMessage());
        ApiException apiException = new ApiException(
                internalServerCrash,
                HttpStatus.INTERNAL_SERVER_ERROR,
                responseObject
        );
        return new ResponseEntity<>(apiException, HttpStatusCode.valueOf(internalServerCrash));
    }

}
