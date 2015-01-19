package net.bryansaunders.camel.eap_6.order_service_infinispan;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.camel.Headers;
import org.apache.camel.Properties;

@Named
public final class CamelExchangeUtil {

	private static List<String> keyList = new LinkedList<String>();
	{
		CamelExchangeUtil.keyList.add(OrderUtility.HEADER_EUR_TOTAL_COST);
		CamelExchangeUtil.keyList.add(OrderUtility.HEADER_ORIG_REQUEST);
		CamelExchangeUtil.keyList.add(OrderUtility.HEADER_US_TOTAL_COST);
	}

	/**
	 * Moves All of the Headers into the Properties Map.
	 * 
	 * @param headers
	 *            Headers Map
	 * @param properties
	 *            Properties Map
	 */
	public static void backupHeaders(@Headers Map<String, Object> headers,
			@Properties Map<String, Object> properties) {

		for (String key : CamelExchangeUtil.keyList) {
			if (headers.containsKey(key)) {
				properties.put(key, headers.get(key));
			}
		}
	}

	/**
	 * Moves All of the Properties into the Headers Map.
	 * 
	 * @param headers
	 *            Headers Map
	 * @param properties
	 *            Properties Map
	 */
	public static void restoreHeaders(@Headers Map<String, Object> headers,
			@Properties Map<String, Object> properties) {

		for (String key : CamelExchangeUtil.keyList) {
			if (properties.containsKey(key)) {
				headers.put(key, properties.get(key));
			}
		}
	}
}
