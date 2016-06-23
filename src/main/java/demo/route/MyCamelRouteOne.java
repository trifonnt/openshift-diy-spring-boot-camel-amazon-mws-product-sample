package demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class MyCamelRouteOne extends RouteBuilder {

	@Override
	public void configure() {
		from("timer:trigger?period={{trigger.demo1.period}}")
			.transform().simple("ref:myBeanOne")
			.to("log:out");
	}

	@Bean
	String myBeanOne() {
		return "I'm Spring bean ONE!";
	}

}