package com.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean saveOrUpdate(User user) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		session.close();
		return true;
	}

	@Override
	public User getUser(int id) {		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
        User user = session.get(User.class, id);
        tx.commit();
		return user;
	}

	@Override
	public void deleteUser(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		User user =  session.load(User.class, id);
		if(user != null)
			session.delete(user);	
		session.getTransaction().commit();
	}

	@Override
	public List<User> getUserList() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<User> users = sessionFactory.getCurrentSession().createQuery("from User").list();
		tx.commit();
		return users;
	}

}
