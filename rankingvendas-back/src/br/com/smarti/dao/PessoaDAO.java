package br.com.smarti.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.smarti.vo.PessoaVo;

/**
 * @author flavius.filipe
 */
public class PessoaDAO extends DAO<PessoaVo> {

    private static PessoaDAO instancia = null;

    public static PessoaDAO getInstancia() {
	if (instancia == null) {
	    instancia = new PessoaDAO();
	}
	return instancia;
    }

    private static Map<Object, Object> filtroPropriedade = new HashMap<Object, Object>();

    private static Map<Object, Object> restritores = new HashMap<Object, Object>();

    @Override
    protected Map<Object, Object> mapFiltro() {
	return filtroPropriedade;
    }

    @Override
    protected Map<Object, Object> mapRestritor() {
	return restritores;
    }

    private PessoaDAO() {
	super.inicializar();
    }

}
