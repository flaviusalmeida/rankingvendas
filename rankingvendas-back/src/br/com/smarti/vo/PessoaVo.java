package br.com.smarti.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.Email;

/**
 * @author flavius.filipe
 */
@Entity
@Table(name = "TB_Pessoa", schema = "manager")
@Proxy(lazy = true)
public class PessoaVo implements Vo, Serializable {

    private static final long serialVersionUID = -8180480430016870700L;

    @Id
    @Column(name = "IdPessoa")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_Pessoa")
    @SequenceGenerator(name = "SQ_Pessoa", sequenceName = "manager.SQ_Pessoa", schema = "manager", allocationSize = 1)
    private Long id;

    @Size(max = 120)
    @Column(name = "Nome", nullable = false)
    @ColumnTransformer(write = "manager.caracteres_especiais(?)")
    private String nome;

    @Email(message = "O e-mail informado não é válido.")
    @Size(max = 100)
    @Column(name = "Email")
    @ColumnTransformer(write = "manager.caracteres_especiais(?)")
    private String email;

    @Size(max = 15)
    @Column(name = "Telefone", nullable = false)
    private String telefone;

    @Size(max = 1)
    @Column(name = "Tipo", nullable = false)
    @ColumnTransformer(write = "manager.caracteres_especiais(?)")
    private String tipo;

    @ManyToMany(mappedBy = "pessoaVo", fetch = FetchType.LAZY)
    private Set<VendedorVo> vendedorVo;

    public void inicializar() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getTelefone() {
	return telefone;
    }

    public void setTelefone(String telefone) {
	this.telefone = telefone;
    }

    public String getTipo() {
	return tipo;
    }

    public void setTipo(String tipo) {
	this.tipo = tipo;
    }

    public Set<VendedorVo> getVendedorVo() {
	return vendedorVo;
    }

    public void setVendedorVo(Set<VendedorVo> vendedorVo) {
	this.vendedorVo = vendedorVo;
    }

}
