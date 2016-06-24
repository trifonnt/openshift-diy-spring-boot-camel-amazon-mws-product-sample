package demo.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsException;
import com.amazonservices.mws.products.model.ListMatchingProductsRequest;
import com.amazonservices.mws.products.model.ListMatchingProductsResponse;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;

import demo.util.MWSConfig;


public class AmazonProductProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(AmazonProductProcessor.class);

	private String mwsUrl;

	private String mwsAccessKey;

	private String mwsSecretAccessKey;

	private String mwsMerchantId;

	private String mwsMarketplaceId;


	public AmazonProductProcessor(String mwsUrl, String mwsAccessKey, String mwsSecretAccessKey, String mwsMerchantId, String mwsMarketplaceId) {
		this.mwsUrl = mwsUrl;
		this.mwsAccessKey = mwsAccessKey;
		this.mwsSecretAccessKey= mwsSecretAccessKey;
		this.mwsMerchantId = mwsMerchantId;
		this.mwsMarketplaceId = mwsMarketplaceId;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		// String body = exchange.getIn().getBody(String.class);

		// Get the searchString from the HTTP Request Parameters(Camel Exchange Header)
		String searchString = (String) exchange.getIn().getHeader("searchString");
		LOG.info("Received query parameter[searchString]=" + searchString);

		String searchContext = (String) exchange.getIn().getHeader("context");
		LOG.info("Received query parameter[context]=" + searchContext);

		// Get a client connection.
		MarketplaceWebServiceProductsClient client = MWSConfig.getClient(mwsAccessKey, mwsSecretAccessKey, mwsUrl);

		// Create a request.
		ListMatchingProductsRequest request = new ListMatchingProductsRequest();
		request.setSellerId( mwsMerchantId );
//		request.setMWSAuthToken( mwsSecretAccessKey );
		request.setMarketplaceId( mwsMarketplaceId );

		request.setQuery( searchString );

//		String queryContextId = "All"; // All, Books, ...
		request.setQueryContextId( searchContext );

		try {
			ListMatchingProductsResponse response = client.listMatchingProducts( request );

			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			// We recommend logging every the request id and timestamp of every call.
			System.out.println("Response:");
			System.out.println("RequestId: " + rhmd.getRequestId());
			System.out.println("Timestamp: " + rhmd.getTimestamp());
			String responseXml = response.toXML();

			exchange.getOut().setHeader("MwsRequestId", rhmd.getRequestId());
			exchange.getOut().setHeader("MwsTimestamp", rhmd.getTimestamp());
			exchange.getOut().setHeader("MwsQuotaMax", rhmd.getQuotaMax());
			exchange.getOut().setHeader("MwsQuotaRemaining", rhmd.getQuotaRemaining());
			exchange.getOut().setHeader("MwsQuotaResetsAt", rhmd.getQuotaResetsAt());

			exchange.getOut().setHeader(Exchange.CONTENT_TYPE, MediaType.TEXT_XML);
			exchange.getOut().setBody( responseXml );

		} catch (MarketplaceWebServiceProductsException ex) {
			// @Trifon - TODO - proper Camel route Exception handling!
			// Exception properties are important for diagnostics.
			System.out.println("Service Exception:");
			ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
			if (rhmd != null) {
				System.out.println("RequestId: " + rhmd.getRequestId());
				System.out.println("Timestamp: " + rhmd.getTimestamp());
			}
			System.out.println("Message: " + ex.getMessage());
			System.out.println("StatusCode: " + ex.getStatusCode());
			System.out.println("ErrorCode: " + ex.getErrorCode());
			System.out.println("ErrorType: " + ex.getErrorType());
			throw ex;
		}
	}
}