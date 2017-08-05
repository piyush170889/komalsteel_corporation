package co.in.replete.komalindustries.webcontroller.taghandlers;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.dao.UserManagementDAO;
import co.in.replete.komalindustries.utils.UDValues;
import co.in.replete.komalindustries.webcontroller.beans.WUserDetailsTO;

public class UserDetails extends SimpleTagSupport {
	
	private UserManagementDAO userDAO;

	private String searchBy;
	
	private String searchValue;
	
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setUserDAO(UserManagementDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
    public void doTag() throws JspException,IOException
    {
    	JspWriter out=getJspContext().getOut();
    	List<WUserDetailsTO> userDetailsList = null;
    	try{
    		if(null!=searchBy && !searchBy.isEmpty() || null != searchValue && !searchValue.isEmpty()) {
    			String sql_1 = "select distinct * from user_dtls ud inner join user_login_dtls uld on ud.TRACK_ID=uld.TRACK_ID and ";
    			String sql_2 = " left join other_address_details as oad on ud.TRACK_ID=oad.TRACK_ID and oad.ADDRESS_TYPE='" + UDValues.ADDRES_TYPE_DEFAULT_SHIPPING.toString() 
    			+ "' left join user_distributors_list as udl on udl.DEALER_TRACK_ID=ud.TRACK_ID order by ud.CREATED_TS desc limit 50";
    			String sql = "";
    			
    			if(searchBy.equals("name")) {
    				sql = sql_1 + "ud.FIRST_NAME LIKE '%" + searchValue + "%'" + sql_2;
    			} else if(searchBy.equals("companyName")) {
    				sql = sql_1 + "ud.DISPLAY_NAME LIKE '%" + searchValue + "%'" + sql_2;
    			} else if(searchBy.equals("panno")) {
    				sql = sql_1 + "ud.PAN_NO LIKE '%" + searchValue + "%'" + sql_2;
    			} else if(searchBy.equals("vatno")) {
    				sql = sql_1 + "ud.VAT_TIN_NO LIKE '%" + searchValue + "%'" + sql_2;
    			} else if(searchBy.equals("cntcNo")) {
    				sql = sql_1 + "ud.CNTC_NUM LIKE '%" + searchValue + "%'" + sql_2;
    			} else if(searchBy.equals("emailId")) {
    				sql = sql_1 + "uld.LOGIN_ID LIKE '%" + searchValue + "%'" + sql_2;
    			} else if(searchBy.equalsIgnoreCase("InactiveUsers")) {
    				String dateRange[] = searchValue.split("-");
    				String startDate = dateRange[0].trim();
    				String endDate = dateRange[1].trim();
    				sql = sql_1 + "uld.STATUS='Inactive' and DATE_FORMAT(uld.CREATED_TS,'%m/%d/%Y') >='" + startDate + "' and DATE_FORMAT(uld.CREATED_TS,'%m/%d/%Y')<='" + endDate + "'" + sql_2;
    			} else if (searchBy.equals("gstNo")) {
    				sql = sql_1 + "ud.GSTNO LIKE '%" + searchValue + "%'" + sql_2;
    			}
//    			System.out.println("SQL QUERY : " + sql);
    			userDetailsList = userDAO.selectWuserDetailsByCriteria(sql);
    		} else {
    			userDetailsList = userDAO.selectWuserDetails();
    		}
    		
//    		System.out.println(userDetailsList.size());
			if(userDetailsList.size() > 0) {
				int i=1;
				for(WUserDetailsTO userDetails : userDetailsList) {
					out.println("<tr>");
					out.println("<td>"+ i++ +"</td>");
					out.println("<td>" +"<a href='userdetails?trackid="+ userDetails.getUserTrackid()+ "'>"+ userDetails.getUserFirstName()+" "+userDetails.getUserLastName() +"</a>"+ "</td>");
					/*out.println("<td>" + null == userDetails.getDisplayName() ? "" : userDetails.getDisplayName() + "</td>");*/
					out.println("<td>" + userDetails.getDisplayName() + "</td>");
					out.println("<td>" + userDetails.getStatus() + "</td>");
					/*out.println("<td>" + userDetails.getPanNo() + "</td>");
					out.println("<td>" + userDetails.getVatNo() + "</td>");*/
					//TODO: Dont sow null values
					/*out.println("<td>" + "null" == userDetails.getGstNo() ? "" : userDetails.getGstNo() + "</td>");*/
					out.println("<td>" + userDetails.getGstNo() + "</td>");
					out.println("<td>" + userDetails.getDiscount() + "</td>");
					out.println("<td>" + userDetails.getContactNo() + "</td>");
				    out.println("<td>" + userDetails.getEmailId() + "</td>");
					out.println("<td>" + userDetails.getUserType() + "</td>");
					out.println("<td>" + userDetails.getRegDate() + "</td>");
					out.println("<td>");
					out.print("<a data-toggle=\"modal\" data-target=\"#editUser\" onClick=\"sendEditUserValues('"
							+ userDetails.getUserTrackid() + "','"
							+ userDetails.getUserFirstName() + "','"
							+ userDetails.getPanNo() + "','"
							+ userDetails.getUserLastName() + "','"
							+ userDetails.getDisplayName() + "','"
							+ userDetails.getStatus() + "','"
							+ userDetails.getVatNo() + "','"
							+ userDetails.getContactNo() + "','"
							+ userDetails.getUserType() + "','"
							+ userDetails.getStAddress1() + "','"
							+ userDetails.getState() + "','"
							+ userDetails.getCity() + "','"
							+ userDetails.getPincode() + "','"
							+ userDetails.getAssociatedDistributor() + "','"
							+ userDetails.getOtherAddressDtlsId() + "','"
							+ userDetails.getUserDistributorListId() + "','"
							+ userDetails.getMark() + "','"
							+ userDetails.getDestination() + "','"
							+ userDetails.getTransportName() + "','"
							+ userDetails.getGstNo() + "','"
							+ userDetails.getDiscount() + "'"
							+ ")\"><i class=\"fa fa-pencil\"></i></a>");
//					out.print("&nbsp&nbsp&nbsp&nbsp<a href=\"deleteUser?trackId=" +userDetails.getUserTrackid() + "\" onclick=\"return confirm('Are you sure?')\"><i class=\"fa fa-trash\"></i></a>");
					out.print("</td>");
					out.println("</tr>");
    	       }
			}
    	}
    	catch (DataAccessException dae) {
			dae.printStackTrace();
			out.println("-11");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("-11");
		}

    }
}
