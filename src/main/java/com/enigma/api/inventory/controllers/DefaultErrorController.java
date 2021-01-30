package com.enigma.api.inventory.controllers;

import com.enigma.api.inventory.exceptions.PathNotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultErrorController implements ErrorController {

    @GetMapping("/error")
    public void handleError() {
        throw new PathNotFoundException();
    }

    @GetMapping("/errortest")
    public void handleUnknownError() {
        throw new NumberFormatException();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
