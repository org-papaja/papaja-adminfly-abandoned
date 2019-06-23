package org.papaja.adminfly.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings({"unused"})
@ControllerAdvice
public class GlobalController {

    @ExceptionHandler({Exception.class})
    public String handleException(
            Exception exception, HttpServletRequest request, HttpServletResponse response, Model model
    ) {
        String template = "errors/exception";

        model.addAttribute("stack", ExceptionUtils.getStackTrace(exception));
        model.addAttribute("exceptionClass", exception.getClass().getName());
        model.addAttribute("rootMassage", exception.getMessage());

        return template;
    }

}