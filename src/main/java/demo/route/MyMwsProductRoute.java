package demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import demo.processor.AmazonProductProcessor;


@Component
public class MyMwsProductRoute extends RouteBuilder {

	//   SSL and Apache Camel HTTP component
	// - http://stackoverflow.com/questions/5706166/apache-camel-http-and-ssl

	//   apache camel http request
	// - http://camel.apache.org/how-to-use-camel-as-a-http-proxy-between-a-client-and-server.html
	// - http://stackoverflow.com/questions/5646557/apache-camel-http-to-http-routing-is-it-possible
	@Override
	public void configure() {
		// Create processors
		AmazonProductProcessor amazonProductProcessor = new AmazonProductProcessor();

		// Use OpenShift environment Variable or Default values if variables are not found
		from("jetty:http://{{env:OPENSHIFT_DIY_IP:0.0.0.0}}:{{env:OPENSHIFT_DIY_PORT:8080}}/mws/product?matchOnUriPrefix=true")
			//.to("http4://www.google.com?bridgeEndpoint=true&throwExceptionOnFailure=false")
			.process( amazonProductProcessor ) // or .processRef("amazonProductProcessor")
			.to("log:out")
		;
	}
}