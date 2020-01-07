package br.com.smarti.util;

import javax.swing.text.MaskFormatter;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import br.com.smarti.validacao.ValidacaoUtil;

/**
 * @author flavius.filipe
 */
public class AppUtil {

    public static final String DIRETORIO_APPLICATION_RESSOURCE = "WEB-INF/classes/ApplicationResources.properties";
    public static final String DIRETORIO_EMAIL = "WEB-INF/classes/br/com/hmit/email";

    static Logger logger = Logger.getLogger(AppUtil.class);

    public static String formataCpf(String cpf) throws Exception {
	MaskFormatter maskFormatter = new MaskFormatter("###.###.###-##");
	maskFormatter.setValueContainsLiteralCharacters(false);
	return maskFormatter.valueToString(cpf);
    }

    public static void adicionarStatusRetorno(JSONObject json, boolean sucesso, String mensagem) {
	if (sucesso) {
	    json.put("statusRetorno", "sucesso");
	} else {
	    json.put("statusRetorno", "erro");
	}
	json.put("mensagemRetorno", mensagem);
    }

    public static void adicionarStatusRetorno(JSONObject json, Exception e, Logger logger) {
	String mensagem = ValidacaoUtil.isPreenchido(e.getMessage()) ? e.getMessage()
		: "Ocorreu um erro inesperado. Contate o administrador do sistema.";
	logger.error(e.getMessage());
	logger.error(e);
	adicionarStatusRetorno(json, false, mensagem);
    }
}