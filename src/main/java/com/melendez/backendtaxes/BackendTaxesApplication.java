package com.melendez.backendtaxes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class BackendTaxesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendTaxesApplication.class, args);
	}
}

@RestController
class HomeController {
    @GetMapping("/")
    public String hello() {
        return "Hello!";
    }
}
