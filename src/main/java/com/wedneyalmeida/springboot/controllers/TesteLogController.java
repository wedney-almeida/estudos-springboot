package com.wedneyalmeida.springboot.controllers;

import com.wedneyalmeida.springboot.services.PersonServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TesteLogController {

    private Logger logger = LoggerFactory.getLogger(TesteLogController.class.getName());


    @GetMapping("/test")
    public String testLog(){
        logger.debug("This is en DEBUG log");
        logger.info("This is en INFO log");
        logger.warn("This is en WARN log");
        logger.error("This is en ERROR log");
        return "Logs generated sucessfully!";
    }
}
