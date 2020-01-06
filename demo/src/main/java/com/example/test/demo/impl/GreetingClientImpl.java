package com.example.test.demo.impl;

import com.example.test.demo.controller.GreetingClient;
import com.example.test.demo.dto.UserDTO;
import com.example.test.demo.exception.ExceptionErrorCode;
import com.example.test.demo.exception.InvalidRequestException;
import com.example.test.demo.service.CompositionService;
import com.example.test.demo.sto.UserSTO;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class GreetingClientImpl implements GreetingClient {

    private CompositionService compositionService;
    private static final Logger LOG = getLogger(GreetingClientImpl.class.getName());

    private Tracer tracer;


    @Autowired
    public GreetingClientImpl(CompositionServiceImpl compositionService, Tracer tracer) {
        this.compositionService = compositionService;
        this.tracer = tracer;
    }

    /*@Override
    public ResponseEntity<Greeting> greeting(@RequestParam(name = "message") String msg) {
        LOG.info("Received message: " + msg);
        ResponseEntity<Greeting> responseEntity = compositionService.getGreeting(msg);
        tracer.activeSpan().setTag("producer_result", responseEntity.toString());
        return responseEntity;
    }*/

}
