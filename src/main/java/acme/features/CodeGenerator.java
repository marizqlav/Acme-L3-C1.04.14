package acme.features;

public class CodeGenerator {

    public static String newCode(String lastCode) {

		//TODO
		/* This doesn't jump between jumps
		 * Z999 -> AA000 works but AA000 -> AA001 doesn't since
		 * we're using a weird repository function that doesn't return the last code
		 * properly f you repository
		 */

		if (lastCode == "ZZZ999") {
			throw new RuntimeException("Out of codes");
		}
		
		String res = "";

		boolean adding = true;

		String code = lastCode;
		if (lastCode.length() == 4) {
			code = '_' + code;
		}
		if (lastCode.length() == 5) {
			code = '_' + code;
		}

		char[] chars = code.toCharArray();

		for (Integer i = code.length() - 1; i >= 0; i--) {

			if (!adding) {
				break;
			}

			if (chars[i] == '9') {
				chars[i] = '0';

			} else if (chars[i] == 'Z') {
				chars[i] = 'A';

			} else if (chars[i] == '_') {
				chars[i] = 'A';
				adding = false;

			} else {
				chars[i] = String.valueOf( (char) (chars[i] + 1)).charAt(0);
				adding = false;
			}
		}

		for (char c : chars) {
			if (c != '_') {
				res += c;
			}
		}

		return res;

	}
}