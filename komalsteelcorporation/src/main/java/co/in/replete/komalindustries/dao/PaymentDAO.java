package co.in.replete.komalindustries.dao;

import co.in.replete.komalindustries.beans.PayUMoneyResponseDetails;

public interface PaymentDAO {
	
	public void updatePaymentDetails(PayUMoneyResponseDetails response) throws Exception;

}
