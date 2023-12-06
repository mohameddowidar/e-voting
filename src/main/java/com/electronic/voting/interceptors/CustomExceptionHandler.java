package com.electronic.voting.interceptors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.faces.application.ViewExpiredException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(ViewExpiredException.class)
    public String handleViewExpiredException() {
        return "redirect:/error";
    }

}
