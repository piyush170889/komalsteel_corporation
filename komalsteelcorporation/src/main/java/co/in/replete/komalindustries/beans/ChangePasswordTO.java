package co.in.replete.komalindustries.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordTO {

	@NotNull(message="error.oldpassword.required")
	@NotEmpty(message="error.oldpassword.required")
	private String oldPassWord;
	
	@NotNull(message="error.newpassword.required")
	@NotEmpty(message="error.newpassword.required")
	private String newPassword;
	
	public ChangePasswordTO() {	}

	public String getOldPassWord() {
		return oldPassWord;
	}

	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
