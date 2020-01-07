package br.com.smarti.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Proxy;
import org.json.JSONObject;

import br.com.smarti.dao.VendedorDAO;
import br.com.smarti.enumeration.Acao;
import br.com.smarti.util.DataUtil;
import br.com.smarti.validacao.ValidacaoUtil;

/**
 * @author flavius.filipe
 */
@Entity
@Table(name = "TB_Venda", schema = "manager")
@Proxy(lazy = true)
public class VendaVo implements Vo, Serializable {

    private static final long serialVersionUID = -6787577859063947963L;

    public VendaVo() {
    }

    public VendaVo(JSONObject dados, String acao) throws Exception {

	if (Acao.ACAO_ALTERAR.getValor().equals(acao)) {
	    setId(new Long(dados.get("idVenda").toString()));
	}
	setDataEntrega(DataUtil.stringToDate(dados.get("dataEntrega").toString()));
	setDataVenda(DataUtil.stringToDate(dados.get("dataVenda").toString()));
	setEnderecoCliente(dados.get("enderecoCliente").toString());
	setNomeCliente(dados.get("nomeCliente").toString());
	setCPFTitular(dados.get("CPFTitular").toString());
	setNumeroProposta(new Long(dados.get("numeroProposta").toString()));
	setQtdeVidas(new Long(dados.get("qtdeVidas").toString()));
	setTelefoneCliente(dados.get("telefoneCliente").toString());
	setValor(new BigDecimal(dados.get("valor").toString().replace(".", "").replace(",", ".")));

	VendedorVo vendedorVo = new VendedorVo();
	vendedorVo.setId(new Long(dados.get("idVendedor").toString()));
	vendedorVo = VendedorDAO.getInstancia().get(vendedorVo, 0);

	if (ValidacaoUtil.isPreenchido(vendedorVo)) {
	    setVendedorVo(vendedorVo);
	} else {
	    throw new Exception("Vendedor não encontrado");
	}

	setAuditoriaVo(new AuditoriaVo());
    }

    @Id
    @Column(name = "IdVenda")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_Venda")
    @SequenceGenerator(name = "SQ_Venda", sequenceName = "manager.SQ_Venda", schema = "manager", allocationSize = 1)
    private Long id;

    @Column(name = "NumeroProposta", nullable = false)
    private Long numeroProposta;

    @Size(max = 120)
    @Column(name = "NomeCliente", nullable = false)
    @ColumnTransformer(write = "manager.caracteres_especiais(?)")
    private String nomeCliente;

    @Size(max = 14)
    @Column(name = "CPFTitular", nullable = false)
    private String CPFTitular;

    @Size(max = 240)
    @Column(name = "EnderecoCliente", nullable = false)
    @ColumnTransformer(write = "manager.caracteres_especiais(?)")
    private String enderecoCliente;

    @Size(max = 15)
    @Column(name = "TelefoneCliente", nullable = false)
    private String telefoneCliente;

    @Column(name = "DataVenda")
    private Date dataVenda;

    @Column(name = "DataEntrega")
    private Date dataEntrega;

    @Column(name = "QtdeVidas")
    private Long qtdeVidas;

    @Column(name = "Valor")
    private BigDecimal valor;

    @ForeignKey(name = "FK_Vendedor_Pessoa")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdVendedor")
    private VendedorVo vendedorVo;

    @Embedded
    private AuditoriaVo auditoriaVo;

    @Transient
    private Map<String, Object> filtro;

    public void inicializar() {
	setAuditoriaVo(new AuditoriaVo());
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public AuditoriaVo getAuditoriaVo() {
	return auditoriaVo;
    }

    public void setAuditoriaVo(AuditoriaVo auditoriaVo) {
	this.auditoriaVo = auditoriaVo;
    }

    public String getNomeCliente() {
	return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
	this.nomeCliente = nomeCliente;
    }

    public String getTelefoneCliente() {
	return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
	this.telefoneCliente = telefoneCliente;
    }

    public Date getDataVenda() {
	return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
	this.dataVenda = dataVenda;
    }

    public Date getDataEntrega() {
	return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
	this.dataEntrega = dataEntrega;
    }

    public BigDecimal getValor() {
	return valor;
    }

    public void setValor(BigDecimal valor) {
	this.valor = valor;
    }

    public VendedorVo getVendedorVo() {
	return vendedorVo;
    }

    public void setVendedorVo(VendedorVo vendedorVo) {
	this.vendedorVo = vendedorVo;
    }

    public Long getNumeroProposta() {
	return numeroProposta;
    }

    public void setNumeroProposta(Long numeroProposta) {
	this.numeroProposta = numeroProposta;
    }

    public Long getQtdeVidas() {
	return qtdeVidas;
    }

    public void setQtdeVidas(Long qtdeVidas) {
	this.qtdeVidas = qtdeVidas;
    }

    public String getCPFTitular() {
	return CPFTitular;
    }

    public void setCPFTitular(String CPFTitular) {
	this.CPFTitular = CPFTitular;
    }

    public String getEnderecoCliente() {
	return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
	this.enderecoCliente = enderecoCliente;
    }

    public Map<String, Object> getFiltro() {
	return filtro;
    }

    public void setFiltro(Map<String, Object> filtro) {
	this.filtro = filtro;
    }

}
