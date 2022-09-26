package com.example.myvocab.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class LoggingController {

    @RequestMapping("/log")
    public String index(){
        log.trace("Trace logging");
        log.debug("Debug logging");
        log.info("Info logging");
        log.warn("Warn logging");
        log.error("Error logging");

        return "Check out the log";
    }
}
