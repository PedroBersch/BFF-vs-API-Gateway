package br.com.bersch.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@Tag(name = "FooBar")
@RestController
@RequestMapping("/book-service")
public class FooBarController {
    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
    @GetMapping("/foo-bar")
    public String fooBar(){
        logger.info("Foo Bar");
        var responseponse = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
        return responseponse.getBody();
    }
    public String fallbackMethod(Exception e){
        return "fallback foo-bar!!\n\n\n";
    }
}
