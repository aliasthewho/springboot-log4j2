package com.example.log4basis.api;

import java.lang.module.Configuration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.log4basis.service.ApiService;

@RestController
public class LoggingController {

    private static final Logger logger = LogManager.getLogger(LoggingController.class);
    private final ApiService apiService;

    public LoggingController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/log")
    public String getMethodName() {
        logger.trace("TRACE message");
        logger.debug("DEBUG message");
        logger.info("INFO message");
        logger.warn("WARN message");
        logger.error("ERROR message");
        logger.fatal("FATAL message");
        apiService.getHelloWorld();
        return "checkout the output messages";
    }
    
    @GetMapping("/change-log-level")
    public String getMethodName(@RequestParam String loggerName, @RequestParam String level) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        org.apache.logging.log4j.core.config.Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(loggerName);
        loggerConfig.setLevel(Level.toLevel(level));
        ctx.updateLoggers();
        return "Log level changed to " + level + " for logger " + loggerName;
    }
    

}
