package br.com.smarti.configuration;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.smarti.util.DataUtil;

/**
 * Restritores do Hibernate
 * 
 * @author flavius.filipe
 */
@SuppressWarnings("rawtypes")
public interface RestritorHb {
    /**
     * @param criteria
     * @param valor
     *            Valor do campo
     * @param propriedade
     *            Nome do campo no mapeamento
     */
    public void restringir(Criteria criteria, Object valor, String propriedade);

    public static final RestritorHb RESTRITOR_LT = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    criteria.add(Restrictions.lt(propriedade, valor));
	}
    };

    public static final RestritorHb RESTRITOR_LE = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    if (valor instanceof Date) {
		valor = DataUtil.truncFinalDate((Date) valor);
	    }
	    criteria.add(Restrictions.le(propriedade, valor));
	}
    };

    public static final RestritorHb RESTRITOR_GT = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    criteria.add(Restrictions.gt(propriedade, valor));
	}
    };

    public static final RestritorHb RESTRITOR_GE = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    if (valor instanceof Date) {
		valor = DataUtil.truncDate((Date) valor);
	    }
	    criteria.add(Restrictions.ge(propriedade, valor));
	}
    };

    public static final RestritorHb RESTRITOR_EQ = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    criteria.add(Restrictions.eq(propriedade, valor));
	}
    };

    public static final RestritorHb RESTRITOR_LIKE = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    criteria.add(Restrictions.like(propriedade, "%" + valor + "%"));
	}
    };

    public static final RestritorHb RESTRITOR_IN = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    if (valor instanceof Collection) {
		criteria.add(Restrictions.in(propriedade, (Collection) valor));
	    } else {
		if (valor instanceof Object[]) {
		    criteria.add(Restrictions.in(propriedade, (Object[]) valor));
		}
	    }
	}
    };

    public static final RestritorHb RESTRITOR_NULL = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    if (valor instanceof Boolean) {
		if (((Boolean) valor).booleanValue()) {
		    criteria.add(Restrictions.isNull(propriedade));
		}
	    }
	}
    };

    public static final RestritorHb RESTRITOR_NOT_EQ = new RestritorHb() {
	public void restringir(Criteria criteria, Object valor, String propriedade) {
	    criteria.add(Restrictions.ne(propriedade, valor));
	}
    };
}