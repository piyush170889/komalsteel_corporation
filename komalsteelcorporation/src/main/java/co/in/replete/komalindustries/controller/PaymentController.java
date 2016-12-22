package co.in.replete.komalindustries.controller;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.PayUMoneyResponseDetails;
import co.in.replete.komalindustries.beans.PaymentDetailsRequest;
import co.in.replete.komalindustries.service.PaymentService;


@RestController
public class PaymentController {

	@Autowired
	PaymentService paymentService;
		
	/**
	 * Description : Inserts the payment details for a transaction
	 * 
	 * @param  {@link PaymentDetailsRequest}
	 * @param  {@link HttpServletResponse}
	 * @throws {@link Exception}
	 */
	@RequestMapping(value="/acceptpayment", method=RequestMethod.POST, consumes="application/json")
	public BaseWrapper acceptPayment(@RequestBody PaymentDetailsRequest request/*, HttpServletResponse servletResponse*/) throws Exception
	{
		System.out.println(request.toString());
		return paymentService.doAcceptPayment(request/*, servletResponse*/);
	}
	
	/**
	 * 
	 * Description : Update the payment details received from payUMoney
	 * @param response
	 * @throws {@link Exception}
	 */
	@RequestMapping(value="/updatepaymentstatus", method=RequestMethod.POST, consumes="application/x-www-form-urlencoded;charset=UTF-8")
	public void response(PayUMoneyResponseDetails request, HttpServletResponse response) throws Exception{
		
		paymentService.doUpdatePaymentDetails(request, response);
	}
	
}
