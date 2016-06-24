package demo.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonservices.mws.products.MWSEndpoint;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsAsyncClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsClient;
import com.amazonservices.mws.products.MarketplaceWebServiceProductsConfig;


public class MWSConfig {

	private static final Logger LOG = LoggerFactory.getLogger(MWSConfig.class);

	public static final String PROPERTY_FILE_NAME = "application-private.properties";

	public static final String PROPERTY_MWS_URL = "MWS_URL";

	public static final String PROPERTY_MWS_ACCESS_KEY = "MWS_ACCESS_KEY";

	public static final String PROPERTY_MWS_SECRET_ACCESS_KEY = "MWS_SECRET_ACCESS_KEY";

	public static final String PROPERTY_MWS_MERCHANT_ID = "MWS_MERCHANT_ID";

	public static final String PROPERTY_MWS_MARKETPLACE_ID = "MWS_MARKETPLACE_ID";

	private static final String APP_NAME = "Trifon-Camel-MWS";

	private static final String APP_VERSION = "1.0.0";


	/** The client, lazy initialized. Async client is also a sync client. */
	private static MarketplaceWebServiceProductsAsyncClient client = null;


	public static MarketplaceWebServiceProductsClient getClient(Properties props) {
		String accessKey = props.getProperty(PROPERTY_MWS_ACCESS_KEY);
		String secretKey = props.getProperty(PROPERTY_MWS_SECRET_ACCESS_KEY);
		String serviceURL = props.getProperty(PROPERTY_MWS_URL, MWSEndpoint.DE_PROD.toString());

		return getAsyncClient( accessKey, secretKey, serviceURL );
	}
	public static MarketplaceWebServiceProductsAsyncClient getClient(String accessKey, String secretKey, String serviceURL) {
		return getAsyncClient( accessKey, secretKey, serviceURL );
	}

	public static synchronized MarketplaceWebServiceProductsAsyncClient getAsyncClient(String accessKey, String secretKey, String serviceURL) {
		if (client == null) {
			MarketplaceWebServiceProductsConfig config = new MarketplaceWebServiceProductsConfig();
			config.setServiceURL(serviceURL);

			client = new MarketplaceWebServiceProductsAsyncClient(accessKey, secretKey, APP_NAME, APP_VERSION, config, null);
		}
		return client;
	}

	public static Properties loadPropertyFile(String propsFileName) {
		if (propsFileName == null || propsFileName.isEmpty()) {
			throw new IllegalArgumentException("Properties file name MUST not be null!!!");
		}
		InputStream inStream = null;
		Properties props = new Properties();

		try {
			inStream = new FileInputStream( propsFileName );
			props.load( inStream );
		} catch (FileNotFoundException ex) {
			LOG.info("Not found file["+propsFileName+"]");
//			ex.printStackTrace();

			try {
				inStream = MWSConfig.class.getResourceAsStream("/"+propsFileName);
				props.load( inStream );
			} catch (IOException ex2) {
				LOG.info("Exception while loading property file["+ex2.getMessage()+"]");
				ex2.printStackTrace();
			}
		} catch (IOException ex) {
			LOG.info("Exception while loading property file["+ex.getMessage()+"]");
			ex.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return props;
	}
}