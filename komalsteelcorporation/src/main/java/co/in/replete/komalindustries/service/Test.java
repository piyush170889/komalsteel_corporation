package co.in.replete.komalindustries.service;

import java.math.BigDecimal;

public class Test {

	public static void main(String[] args) {
		/*String firstString ="<html><head><title>Order Details from App</title></head><body><h1>New Order Placed from:</h1><ul><li><span>Company Name: </span><span>%s</span></li><li><span>Person Name: </span><span%s %s</span></li><li><span>Contact No. : </span><span>%s</span></li><li><span>Order Id : </span><span>%s</span></li></ul><h4>Order Details are as follows: </h4><table border=1 cellpadding=\"0\" cellspacing=\"0\"><tr><td>Item name</td><td>Item Type</td><td>Item Quantity</td><tr><tbody>";
		String lastString = "</tbody></table><p><h4>To:</h4><p>Mark: <span>%s</span></p><p>Destination: <span>%s</span></p><p>Track Name: <span>%s</span></p></p></body></html>";
		
		String finalString = firstString + lastString;
		CartDAO cartDAO = new CartDAOImpl();
		UserDetailsTO userDetails = cartDAO.selectUserDetails("9a31ba9d-9631-11e6-9cfc-525400f54f71");
		System.out.println(String.format(finalString, userDetails.getDisplayName(), userDetails.getFirstName(), userDetails.getLastName(), 
				userDetails.getCntc_num(), 1, "SEPT001", "kbksdj", "sdhvjhs"));*/
		
/*		String gst = "27ABQPH2220Q1Z7";
		getGstCode(gst);*/

		BigDecimal bd = new BigDecimal("25");
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        String setValue = Double.toString(bd.doubleValue());
        String[] setValueArr = setValue.toString().split("\\.");
        if (setValueArr[1].length()==1) {
        	setValue += "0";
        }
        System.out.println(setValue);
	}
	
	public static String getGstCode(String gstNo) {
		System.out.println("Gst No.: " + gstNo + ", GstCode: " + gstNo.substring(0, 2));
		return gstNo.substring(0, 2);
	}

}
