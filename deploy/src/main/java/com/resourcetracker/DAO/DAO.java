package com.resourcetracker.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Static class which contains all types
 * of function-wrapper requests to the dbms
 */
public class DAO {
	private static SessionFactory sessionFactory = null;

	public DAO(){
		if (sessionFactory == null){
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
	}

	public static void AddRecord(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();


		// session.save( person );
		// session.save( person2 );

		session.getTransaction().commit();
		session.close();
	}

	public static void close(){
		sessionFactory.close();
	}
}
