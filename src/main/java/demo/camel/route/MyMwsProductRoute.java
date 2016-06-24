package demo.camel.route;

import org.apache.camel.PropertyInject;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import demo.camel.processor.AmazonProductProcessor;


@Component
public class MyMwsProductRoute extends RouteBuilder {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(MyMwsProductRoute.class);

	@PropertyInject(value = "mws.url", defaultValue = "")
	private String mwsUrl = "";


	//   SSL and Apache Camel HTTP component
	// - http://stackoverflow.com/questions/5706166/apache-camel-http-and-ssl

	//   apache camel http request
	// - http://camel.apache.org/how-to-use-camel-as-a-http-proxy-between-a-client-and-server.html
	// - http://stackoverflow.com/questions/5646557/apache-camel-http-to-http-routing-is-it-possible
	@Override
	public void configure() {
		// Create processors
		AmazonProductProcessor amazonProductProcessor = new AmazonProductProcessor( mwsUrl );

		// http://localhost:8080/camel/mws/product
        from("servlet:///mws/product?matchOnUriPrefix=true")
        	//.transform().constant("Hello from Camel!");
        
		// Use OpenShift environment Variable or Default values if variables are not found
//		from("jetty:http://{{env:OPENSHIFT_DIY_IP:0.0.0.0}}:{{env:OPENSHIFT_DIY_PORT:8080}}/mws/product?matchOnUriPrefix=true")
			//.to("http4://www.google.com?bridgeEndpoint=true&throwExceptionOnFailure=false")
			.process( amazonProductProcessor ) // or .processRef("amazonProductProcessor")
			.to("log:out")
		;
	}
}