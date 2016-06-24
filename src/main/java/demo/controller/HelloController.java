package demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);


	@RequestMapping(value = "/rs/hello")
	public String index() {
        return "Hello from REST!";
    }
}