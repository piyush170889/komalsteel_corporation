package co.in.replete.komalindustries.webcontroller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.in.replete.komalindustries.beans.PaymentDetailsRequest;
import co.in.replete.komalindustries.service.PaymentService;

@Controller
public class WPaymentController {

	@Autowired
	PaymentService paymentService;
	
	/**
	 * Description : Inserts the payment details for a transaction
	 * 
	 * @param  {@link PaymentDetailsRequest}
	 * @param  {@link HttpServletResponse}
	 * @throws {@link Exception}
	 */
	@RequestMapping(value="/wacceptpayment", method=RequestMethod.POST)
	public void acceptPayment(PaymentDetailsRequest request, HttpServletResponse servletResponse) throws Exception
	{
		System.out.println(request.toString());
		paymentService.doAcceptPayment(request, servletResponse);
	}
}
