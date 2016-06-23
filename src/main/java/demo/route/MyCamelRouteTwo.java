package demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class MyCamelRouteTwo extends RouteBuilder {

	@Override
	public void configure() {
		from("timer:trigger?period={{trigger.demo2.period}}")
			.transform().simple("ref:myBeanTwo")
			.to("log:out");
	}

	@Bean
	String myBeanTwo() {
		return "I'm Spring bean TWO!";
	}

}