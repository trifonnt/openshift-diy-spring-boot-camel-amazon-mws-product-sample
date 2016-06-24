package demo.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public class AmazonProductProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(AmazonProductProcessor.class);


	@Override
	public void process(Exchange exchange) throws Exception {
		// String body = exchange.getIn().getBody(String.class);

		// Get the bookId Header
		String searchString = (String) exchange.getIn().getHeader("searchString");
		LOG.info("Received query parameter[searchString]=" + searchString);

		// We have access to the HttpServletRequest here and we can grab it if we need to
		// HttpServletRequest req = exchange.getIn().getBody(HttpServletRequest.class);
		// assertNotNull(req);

		// For unit testing
		// assertEquals("searchString=123", body);

		// Send a html response
		exchange.getOut().setHeader(Exchange.CONTENT_TYPE, MediaType.TEXT_HTML);
		exchange.getOut().setBody("<html><body>Searching for " + searchString + ".</body></html>")
		;
	}
}