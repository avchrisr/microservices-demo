package com.chrisr.userdashboard.controller.exception;

import com.chrisr.userdashboard.exception.BadRequestException;
import com.chrisr.userdashboard.exception.UserAlreadyExistsException;
import com.chrisr.userdashboard.exception.UserNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        logger.error("Error occurred while using Feign client to send HTTP Request. Status code " + response.status() + ", feignMethod = " + s);

        switch (response.status()) {
            case 400:
                return new BadRequestException("Request not valid..");
            case 404:
                return new UserNotFoundException("User not found..");
            case 409:
                return new UserAlreadyExistsException("Username already exists..");
//            default:
//                return new Exception(response.reason());
        }

        // response.reason() appears null at all times
        return new ResponseStatusException(HttpStatus.valueOf(response.status()), "Exception = " + response.reason());
    }
}
