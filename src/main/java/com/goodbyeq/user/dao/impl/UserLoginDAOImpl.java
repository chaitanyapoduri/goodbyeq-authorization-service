package com.goodbyeq.user.dao.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.goodbyeq.exception.GBQUserException;
import com.goodbyeq.user.dao.api.UserLoginDAO;
import com.goodbyeq.user.db.bo.UserLoginVO;

@Repository("userLoginDAO")
public class UserLoginDAOImpl extends HibernateDaoSupport implements UserLoginDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserLoginDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	public void anyMethodName(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	@Override
	public void addUserLoginInfo(final UserLoginVO userLoginVO) throws GBQUserException {
		//userLoginVO.setUserLoginSysGUID(UUID.randomUUID().toString());
		getEntityManager().persist(userLoginVO);
	}

}
