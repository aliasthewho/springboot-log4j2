package com.example.log4basis.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

        private static final Logger logger = LogManager.getLogger(ApiService.class);


    private String getHello() {
        return "Hello";
    }

    private String getWorld() {
        return "World";
    }

    public String getHelloWorld() {
        logger.trace("TRACE message");
        logger.debug("DEBUG message");
        logger.info("INFO message");
        logger.warn("WARN message");
        logger.error("ERROR message");
        logger.fatal("FATAL message");
        return getHello() + " " + getWorld();
    }

}
