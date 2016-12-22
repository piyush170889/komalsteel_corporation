package co.in.replete.komalindustries.beans;

public class MResetPasswordTO {

	private String cellNumber;
	
	private String otp;
	
	private String newPassword;
	
	public MResetPasswordTO() {}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
