package br.com.smarti.vo;

import java.io.Serializable;

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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Proxy;

/**
 * @author flavius.filipe
 */
@Entity
@Table(name = "TB_Supervisor", schema = "manager")
@Proxy(lazy = true)
public class SupervisorVo implements Vo, Serializable {

    private static final long serialVersionUID = -5083745667693878647L;

    @Id
    @Column(name = "IdSupervisor")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_Supervisor")
    @SequenceGenerator(name = "SQ_Supervisor", sequenceName = "manager.SQ_Supervisor", schema = "manager", allocationSize = 1)
    private Long id;

    @ForeignKey(name = "FK_Supervisor_Pessoa")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdPessoa")
    private PessoaVo pessoaVo;

    @Embedded
    private AuditoriaVo auditoriaVo;

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

}
