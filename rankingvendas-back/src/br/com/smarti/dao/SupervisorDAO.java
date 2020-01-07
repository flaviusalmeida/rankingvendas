package br.com.smarti.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;

import br.com.smarti.vo.PessoaVo;
import br.com.smarti.vo.SupervisorVo;

/**
 * @author flavius.filipe
 */
public class SupervisorDAO extends DAO<SupervisorVo> {

    private static SupervisorDAO instancia = null;

    public static SupervisorDAO getInstancia() {
	if (instancia == null) {
	    instancia = new SupervisorDAO();
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

    private SupervisorDAO() {
	super.inicializar();
    }

    @Override
    protected void preparaInserir(Session sessao, SupervisorVo vo) throws Exception {
	PessoaVo pessoaVo = PessoaDAO.getInstancia().inserir(sessao, vo.getPessoaVo());
	vo.setPessoaVo(pessoaVo);
    }

}
