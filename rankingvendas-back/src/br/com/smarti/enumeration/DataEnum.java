package br.com.smarti.enumeration;

/**
 * @author flavius.filipe
 */
public class DataEnum {

    private String valor;
    private String descricao;

    public DataEnum(String valor, String descricao) {
	setValor(valor);
	setDescricao(descricao);
    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }
}