package com.cdamayab.flox.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * Global exception handler to handle all uncaught exceptions throughout the application.
 * Provides customized error messages based on the active Spring profile (e.g., dev or prod).
 */
@Hidden         // fix documentation error java.lang.NoSuchMethodError: 'void org.springframework.web.method.ControllerAdviceBean.<init>(java.lang.Object)'
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Active Spring profile, injected from the application's configuration.
     * Defaults to "default" if no active profile is set.
     */
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    /**
     * Handles all uncaught exceptions in the application.
     * Depending on the active profile, includes detailed error headers in the response.
     *
     * @param ex the exception to handle
     * @return a ResponseEntity containing the error message and a 500 HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Print stack trace for logging purposes
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("-------------------------- UNCAUGTH EXCEPTION ----------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        //ex.printStackTrace();

        // Build and return the error response
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(("dev".equalsIgnoreCase(activeProfile) ? GlobalExceptionHandler.generateExceptionHeader(ex) : "")
                        + ex.getMessage());
    }

    /**
     * Generates a header string that provides information about the exception location.
     * This includes the class name, method name, and line number where the exception occurred.
     *
     * @param throwable the exception for which to generate the header
     * @return a formatted string containing exception location details
     */
    private static String generateExceptionHeader(Throwable throwable) {
        if (throwable == null || throwable.getStackTrace().length == 0) {
            return "Exception occurred, but no stack trace available\n";
        }

        // Retrieve the first stack trace element (most relevant for the error)
        StackTraceElement element = throwable.getStackTrace()[0];
        String className = element.getClassName();
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();

        // Build and return the formatted header
        return String.format("Generated exception in %s.%s (line %d): ", className, methodName, lineNumber);
    }

    /**
     * Global exception handler for validation errors.
     * This method is triggered when a {@link MethodArgumentNotValidException}.
     * when a request body does not pass validation constraints from entities validations.
     * Needs the @Valid annotation to effectively throw the exception
     * 
     * It processes the validation errors, extracts the field name and associated error message,
     * and returns a detailed response with a {@link BAD_REQUEST} HTTP status.
     *
     * @param ex The exception object that contains the details of the validation errors.
     *           This is typically thrown when the request body fails validation against model constraints.
     * @return A {@link ResponseEntity} containing a structured error response with an HTTP status of {@link HttpStatus#BAD_REQUEST}.
     *         The response body contains a list of validation errors with details on the field and the error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder("Validation failed: \n");

        for (FieldError fieldError : result.getFieldErrors()) {
            errorMessage.append("{field:")
                        .append(fieldError.getField())
                        .append("\nerror:")
                        .append(fieldError.getDefaultMessage())
                        .append("\n}");
        }

        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }
    
}
