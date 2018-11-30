package co.in.replete.komalindustries.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {
		/*
		 * String firstString =
		 * "<html><head><title>Order Details from App</title></head><body><h1>New Order Placed from:</h1><ul><li><span>Company Name: </span><span>%s</span></li><li><span>Person Name: </span><span%s %s</span></li><li><span>Contact No. : </span><span>%s</span></li><li><span>Order Id : </span><span>%s</span></li></ul><h4>Order Details are as follows: </h4><table border=1 cellpadding=\"0\" cellspacing=\"0\"><tr><td>Item name</td><td>Item Type</td><td>Item Quantity</td><tr><tbody>"
		 * ; String lastString =
		 * "</tbody></table><p><h4>To:</h4><p>Mark: <span>%s</span></p><p>Destination: <span>%s</span></p><p>Track Name: <span>%s</span></p></p></body></html>"
		 * ;
		 * 
		 * String finalString = firstString + lastString; CartDAO cartDAO = new
		 * CartDAOImpl(); UserDetailsTO userDetails =
		 * cartDAO.selectUserDetails("9a31ba9d-9631-11e6-9cfc-525400f54f71");
		 * System.out.println(String.format(finalString,
		 * userDetails.getDisplayName(), userDetails.getFirstName(),
		 * userDetails.getLastName(), userDetails.getCntc_num(), 1, "SEPT001",
		 * "kbksdj", "sdhvjhs"));
		 */

		/*
		 * String gst = "27ABQPH2220Q1Z7"; getGstCode(gst);
		 */

		/*
		 * BigDecimal bd = new BigDecimal("25"); bd = bd.setScale(2,
		 * BigDecimal.ROUND_HALF_UP); String setValue =
		 * Double.toString(bd.doubleValue()); String[] setValueArr =
		 * setValue.toString().split("\\."); if (setValueArr[1].length()==1) {
		 * setValue += "0"; } System.out.println(setValue);
		 */

		String startDateTimeOfEventString = "2018-08-14T12:00:00Z";
		String endDateTimeOfEventString = "2018-08-14T20:00:00Z";

		String shopClosingTimeString = "18:00:00";
		String shopOpeningTimeString = "09:00:00";

		DateFormat dfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

		Date startDateTimeOfEventDate = dfISO.parse(startDateTimeOfEventString);
		Date endDateTimeOfEventDate = dfISO.parse(endDateTimeOfEventString);

		DateFormat dfHhMmSs = new SimpleDateFormat("HH:mm:ss");

		String startDateTimeOfEventTimeString = dfHhMmSs.format(startDateTimeOfEventDate);
		String endDateTimeOfEventTimeString = dfHhMmSs.format(endDateTimeOfEventDate);
		System.out.println("1. startTimeOfEvent = " + startDateTimeOfEventTimeString + "\n 2. Shop Opening Time = "
				+ shopOpeningTimeString + "\n 3. endTimeOfEvent = " + endDateTimeOfEventTimeString
				+ "\n 3. Shop Closing Time = " + shopClosingTimeString);

		Date startDateTimeOfEventTime = dfHhMmSs.parse(startDateTimeOfEventTimeString);
		Date endDateTimeOfEventTime = dfHhMmSs.parse(endDateTimeOfEventTimeString);
		Date shopOpeningTimeTime = dfHhMmSs.parse(shopOpeningTimeString);
		Date shopClosingTimeTime = dfHhMmSs.parse(shopClosingTimeString);

		System.out.println("\n\n");

		System.out.println("1. startTimeOfEvent (Date) = " + startDateTimeOfEventTime
				+ "\n 2. Shop Opening Time (Date) = " + shopOpeningTimeTime + "\n 3. endTimeOfEvent (Date) = "
				+ endDateTimeOfEventTime + "\n 3. Shop Closing Time (Date) = " + shopClosingTimeTime);
		
		System.out.println("\n\n");
		
		System.out.println("startTs - startDateTimeOfEventTime \n endts = endDateTimeOfEventTime");
		doCalc(startDateTimeOfEventTime.getTime(), endDateTimeOfEventTime.getTime(), 30);
		
		System.out.println("\n\n");
		System.out.println("startTs - startDateTimeOfEventTime \n endts = shopClosingTimeTime");
		doCalc(startDateTimeOfEventTime.getTime(), shopClosingTimeTime.getTime(), 30);
	}

	public static void doCalc(long startTs, long endTs, int durationOfMeet) {
		
		long diff = endTs - startTs;
		System.out.println("diff = "+diff+", endTs = "+endTs+",  startTs = "+startTs);
		
		long diffHours = diff / (60 * 60 * 1000) % 24;
		//long diffMinutes = diff / (60 * 1000) % 60;
		
		int mint = (int) (diffHours * 60);

		int slotsInOneDay = mint/durationOfMeet;
		
		System.out.println("slotsInOneDay = " + slotsInOneDay);
		
	}
	public static String getGstCode(String gstNo) {
		System.out.println("Gst No.: " + gstNo + ", GstCode: " + gstNo.substring(0, 2));
		return gstNo.substring(0, 2);
	}

}
