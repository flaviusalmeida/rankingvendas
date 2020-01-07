package br.com.smarti.enumeration;

import javax.faces.model.SelectItem;

/**
 * @author flavius.filipe
 */
public enum ErrosLogin {

    USUARIO_INVALIDO("1", "O usuário ou a senha estão incorretos."), USUARIO_BLOQUEADO("2",
	    "O usuário bloqueado, contate o administrador do sistema."), USUARIO_NAO_CONFIRMADO("3",
		    "Usuário não confirmado. Verifique seu e-mail."), ADESAO_NAO_APROVADA("4",
			    "Adesão não aprovada. Aguarde a aprovação."), CADASTRO_DESATUALIZADO("5",
				    "Cdastro desatualizado. Para realizar a atualização cadastral acesse: https://sistema.cartos.com.br/PortalPessoaFisica.");
    private final String valor;
    private final String descricao;

    ErrosLogin(String valor, String descricao) {
	this.valor = valor;
	this.descricao = descricao;
    }

    public String getValor() {
	return valor;
    }

    public String getDescricao() {
	return descricao;
    }

    public static SelectItem[] getItemValues() {
	SelectItem[] items = new SelectItem[ErrosLogin.values().length];
	int i = 0;

	for (ErrosLogin item : ErrosLogin.values()) {
	    items[i++] = new SelectItem(item.getValor(), item.getDescricao());
	}
	return items;
    }

    public static String getDescricao(String valor) {
	for (ErrosLogin item : ErrosLogin.values()) {
	    if (item.getValor().equals(valor)) {
		return item.getDescricao();
	    }
	}
	return null;
    }
}
