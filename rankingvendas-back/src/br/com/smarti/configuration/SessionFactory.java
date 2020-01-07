package br.com.smarti.configuration;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Classe responsável por abrir uma sessão do Hibernate com o banco
 * 
 * @author flavius.filipe
 */
public class SessionFactory {

    private static final org.hibernate.SessionFactory sessionFactory;

    static {
	try {
	    sessionFactory = new Configuration()
		    .configure(SessionFactory.class.getClassLoader().getResource("hibernate.cfg.xml"))
		    .buildSessionFactory();
	} catch (Exception ex) {
	    System.err.println("Initial SessionFactory creation failed." + ex);
	    throw new ExceptionInInitializerError(ex);
	}
    }

    public static org.hibernate.SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public static Session getSession() {
	return getSessionFactory().openSession();
    }
}