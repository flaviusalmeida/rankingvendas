package br.com.smarti.objectJson;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author flavius.filipe
 */
public class VendaJson {

    private Long idVenda;
    private Long numeroProposta;
    private String nomeCliente;
    private String CPFTitular;
    private String enderecoCliente;
    private String telefoneCliente;
    private String dataVenda;
    private String dataEntrega;
    private Long qtdeVidas;
    private String valor;
    private Long idVendedor;
    private String nomeVendedor;

    public VendaJson(Long idVenda, Long numeroProposta, String nomeCliente, String CPFTitular, String enderecoCliente,
	    String telefoneCliente, String dataVenda, String dataEntrega, Long qtdeVidas, String valor, Long idVendedor,
	    String nomeVendedor) {
	this.idVenda = idVenda;
	this.numeroProposta = numeroProposta;
	this.nomeCliente = nomeCliente;
	this.CPFTitular = CPFTitular;
	this.enderecoCliente = enderecoCliente;
	this.telefoneCliente = telefoneCliente;
	this.dataVenda = dataVenda;
	this.dataEntrega = dataEntrega;
	this.qtdeVidas = qtdeVidas;
	DecimalFormat formato = new DecimalFormat("#,##0.00");
	this.setValor(formato.format(new BigDecimal(valor)));
	this.idVendedor = idVendedor;
	this.nomeVendedor = nomeVendedor;
    }

    public Long getIdVenda() {
	return idVenda;
    }

    public void setIdVenda(Long idVenda) {
	this.idVenda = idVenda;
    }

    public Long getNumeroProposta() {
	return numeroProposta;
    }

    public void setNumeroProposta(Long numeroProposta) {
	this.numeroProposta = numeroProposta;
    }

    public String getNomeCliente() {
	return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
	this.nomeCliente = nomeCliente;
    }

    public String getCPFTitular() {
	return CPFTitular;
    }

    public void setCPFTitular(String cPFTitular) {
	CPFTitular = cPFTitular;
    }

    public String getEnderecoCliente() {
	return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
	this.enderecoCliente = enderecoCliente;
    }

    public String getTelefoneCliente() {
	return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
	this.telefoneCliente = telefoneCliente;
    }

    public Long getQtdeVidas() {
	return qtdeVidas;
    }

    public void setQtdeVidas(Long qtdeVidas) {
	this.qtdeVidas = qtdeVidas;
    }

    public Long getIdVendedor() {
	return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
	this.idVendedor = idVendedor;
    }

    public String getNomeVendedor() {
	return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
	this.nomeVendedor = nomeVendedor;
    }

    public String getDataVenda() {
	return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
	this.dataVenda = dataVenda;
    }

    public String getDataEntrega() {
	return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
	this.dataEntrega = dataEntrega;
    }

    public String getValor() {
	return valor;
    }

    public void setValor(String valor) {
	this.valor = valor;
    }

}
