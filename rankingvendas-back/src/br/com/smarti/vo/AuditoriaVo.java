package br.com.smarti.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * Classe de Auditoria do Sistema
 * 
 * @author flavius.filipe
 */
@Embeddable
public class AuditoriaVo implements Serializable {

    private static final long serialVersionUID = 7443570200618024616L;

    @Column(name = "DataInclusao", columnDefinition = "timestamp default current_timestamp", insertable = false, updatable = false)
    private Date dataInclusao;

    @Column(name = "DataAlteracao", insertable = false)
    private Date dataAlteracao;

    @Column(name = "DataAlteracaoSituacao", insertable = false)
    private Date dataAlteracaoSituacao;

    @Size(max = 2)
    @Column(name = "Situacao", columnDefinition = "varchar(2) default '1'", insertable = false)
    private String situacao;

    public Date getDataInclusao() {
	return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
	this.dataInclusao = dataInclusao;
    }

    public Date getDataAlteracao() {
	return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
	this.dataAlteracao = dataAlteracao;
    }

    public String getSituacao() {
	return situacao;
    }

    public void setSituacao(String situacao) {
	this.situacao = situacao;
    }

    public Date getDataAlteracaoSituacao() {
	return dataAlteracaoSituacao;
    }

    public void setDataAlteracaoSituacao(Date dataAlteracaoSituacao) {
	this.dataAlteracaoSituacao = dataAlteracaoSituacao;
    }

}