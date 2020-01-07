package br.com.smarti.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import br.com.smarti.configuration.RestritorHb;
import br.com.smarti.configuration.SessionFactory;
import br.com.smarti.enumeration.Situacao;
import br.com.smarti.vo.AuditoriaVo;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * @author flavius.filipe
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DAO<Entity> {
    private Class classe;

    protected abstract Map mapFiltro();

    protected abstract Map mapRestritor();

    protected DAO() {
	// OBTENDO A CLASSE REFERENTE A ENTIDADE
	ParameterizedTypeImpl type = (ParameterizedTypeImpl) this.getClass().getGenericSuperclass();
	classe = (Class) type.getActualTypeArguments()[0];
    }

    protected Criteria montaCriteria(Session session, Entity filter, int join) {
	Criteria criteria = session.createCriteria(classe);

	this.setJoin(session, criteria, filter, join);

	return criteria;
    }

    /**
     * Método que adiciona os atributos da classe como filtro da consulta.
     */
    public void inicializar() {
	// Obtendo os métodos da classe
	Method[] metodos = classe.getMethods();

	String campo = null;
	Class tipo = null;

	for (int i = 0; i < metodos.length; i++) {
	    if (metodos[i].getName().indexOf("get") >= 0) {
		// Obtendo o nome do campo
		campo = metodos[i].getName().substring(3, 4).toLowerCase() + metodos[i].getName().substring(4);

		Field field = null;
		try {
		    field = classe.getDeclaredField(campo);
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}

		// Validando se o campo existe e está mapeado no banco de dados
		if (field != null && field.getAnnotation(Transient.class) == null) {
		    tipo = metodos[i].getReturnType();

		    if (tipo.equals(String.class)) {
			this.addFilter(campo, RestritorHb.RESTRITOR_LIKE, campo);
		    } else if (tipo.equals(Long.class) || tipo.equals(Integer.class) || tipo.equals(Date.class)
			    || tipo.equals(BigDecimal.class)) {
			this.addFilter(campo, RestritorHb.RESTRITOR_EQ, campo);
		    }
		}
	    }
	}
    }

    protected void setJoin(Session session, Criteria criteria, Entity filter, int join) {
    }

    protected void setMaxResults(Criteria criteria, Entity filter, int join) {
    }

    protected void setOrder(Criteria criteria, Entity filter, int join) {
    }

    protected void addFilter(String mappingFieldName, RestritorHb restriction, String voFieldName) {
	this.mapRestritor().put(voFieldName, restriction);
	this.mapFiltro().put(voFieldName, mappingFieldName);
    }

    protected void restringir(Criteria criteria, Entity filter) throws Exception {
	Set restricoes = this.mapRestritor().keySet();
	for (Iterator<String> iter = restricoes.iterator(); iter.hasNext();) {
	    String restritorNome = iter.next();
	    Object valor = null;
	    try {
		valor = PropertyUtils.getProperty(filter, restritorNome);
	    } catch (Exception exception) {
		valor = null;
	    }
	    if (this.isPrenchido(valor)) {
		RestritorHb restritorHb = (RestritorHb) this.mapRestritor().get(restritorNome);

		restritorHb.restringir(criteria, valor, (String) this.mapFiltro().get(restritorNome));
	    }
	}
    }

    public boolean isPrenchido(Object obj) {
	if (obj == null) {
	    return false;
	} else {
	    if (obj instanceof Collection) {
		return !((Collection) obj).isEmpty();
	    } else {
		return !obj.toString().equalsIgnoreCase("");
	    }
	}
    }

    public Entity inserir(Entity vo) throws Exception {
	Session sessao = SessionFactory.getSession();
	sessao.setFlushMode(FlushMode.COMMIT);
	Transaction tx = sessao.beginTransaction();
	try {
	    vo = inserir(sessao, vo);
	    tx.commit();
	    return vo;
	} finally {
	    sessao.close();
	}
    }

    public Entity inserir(Session sessao, Entity vo) throws Exception {
	preparaInserir(sessao, vo);
	sessao.save(vo);
	return vo;
    }

    protected void preparaInserir(Session sessao, Entity vo) throws Exception {
    }

    public Entity alterar(Entity vo) throws Exception {
	Session sessao = SessionFactory.getSession();
	sessao.setFlushMode(FlushMode.COMMIT);
	Transaction tx = sessao.beginTransaction();
	try {
	    vo = alterar(sessao, vo);
	    tx.commit();
	    return vo;
	} catch (StaleObjectStateException e) {
	    // Mensagem de erro para o controle de alteração @Version
	    throw new Exception("O registro não pode ser alterado pois ele está sendo utilizado por outro usuário.");
	} finally {
	    sessao.close();
	}
    }

    public Entity alterar(Session sessao, Entity vo) throws Exception {
	preparaAlterar(sessao, vo);
	sessao.merge(vo);
	return vo;
    }

    protected void preparaAlterar(Session sessao, Entity vo) throws Exception {
    }

    public Entity get(Entity filter, int join) throws Exception {
	Session sessao = SessionFactory.getSession();
	try {
	    return this.get(sessao, filter, join);
	} finally {
	    sessao.close();
	}
    }

    public Entity get(Session session, Entity filter, int join) throws Exception {
	try {
	    Criteria criteria = this.montaCriteria(session, filter, join);

	    this.setOrder(criteria, filter, join);

	    this.restringir(criteria, filter);

	    return (Entity) criteria.uniqueResult();
	} catch (NonUniqueResultException e) {
	    throw new Exception("Sua consulta retornou mais de um registro.");
	} catch (Exception e) {
	    throw e;
	}
    }

    public void excluir(Entity vo) throws Exception {
	Session sessao = SessionFactory.getSession();
	sessao.setFlushMode(FlushMode.COMMIT);
	Transaction tx = sessao.beginTransaction();
	try {
	    excluir(sessao, vo);
	    tx.commit();
	} finally {
	    sessao.close();
	}
    }

    public void excluir(Session sessao, Entity vo) throws Exception {
	preparaExcluir(sessao, vo);
	sessao.delete(vo);
    }

    protected void preparaExcluir(Session sessao, Entity vo) throws Exception {
    }

    public Collection<Entity> pesquisar(Entity filter, int join) throws Exception {
	Session sessao = SessionFactory.getSession();
	try {
	    return this.pesquisar(sessao, filter, join);
	} finally {
	    sessao.close();
	}
    }

    public Collection<Entity> pesquisar(Session session, Entity filter, int join) throws Exception {
	Criteria criteria = this.montaCriteria(session, filter, join);

	this.setOrder(criteria, filter, join);

	this.restringir(criteria, filter);

	this.setMaxResults(criteria, filter, join);

	return criteria.list();
    }

    public String alterarSituacao(Entity vo) throws Exception {
	Session sessao = SessionFactory.getSession();
	sessao.setFlushMode(FlushMode.COMMIT);
	Transaction tx = sessao.beginTransaction();
	try {
	    String retorno = this.alterarSituacao(sessao, vo);

	    tx.commit();

	    return retorno;
	} finally {
	    sessao.close();
	}
    }

    public String alterarSituacao(Session sessao, Entity vo) throws Exception {
	String situacao = alterarSituacao((AuditoriaVo) getReflexaoValor(vo, "AuditoriaVo"));

	sessao.merge(vo);

	return situacao;
    }

    public String alterarSituacao(AuditoriaVo vo) throws Exception {
	setAuditoriaAlteracaoSituacao(vo);

	if (vo.getSituacao().equals(Situacao.ATIVO.getValor())) {
	    vo.setSituacao(Situacao.INATIVO.getValor());
	    return Situacao.INATIVO.getValor();
	} else {
	    vo.setSituacao(Situacao.ATIVO.getValor());
	    return Situacao.ATIVO.getValor();
	}
    }

    public void setAuditoriaAlteracaoSituacao(AuditoriaVo vo) throws Exception {
	vo.setDataAlteracaoSituacao(new Date());
    }

    public Object getReflexaoValor(Object objetoOrigem, String nomeCampoOrigem) {
	try {
	    Method get = objetoOrigem.getClass().getMethod("get" + nomeCampoOrigem, new Class[] {});
	    Object retorno = get.invoke(objetoOrigem, new Object[] {});
	    return retorno;
	} catch (Exception e) {
	    return null;
	}
    }

    public Long getRowCount(Entity vo, int join) throws Exception {
	Session sessao = SessionFactory.getSession();
	try {
	    Criteria criteria = this.montaCriteria(sessao, vo, join);
	    this.restringir(criteria, vo);
	    ProjectionList projectionList = Projections.projectionList();
	    projectionList.add(Projections.count("id"));
	    criteria.setProjection(projectionList);
	    return (Long) criteria.uniqueResult();
	} finally {
	    sessao.close();
	}
    }
}
