package demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

	@RequestMapping(value = "/rs/hello")
	public String index() {
        return "Hello from REST!";
    }
}