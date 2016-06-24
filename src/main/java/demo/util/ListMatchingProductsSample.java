package demo.util;

import com.amazonservices.mws.products.MarketplaceWebServiceProducts;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsException;
import com.amazonservices.mws.products.model.ListMatchingProductsRequest;
import com.amazonservices.mws.products.model.ListMatchingProductsResponse;
import com.amazonservices.mws.products.model.ResponseHeaderMetadata;

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
		// Get a client connection.
		// Make sure you've set the variables in
		// MarketplaceWebServiceProductsSampleConfig.
		MarketplaceWebServiceProductsClient client = MarketplaceWebServiceProductsSampleConfig.getClient();

		// Create a request.
		ListMatchingProductsRequest request = new ListMatchingProductsRequest();
		String sellerId = "example";
		request.setSellerId(sellerId);

		String mwsAuthToken = "example";
		request.setMWSAuthToken(mwsAuthToken);

		String marketplaceId = "example";
		request.setMarketplaceId(marketplaceId);

		String query = "example";
		request.setQuery(query);

		String queryContextId = "example";
		request.setQueryContextId(queryContextId);

		// Make the call.
		ListMatchingProductsResponse response = ListMatchingProductsSample.invokeRequest(client, request);
	}
}