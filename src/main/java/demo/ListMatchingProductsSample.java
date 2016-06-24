package demo;

import java.util.Properties;

import com.amazonservices.mws.products.MarketplaceWebServiceProducts;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsException;
import com.amazonservices.mws.products.model.ListMatchingProductsRequest;
import com.amazonservices.mws.products.model.ListMatchingProductsResponse;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;

import demo.util.MWSConfig;

public class ListMatchingProductsSample {

	public static ListMatchingProductsResponse invokeRequest(MarketplaceWebServiceProducts client, ListMatchingProductsRequest request) {
		try {
			ListMatchingProductsResponse response = client.listMatchingProducts(request);

			ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
			// We recommend logging every the request id and timestamp of every call.
			System.out.println("Response:");
			System.out.println("RequestId: " + rhmd.getRequestId());
			System.out.println("Timestamp: " + rhmd.getTimestamp());
			String responseXml = response.toXML();
			System.out.println(responseXml);
			return response;
		} catch (MarketplaceWebServiceProductsException ex) {
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

	public static void main(String[] args) {
		Properties props = MWSConfig.loadPropertyFile("src/main/resources/" + MWSConfig.PROPERTY_FILE_NAME);
//		String secretKey = props.getProperty(MWSConfig.PROPERTY_MWS_SECRET_ACCESS_KEY);
		String merchantId = props.getProperty(MWSConfig.PROPERTY_MWS_MERCHANT_ID);
		String marketplaceId = props.getProperty(MWSConfig.PROPERTY_MWS_MARKETPLACE_ID);


		// Get a client connection.
		MarketplaceWebServiceProductsClient client = MWSConfig.getClient( props );

		// Create a request.
		ListMatchingProductsRequest request = new ListMatchingProductsRequest();
		request.setSellerId(merchantId);
//		request.setMWSAuthToken(secretKey);
		request.setMarketplaceId(marketplaceId);

		// TODO - Search string entered by the application user!
		String query = "OSGi";
		request.setQuery(query);

		// TODO - Proper Query context, something like a category where search is performed!
		String queryContextId = "All"; // All, Books, ...
		request.setQueryContextId(queryContextId);

		@SuppressWarnings("unused")
		ListMatchingProductsResponse response = ListMatchingProductsSample.invokeRequest(client, request);
	}
}