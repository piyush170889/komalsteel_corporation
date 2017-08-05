package co.in.replete.komalindustries.utils;

public class GSTINValidator {

	private static final String GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}";
	private static final String GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	
	//Check GSTIN Number is valid or not
	public  boolean validateGSTIN(String gstin) throws Exception {
		boolean isValid = false;
		if (checkPattern(gstin)) {
			isValid = verifyCheckDigit(gstin);
		}
		return isValid;

	}
	
	//Method for check digit validation
	private static boolean verifyCheckDigit(String gstinWCheckDigit) throws Exception {
		Boolean isCDValid = false;
		String newGstninWCheckDigit = getGSTINWithCheckDigit(
				gstinWCheckDigit.substring(0, gstinWCheckDigit.length() - 1));

		if (gstinWCheckDigit.trim().equals(newGstninWCheckDigit)) {
			isCDValid = true;
		}
		return isCDValid;
	}
	
	//Method For Regular Expression validation
	public static boolean checkPattern(String inputval) {
		boolean result = false;
		if ((inputval.trim()).matches(GSTINFORMAT_REGEX)) {
			result = true;
		}
		return result;
	}

	private static String getGSTINWithCheckDigit(String gstinWOCheckDigit) throws Exception {
		int factor = 2;
		int sum = 0;
		int checkCodePoint = 0;
		char[] cpChars;
		char[] inputChars;

		try {
			if (gstinWOCheckDigit == null) {
				throw new Exception("GSTIN supplied for checkdigit calculation is null");
			}
			cpChars = GSTN_CODEPOINT_CHARS.toCharArray();
			inputChars = gstinWOCheckDigit.trim().toUpperCase().toCharArray();

			int mod = cpChars.length;
			for (int i = inputChars.length - 1; i >= 0; i--) {
				int codePoint = -1;
				for (int j = 0; j < cpChars.length; j++) {
					if (cpChars[j] == inputChars[i]) {
						codePoint = j;
					}
				}
				int digit = factor * codePoint;
				factor = (factor == 2) ? 1 : 2;
				digit = (digit / mod) + (digit % mod);
				sum += digit;
			}
			checkCodePoint = (mod - (sum % mod)) % mod;
			return gstinWOCheckDigit + cpChars[checkCodePoint];
		} finally {
			inputChars = null;
			cpChars = null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		GSTINValidator gstin=new GSTINValidator();
		if(gstin.validateGSTIN("21AAFFN8648C1ZI"))
		{
			System.out.println("Valid GSTIN");
		}
		else
		{
			System.out.println("Invalid GSTIN");
		}
	}
	
}
