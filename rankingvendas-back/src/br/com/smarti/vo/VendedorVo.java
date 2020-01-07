package br.com.smarti.vo;

import java.io.Serializable;
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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Proxy;
import org.json.JSONObject;

import br.com.smarti.enumeration.Acao;
import br.com.smarti.enumeration.TipoPessoa;

/**
 * @author flavius.filipe
 */
@Entity
@Table(name = "TB_Vendedor", schema = "manager")
@Proxy(lazy = true)
public class VendedorVo implements Vo, Serializable {

    private static final long serialVersionUID = -5083745667693878647L;

    public VendedorVo(JSONObject dados, String acao) {
	setAuditoriaVo(new AuditoriaVo());
	setPessoaVo(new PessoaVo());
	if (Acao.ACAO_ALTERAR.getValor().equals(acao)) {
	    setId(new Long(dados.get("idVendedor").toString()));
	    getPessoaVo().setId(new Long(dados.get("idPessoa").toString()));
	}

	getPessoaVo().setNome(dados.get("nome").toString());
	getPessoaVo().setTipo(TipoPessoa.VENDEDOR.getValor());
	getPessoaVo().setTelefone(dados.get("telefone").toString());
    }

    public VendedorVo() {
    }

    @Id
    @Column(name = "IdVendedor")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_Vendedor")
    @SequenceGenerator(name = "SQ_Vendedor", sequenceName = "manager.SQ_Vendedor", schema = "manager", allocationSize = 1)
    private Long id;

    @ForeignKey(name = "FK_Vendedor_Pessoa")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPessoa")
    private PessoaVo pessoaVo;

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

    public PessoaVo getPessoaVo() {
	return pessoaVo;
    }

    public void setPessoaVo(PessoaVo pessoaVo) {
	this.pessoaVo = pessoaVo;
    }

    public Map<String, Object> getFiltro() {
	return filtro;
    }

    public void setFiltro(Map<String, Object> filtro) {
	this.filtro = filtro;
    }

}
