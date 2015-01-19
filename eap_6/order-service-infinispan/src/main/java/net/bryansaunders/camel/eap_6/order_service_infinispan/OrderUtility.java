package net.bryansaunders.camel.eap_6.order_service_infinispan;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;

import org.apache.camel.Body;
import org.apache.camel.Headers;

@Named
public class OrderUtility {

	public static final String HEADER_ORIG_REQUEST = "originalRequestString";
	public static final String HEADER_US_TOTAL_COST = "totalUsCost";
	public static final String HEADER_EUR_TOTAL_COST = "totalEurCost";

	public static final Double WIDGET_UNIT_COST = 3.50;
	public static final Double TRINKET_UNIT_COST = 4.25;
	public static final Double BASE_UNIT_COST = 2.00;

	public static String storeOriginalRequest(@Body String message,
			@Headers Map<String, Object> headers) {

		headers.put(HEADER_ORIG_REQUEST, message);

		return message;
	}

	public String getTotalCost(@Body String message,
			@Headers Map<String, Object> headers) {

		String[] parts = message.split("\\|");
		String unitType = parts[0];
		Double unitCount = Double.valueOf(parts[1]);

		Double unitCost = this.getUnitCost(unitType);
		Double totalCost = unitCost * unitCount;

		headers.put(HEADER_US_TOTAL_COST, totalCost);

		System.out.println(">> Total Cost in US Dollars: " + totalCost);
		return totalCost.toString();
	}

	private Double getUnitCost(String unitType) {
		if (unitType.equalsIgnoreCase("Widget")) {
			return WIDGET_UNIT_COST;
		} else if (unitType.equalsIgnoreCase("Trinket")) {
			return TRINKET_UNIT_COST;
		} else {
			return BASE_UNIT_COST;
		}
	}

	public String getConvRateFromXml(@Body String message) {
		// Get Conversion Rate from Body with RegEx
		String convRateString = "";
		String regEx = ".*<ConversionRateResult>([0-9\\.]*)</.*";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(message);
		if (matcher.find()) {
			convRateString = matcher.group(1);
		} else {
			System.out
					.println(">> Conversion Rate Not Matched, Using Default of 1.25");
			convRateString = "1.25";
		}

		Double convRate = Double.valueOf(convRateString);
		System.out.println(">> Received Conversion Rate: " + convRate);

		return convRateString;
	}

	public String getAdjustedTotalCost(@Body String message,
			@Headers Map<String, Object> headers) {

		Double convRate = Double.valueOf(message);
		System.out.println(">> Using Conversion Rate: " + convRate);

		// Convert Rate
		Double usCost = (Double) headers.get(HEADER_US_TOTAL_COST);
		Double eurCost = usCost * convRate;

		// Store EUR Total Cost
		headers.put(HEADER_EUR_TOTAL_COST, eurCost);

		System.out.println(">> Total Cost in Euros: " + eurCost);
		return eurCost.toString();
	}

	public String appendTotalCost(@Body String message,
			@Headers Map<String, Object> headers) {

		String origRequest = (String) headers.get(HEADER_ORIG_REQUEST);
		Double usCost = (Double) headers.get(HEADER_US_TOTAL_COST);
		Double eurCost = (Double) headers.get(HEADER_EUR_TOTAL_COST);

		return origRequest + "|" + usCost + " USD|" + eurCost + " EUR";

	}
}