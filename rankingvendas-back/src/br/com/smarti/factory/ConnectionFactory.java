package br.com.smarti.factory;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

/**
 * A classe ConnectionFactory encapsula a criação de uma conexão com o banco de
 * dados
 * 
 * @author flavius.filipe
 */
public class ConnectionFactory {
    private static Logger logger = Logger.getLogger(ConnectionFactory.class);

    protected ConnectionFactory() {
    }

    public static Connection getConnection() throws Exception {
	Connection conn = null;
	try {
	    Context initialCtx = new InitialContext();
	    Context envCtx = (Context) initialCtx.lookup("java:comp/env");
	    DataSource ds = (DataSource) envCtx.lookup("jdbc/PPRManager");
	    conn = ds.getConnection();

	    // Nova tentativa se a conexão tiver nula ou fechada
	    if (conn == null || conn.isClosed()) {
		try {
		    conn = ds.getConnection();
		} catch (PSQLException e) {
		    throw new Exception("Erro ao tentar fazer conexão com o banco.");
		}
		if (conn == null || conn.isClosed()) {
		    logger.error("Conexão fechada.");
		    throw new Exception("Erro ao tentar fazer conexão com o banco.");
		}
	    }
	} catch (PSQLException e) {
	    throw new Exception("Erro ao tentar fazer conexão com o banco.");
	}
	return conn;
    }
}