package br.com.smarti.objectJson;

import java.math.BigDecimal;

/**
 * @author flavius.filipe
 */
public class RankingJson {

    private BigDecimal qtdeVidas;
    private BigDecimal valorTotal;
    private String nomeVendedor;
    private Integer idVendedor;

    public RankingJson(BigDecimal qtdeVidas, BigDecimal valorTotal, String nomeVendedor, Integer idVendedor) {
	this.setQtdeVidas(qtdeVidas);
	this.valorTotal = valorTotal;
	this.nomeVendedor = nomeVendedor;
	this.idVendedor = idVendedor;
    }

    public BigDecimal getValorTotal() {
	return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
	this.valorTotal = valorTotal;
    }

    public String getNomeVendedor() {
	return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
	this.nomeVendedor = nomeVendedor;
    }

    public BigDecimal getQtdeVidas() {
	return qtdeVidas;
    }

    public void setQtdeVidas(BigDecimal qtdeVidas) {
	this.qtdeVidas = qtdeVidas;
    }

    public Integer getIdVendedor() {
	return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
	this.idVendedor = idVendedor;
    }

}
