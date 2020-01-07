package br.com.smarti.resource;

/**
 * @author flavius.filipe
 */
public interface Resource {

    public String cadastrar(String form);

    public String pesquisar(String filtro);

    public String alterar(String registro);
}
