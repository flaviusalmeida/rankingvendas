package br.com.smarti.validacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.smarti.vo.Vo;

/**
 * Classe responsável pela validação de campo
 * 
 * @author flavius.filipe
 */
public class ValidacaoUtil {

    /**
     * Verifica se o campo está preenchido
     */
    public static boolean isPreenchido(Object objeto) {
	if (objeto instanceof Collection<?>) {
	    return objeto != null && !((Collection<?>) objeto).isEmpty();
	}
	if (objeto instanceof List<?>) {
	    return objeto != null && !((List<?>) objeto).isEmpty();
	}
	if (objeto instanceof ArrayList<?>) {
	    return objeto != null && !((ArrayList<?>) objeto).isEmpty();
	}
	if (objeto instanceof String[]) {
	    return objeto != null && ((String[]) objeto).length > 0;
	}
	if (objeto instanceof Object[]) {
	    return objeto != null && ((Object[]) objeto).length > 0;
	}
	if (objeto instanceof Vo) {
	    return objeto != null && ((Vo) objeto).getId() != null;
	}
	return (objeto != null && !"".equals(objeto));
    }

    /**
     * Verifica se o CPF é válido
     */
    public static boolean validarCPF(String cpf) {
	if (Pattern.matches("(\\d{3}).(\\d{3}).(\\d{3})-(\\d{2})$", cpf)) {
	    Pattern pattern = Pattern.compile("(\\d{3}).(\\d{3}).(\\d{3})-(\\d{2})$");
	    Matcher matcher = pattern.matcher(cpf);
	    cpf = matcher.replaceAll("$1$2$3$4");
	}
	if (!Pattern.matches("(\\d{3})(\\d{3})(\\d{3})(\\d{2})$", cpf))
	    return false;

	int soma = 0;
	if (cpf.replaceAll(String.valueOf(cpf.charAt(0)), "").equalsIgnoreCase(""))
	    return false;
	for (int i = 0; i < 9; i++) {
	    soma += (10 - i) * (cpf.charAt(i) - '0');
	}
	soma = 11 - (soma % 11);
	if (soma > 9)
	    soma = 0;
	if (soma == (cpf.charAt(9) - '0')) {
	    soma = 0;
	    for (int i = 0; i < 10; i++) {
		soma += (11 - i) * (cpf.charAt(i) - '0');
	    }
	    soma = 11 - (soma % 11);
	    if (soma > 9)
		soma = 0;
	    if (soma == (cpf.charAt(10) - '0'))
		return true;
	}
	return false;
    }

    /**
     * Verifica se CNPJ é válido
     */
    public static boolean validarCNPJ(String cnpj) {
	if (Pattern.matches("(\\d{2}).(\\d{3}).(\\d{3})/(\\d{4})-(\\d{2})$", cnpj)) {
	    Pattern pattern = Pattern.compile("(\\d{2}).(\\d{3}).(\\d{3})/(\\d{4})-(\\d{2})$");
	    Matcher matcher = pattern.matcher(cnpj);
	    cnpj = matcher.replaceAll("$1$2$3$4$5");
	}
	if (!Pattern.matches("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})$", cnpj))
	    return false;

	int soma = 0;
	for (int i = 0, j = 5; i < 12; i++) {
	    soma += j-- * (cnpj.charAt(i) - '0');
	    if (j < 2)
		j = 9;
	}
	soma = 11 - (soma % 11);
	if (soma > 9)
	    soma = 0;
	if (soma == (cnpj.charAt(12) - '0')) {
	    soma = 0;
	    for (int i = 0, j = 6; i < 13; i++) {
		soma += j-- * (cnpj.charAt(i) - '0');
		if (j < 2)
		    j = 9;
	    }
	    soma = 11 - (soma % 11);
	    if (soma > 9)
		soma = 0;
	    if (soma == (cnpj.charAt(13) - '0'))
		return true;
	}
	return false;
    }
}