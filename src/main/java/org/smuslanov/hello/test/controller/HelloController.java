package org.smuslanov.hello.test.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(
        value = "/hello",
        tags = "hello")
@RestController
@RequestMapping("${api.path}/hello")
public class HelloController {
    @GetMapping
    String sayHello() {
        return "Hello world!";
    }
}