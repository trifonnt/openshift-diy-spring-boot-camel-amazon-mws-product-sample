package demo.util;

import com.amazonservices.mws.products.MWSEndpoint;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsyncClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;


public class MarketplaceWebServiceProductsSampleConfig {

	/** Developer AWS access key. */
	private static final String accessKey = "replaceWithAccessKey";

	/** Developer AWS secret key. */
	private static final String secretKey = "replaceWithSecretKey";

	/** The client application name. */
	private static final String appName = "replaceWithAppName";

	/** The client application version. */
	private static final String appVersion = "replaceWithAppVersion";

	/**
	 * The endpoint for region service and version.
	 * ex: serviceURL = MWSEndpoint.NA_PROD.toString();
	 */
	private static final String serviceURL = MWSEndpoint.DE_PROD.toString();

	/** The client, lazy initialized. Async client is also a sync client. */
	private static MarketplaceWebServiceProductsAsyncClient client = null;

	public static MarketplaceWebServiceProductsClient getClient() {
		return getAsyncClient();
	}

	public static synchronized MarketplaceWebServiceProductsAsyncClient getAsyncClient() {
		if (client == null) {
			MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
			config.setServiceURL(serviceURL);
			// Set other client connection configurations here.
			client = new MarketplaceWebServiceProductsAsyncClient(accessKey, secretKey, appName, appVersion, config, null);
		}
		return client;
	}
}