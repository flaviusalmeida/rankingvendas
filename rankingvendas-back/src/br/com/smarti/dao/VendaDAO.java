package br.com.smarti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.smarti.configuration.RestritorHb;
import br.com.smarti.factory.ConnectionFactory;
import br.com.smarti.objectJson.RankingJson;
import br.com.smarti.util.DataUtil;
import br.com.smarti.vo.VendaVo;

/**
 * @author flavius.filipe
 */
public class VendaDAO extends DAO<VendaVo> {

    static Logger logger = Logger.getLogger(VendaDAO.class);

    private static int j = 1;
    public static final int JOIN_AUDITORIA = j *= 2;
    public static final int JOIN_VENDEDOR = j *= 2;
    public static final int JOIN_PESSOA = j *= 2;

    private static VendaDAO instancia = null;

    public static VendaDAO getInstancia() {
	if (instancia == null) {
	    instancia = new VendaDAO();
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

    private VendaDAO() {
	super.inicializar();

	addFilter("vendedorVo.id", RestritorHb.RESTRITOR_EQ, "filtro.idVendedor");
	addFilter("dataVenda", RestritorHb.RESTRITOR_GE, "filtro.dataVendaInicial");
	addFilter("dataVenda", RestritorHb.RESTRITOR_LE, "filtro.dataVendaFinal");
	addFilter("dataEntrega", RestritorHb.RESTRITOR_GE, "filtro.dataEntregaInicial");
	addFilter("dataEntrega", RestritorHb.RESTRITOR_LE, "filtro.dataEntregaFinal");
	addFilter("nomeCliente", RestritorHb.RESTRITOR_LIKE, "filtro.nomeCliente");
    }

    @Override
    protected void setJoin(Session session, Criteria criteria, VendaVo filter, int join) {
	if ((join & JOIN_VENDEDOR) != 0) {
	    criteria.createCriteria("vendedorVo", "vendedorVo", Criteria.INNER_JOIN);
	    if ((join & JOIN_PESSOA) != 0) {
		criteria.createCriteria("vendedorVo.pessoaVo", "pessoaVo", Criteria.INNER_JOIN);
	    }
	}
	if ((join & JOIN_AUDITORIA) != 0) {
	    criteria.createCriteria("auditoriaVo", "auditoriaVo", Criteria.INNER_JOIN);
	}
    }

    public ArrayList<RankingJson> getRanking() throws Exception {
	Connection conn = null;
	ArrayList<RankingJson> ranking = new ArrayList<RankingJson>();
	try {
	    conn = ConnectionFactory.getConnection();

	    Date dataInicial = DataUtil.getPrimeiroDia(new Date());
	    Date dataFinal = DataUtil.getUltimoDia(new Date());
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	    StringBuffer sql = new StringBuffer();

	    sql.append(
		    " select SUM(qtdeVidas) as qtdeVidas, pessoa.nome as nome, SUM(venda.valor) as valorTotal,  venda.idVendedor as idVendedor ");
	    sql.append(" from manager.tb_venda venda ");
	    sql.append(" inner join manager.tb_vendedor vendedor on vendedor.idVendedor = venda.idVendedor ");
	    sql.append(" inner join manager.tb_pessoa pessoa on pessoa.idPessoa = vendedor.idPessoa ");
	    sql.append(" where dataVenda BETWEEN '" + DataUtil.truncDate(sdf.format(dataInicial)) + "' and '"
		    + DataUtil.truncFinalDate(sdf.format(dataFinal)) + "'");
	    sql.append(" group by venda.idVendedor, pessoa.nome ");
	    sql.append(" order by SUM(qtdeVidas) desc , SUM(venda.valor) desc ");

	    PreparedStatement stmt = conn.prepareStatement(sql.toString());

	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		ranking.add(new RankingJson(rs.getBigDecimal("qtdeVidas"), rs.getBigDecimal("valorTotal"),
			rs.getString("nome"), rs.getInt("idVendedor")));
	    }
	    rs.close();
	    rs = null;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    logger.error(e);
	    try {
		conn.rollback();
	    } catch (SQLException e1) {
		logger.error(e1.getMessage());
		logger.error(e1);
	    }
	    throw e;
	} finally {
	    try {
		if (conn != null && !conn.isClosed()) {
		    conn.close();
		}
	    } catch (SQLException e) {
		logger.error(e.getMessage());
		logger.error(e);
	    }
	}
	return ranking;
    }

}
