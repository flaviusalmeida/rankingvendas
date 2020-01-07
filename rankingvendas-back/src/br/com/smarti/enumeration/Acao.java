package br.com.smarti.enumeration;

import javax.faces.model.SelectItem;

/**
 * @author flavius.filipe
 */
public enum Acao {
    ACAO_INSERIR("1", "ATIVO"), ACAO_ALTERAR("0", "INATIVO");

    private final String valor;
    private final String descricao;

    Acao(String valor, String descricao) {
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
	SelectItem[] items = new SelectItem[Situacao.values().length];
	int i = 0;

	for (Situacao item : Situacao.values()) {
	    items[i++] = new SelectItem(item.getValor(), item.getDescricao());
	}
	return items;
    }

    public static String getDescricao(String valor) {
	for (Situacao item : Situacao.values()) {
	    if (item.getValor().equals(valor)) {
		return item.getDescricao();
	    }
	}
	return null;
    }
}
