package com.dairyproject.exception_handler;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.dairyproject.exceptions.ConsumerNotFoundException;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectAdminDetect;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.ProductNotFoundException;
import com.dairyproject.exceptions.SellerNotFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConsumerNotFoundException.class)
    public ResponseEntity<?> handleConsumerNotFoundException(ConsumerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAddressFoundException.class)
    public ResponseEntity<?> handleEmailAddressFoundException(EmailAddressFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectAdminDetect.class)
    public ResponseEntity<?> handleIncorrectAdminDetect(IncorrectAdminDetect ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberFoundException.class)
    public ResponseEntity<?> handlePhoneNumberFoundException(PhoneNumberFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<?> handleSellerNotFoundException(SellerNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnmatchedPasswordException.class)
    public ResponseEntity<?> handleUnmatchedPasswordException(UnmatchedPasswordException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameFoundException.class)
    public ResponseEntity<?> handleUsernameFoundException(UsernameFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<?> handleUnsupportedEncodingException(UnsupportedEncodingException ex) {
    	return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Generic exception handler for other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//@ExceptionHandler(ConsumerNotFoundException.class)
//@ResponseStatus(HttpStatus.NOT_FOUND)
//public String handleConsumerNotFoundException(ConsumerNotFoundException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(EmailAddressFoundException.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handleEmailAddressFoundException(EmailAddressFoundException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(IncorrectAdminDetect.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handleIncorrectAdminDetect(IncorrectAdminDetect ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(IncorrectPasswordException.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handleIncorrectPasswordException(IncorrectPasswordException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(PhoneNumberFoundException.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handlePhoneNumberFoundException(PhoneNumberFoundException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(ProductNotFoundException.class)
//@ResponseStatus(HttpStatus.NOT_FOUND)
//public String handleProductNotFoundException(ProductNotFoundException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(SellerNotFoundException.class)
//@ResponseStatus(HttpStatus.NOT_FOUND)
//public String handleSellerNotFoundException(SellerNotFoundException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(UnmatchedPasswordException.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handleUnmatchedPasswordException(UnmatchedPasswordException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(UsernameFoundException.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handleUsernameFoundException(UsernameFoundException ex) {
//    return ex.getMessage();
//}
//
//@ExceptionHandler(UnsupportedEncodingException.class)
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public String handleUnsupportedEncodingException(UnsupportedEncodingException ex) {
//    return "Unsupported encoding exception occurred";
//}
//
//// Generic exception handler for other exceptions
//@ExceptionHandler(Exception.class)
//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//public String handleGlobalException(Exception ex) {
//    return "An error occurred: " + ex.getMessage();
//}
