package br.com.smarti.enumeration;

import javax.faces.model.SelectItem;

/**
 * @author flavius.filipe
 */
public enum Mensagens {
    SUCESSO_CADASTRO("{param} cadastrado com sucesso.", "Registro cadastrado com sucesso."), SUCESSO_ALTERACAO(
	    "{param} alterado com sucesso.", "Registro alterado com sucesso."), SUCESSO_EXCLUSAO(
		    "{param} excluido com sucesso.", "Registro excluido com sucesso."), SUCESSO_INATIVACAO(
			    "{param} inativado com sucesso.", "Registro inativado com sucesso."), SUCESSO_ATIVACAO(
				    "{param} ativado com sucesso.", "Registro ativado com sucesso."), ERRO_CADASTRO(
					    "Ocorreu um erro ao tentar cadastrar o(a) {param}.",
					    "Ocorreu um erro ao tentar cadastrar o registro."), ERRO_ALTERACAO(
						    "Ocorreu um erro ao tentar alterar o(a) {param}.",
						    "Não foi possível alterar o registro."), ERRO_EXCLUSAO(
							    "Ocorreu um erro ao tentar excluir o(a) {param}.",
							    "Não foi possível excluir o registro."), ERRO_INATIVACAO(
								    "Ocorreu um erro ao tentar inativar o(a) {param}.",
								    "Não foi possível inativar o registro."), ERO_ATIVACAO(
									    "Ocorreu um erro ao tentar ativar o(a) {param}.",
									    "Não foi possível ativar o registro."), ERRO_INESPERADO(
										    "{param}",
										    "Ocorreu um erro inesperado. Contacte o adminstrador do sistema."), CONSULTA_SEM_RESULTADOS(
											    "{param}",
											    "Sua consulta não retornou resultados.");

    private final String descricaoDinamica;
    private final String descricao;

    Mensagens(String valor, String descricaoDinamica) {
	this.descricaoDinamica = valor;
	this.descricao = descricaoDinamica;
    }

    public String getDescricaoDinamica(String palavra) {
	return descricaoDinamica.replace("{param}", palavra);
    }

    public String getDescricao() {
	return descricao;
    }

    public static SelectItem[] getItemValues() {
	SelectItem[] items = new SelectItem[Situacao.values().length];
	int i = 0;

	for (Situacao item : Situacao.values()) {
	    items[i++] = new SelectItem(item.getValor(), item.getDescricao());
	}
	return items;
    }

    public static String getDescricao(String valor) {
	for (Situacao item : Situacao.values()) {
	    if (item.getValor().equals(valor)) {
		return item.getDescricao();
	    }
	}
	return null;
    }
}