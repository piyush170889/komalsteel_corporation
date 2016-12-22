package co.in.replete.komalindustries.service;

import javax.servlet.http.HttpServletResponse;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.PayUMoneyResponseDetails;
import co.in.replete.komalindustries.beans.PaymentDetailsRequest;

public interface PaymentService {
	public BaseWrapper doAcceptPayment(PaymentDetailsRequest paymentDetailsRequest/*, HttpServletResponse servletResponse*/) throws Exception;
	
	public void doUpdatePaymentDetails(PayUMoneyResponseDetails payUMoneyResponseDetails, HttpServletResponse response) throws Exception;
	
	public BaseWrapper doAcceptPayment(PaymentDetailsRequest paymentDetailsRequest, HttpServletResponse servletResponse) throws Exception;
}
