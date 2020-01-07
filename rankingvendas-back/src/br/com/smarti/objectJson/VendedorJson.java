package br.com.smarti.objectJson;

/**
 * @author flavius.filipe
 */
public class VendedorJson {

    private Long idVendedor;
    private Long idPessoa;
    private String nome;
    private String email;
    private String telefone;
    private String tipo;

    public VendedorJson(Long idVendedor, Long idPessoa, String nome, String email, String telefone, String tipo) {
	this.idVendedor = idVendedor;
	this.idPessoa = idPessoa;
	this.nome = nome;
	this.email = email;
	this.telefone = telefone;
	this.tipo = tipo;
    }

    public Long getIdVendedor() {
	return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
	this.idVendedor = idVendedor;
    }

    public Long getIdPessoa() {
	return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
	this.idPessoa = idPessoa;
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

}
