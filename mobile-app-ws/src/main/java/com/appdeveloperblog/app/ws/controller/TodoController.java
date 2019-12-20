package com.appdeveloperblog.app.ws.controller;

import com.appdeveloperblog.app.ws.feign.TodoClient;
import com.appdeveloperblog.app.ws.feign.TodoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoClient todoClient;

    @GetMapping()
    public List<TodoModel> getTodos(){

        return todoClient.getTodos();
    }
}
