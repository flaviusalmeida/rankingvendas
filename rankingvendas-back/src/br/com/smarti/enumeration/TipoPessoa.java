package br.com.smarti.enumeration;

import javax.faces.model.SelectItem;

/**
 * @author flavius.filipe
 */
public enum TipoPessoa {

    VENDEDOR("1", "Vendedor"), SUPERVISOR("2", "Supervisor");

    private final String valor;
    private final String descricao;

    TipoPessoa(String valor, String descricao) {
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
	SelectItem[] items = new SelectItem[TipoPessoa.values().length];
	int i = 0;

	for (TipoPessoa item : TipoPessoa.values()) {
	    items[i++] = new SelectItem(item.getValor(), item.getDescricao());
	}
	return items;
    }

    public static String getDescricao(String valor) {
	for (TipoPessoa item : TipoPessoa.values()) {
	    if (item.getValor().equals(valor)) {
		return item.getDescricao();
	    }
	}
	return null;
    }
}
