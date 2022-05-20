package com.resourcetracker.dao;

import org.hibernate.Criteria;
import org.hibernate.LazyInitializationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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