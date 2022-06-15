package com.cts.skilltracker.handlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cts.skilltracker.exceptions.ApiError;
import com.cts.skilltracker.exceptions.AssociateExistsException;
import com.cts.skilltracker.exceptions.InvalidExpertiseLevelException;
import com.cts.skilltracker.exceptions.InvalidSkillException;
import com.cts.skilltracker.exceptions.ResponseEntityBuilder;
import com.cts.skilltracker.utils.CommandSideConstants;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * This exception will be triggered if the request body is invalid
	 */

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, CommandSideConstants.INVALID_JSON_MSG,
				details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * This exception occurs when a handler method argument annotated with @Valid
	 * annotation failed validation
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getObjectName() + " : " + error.getDefaultMessage()).collect(Collectors.toList());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.VALIDATION_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * 
	 * This exception indicates that a controller method does not receive a required
	 * parameter
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getParameterName() + " parameter is missing");

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.MISSING_PARAM_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * The exception informs that the specified request media type (Content type) is
	 * not supported
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

		details.add(builder.toString());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				CommandSideConstants.UNSUPPORTED_MEDIA_TYPE_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * This exception is thrown if there is no handler for a particular request
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND,
				CommandSideConstants.METHOD_NOT_FOUND_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);

	}

	/**
	 * The exception, as the name implies, is thrown when a method parameter has the
	 * wrong type
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.TYPE_MISMATCH_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * This exception reports the result of constraint violations
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.CONSTRAINT_VIOLATION_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * This exception is used to handle invalid skill being passed in the request
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(InvalidSkillException.class)
	public ResponseEntity<Object> handleInvalidSkillException(InvalidSkillException ex) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.SKILL_NOT_FOUND_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * This exception is thrown when there is an invalid expertise level for a skill
	 */
	@ExceptionHandler(InvalidExpertiseLevelException.class)
	public ResponseEntity<Object> handleInvalidExpertiseLevelException(InvalidExpertiseLevelException ex) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.INVALID_EXPERTISE_LEVEL_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * This exception is thrown when there is an associate that already exists
	 */
	@ExceptionHandler(AssociateExistsException.class)
	public ResponseEntity<Object> handleAssociateExistsException(AssociateExistsException ex) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST,
				CommandSideConstants.ADD_PROFILE_ERROR_MSG, details);

		return ResponseEntityBuilder.build(err);
	}

	/**
	 * Defining a global exception hander allows us to deal with all those
	 * exceptions that do not have specific handlers.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

		List<String> details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());

		ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, CommandSideConstants.EXCEPTION_MSG,
				details);

		return ResponseEntityBuilder.build(err);
	}

}
