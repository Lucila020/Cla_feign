package com.appdeveloperblog.app.ws.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.MediaType;
import java.util.List;

@FeignClient(name="TodoCliente",url = "http://jsonplaceholder,typicode.com")
public interface TodoClient {

    @GetMapping(value="/todos",consumes = MediaType.APPLICATION_JSON_VALUE)
    List<TodoModel> getTodos();

}
