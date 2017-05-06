package com.daos;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.models.entities.User;

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
	public boolean saveOrUpdate(User user) {
		sessionFactory.getCurrentSession().persist(user);
		return true;
	}

	@Override
	public User getUser(int id) {		
		return  sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public void deleteUser(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		User user =  session.get(User.class, id);
		if(user != null)
			session.delete(user);	
	}

	@Override
	public List<User> getUserList() {
		@SuppressWarnings("unchecked")
		List<User> users = sessionFactory.getCurrentSession().createQuery("from User").list();
		return users;
	}

}
