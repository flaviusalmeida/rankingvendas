package br.com.smarti.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.smarti.configuration.RestritorHb;
import br.com.smarti.vo.PessoaVo;
import br.com.smarti.vo.VendedorVo;

/**
 * @author flavius.filipe
 */
public class VendedorDAO extends DAO<VendedorVo> {

    private static int j = 1;
    public static final int JOIN_AUDITORIA = j *= 2;
    public static final int JOIN_PESSOA = j *= 2;

    private static VendedorDAO instancia = null;

    public static VendedorDAO getInstancia() {
	if (instancia == null) {
	    instancia = new VendedorDAO();
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

    private VendedorDAO() {
	super.inicializar();

	addFilter("pessoaVo.nome", RestritorHb.RESTRITOR_LIKE, "filtro.nome");
	addFilter("pessoaVo.tipo", RestritorHb.RESTRITOR_EQ, "filtro.tipo");
    }

    @Override
    protected void setJoin(Session session, Criteria criteria, VendedorVo filter, int join) {
	if ((join & JOIN_PESSOA) != 0) {
	    criteria.createCriteria("pessoaVo", "pessoaVo", Criteria.INNER_JOIN);
	}
	if ((join & JOIN_AUDITORIA) != 0) {
	    criteria.createCriteria("auditoriaVo", "auditoriaVo", Criteria.INNER_JOIN);
	}
    }

    @Override
    protected void preparaInserir(Session sessao, VendedorVo vo) throws Exception {
	PessoaVo pessoaVo = PessoaDAO.getInstancia().inserir(sessao, vo.getPessoaVo());
	vo.setPessoaVo(pessoaVo);
    }

    @Override
    protected void preparaAlterar(Session sessao, VendedorVo vo) throws Exception {
	PessoaVo pessoaVo = PessoaDAO.getInstancia().alterar(vo.getPessoaVo());
	vo.setPessoaVo(pessoaVo);
    }

}
