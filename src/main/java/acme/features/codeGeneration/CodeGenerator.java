package acme.features.codeGeneration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CodeGenerator")
public class CodeGenerator {

	@Autowired
	protected CodeRepository repo;

    public String newCode(String className) {

		String lastCode = repo.getLastCode(className);

		if (lastCode == null) {
			return "A000";
		}

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

		
		LastCode entry = repo.getEntry(className);
		if (entry == null) {
			entry = new LastCode();
			entry.className = className;
		}
		entry.lastCode = res;
		repo.save(entry);

		return res;

	}
}