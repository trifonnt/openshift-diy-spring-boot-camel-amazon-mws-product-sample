package demo;

import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyApplicationConfig {

	private static final Logger LOG = LoggerFactory.getLogger(MyApplicationConfig.class);

//	@Autowired
//	CamelContext camelContext;

//	@Bean
//	IMyService myService() {
//		return new DefaultMyService( camelContext );
//	}

	private static final String CAMEL_URL_MAPPING = "/camel/*";
	private static final String CAMEL_SERVLET_NAME = "CamelServlet";


	@Bean
	CamelContextConfiguration contextConfiguration() {
		return new CamelContextConfiguration() {
			@Override
			public void beforeApplicationStart(CamelContext context) {
				// Custom configuration goes here
				LOG.info(" *** TRIFON - before Camel Application Start!"); // Old: Configuring Camel metrics on all routes
			}

			@Override
			public void afterApplicationStart(CamelContext camelContext) {
				LOG.info(" *** TRIFON - after Camel Application Start!");
			}
		};
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		LOG.info(" *** TRIFON - executing servletRegistrationBean!");
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), CAMEL_URL_MAPPING);
		registration.setName(CAMEL_SERVLET_NAME);
		return registration;
    }
}