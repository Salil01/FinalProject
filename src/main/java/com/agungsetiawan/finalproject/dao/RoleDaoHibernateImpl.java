package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class RoleDaoHibernateImpl implements RoleDao{

    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public Role findOne(Long id) {
        return (Role) sessionFactory.getCurrentSession().get(Role.class, id);
    }
    
}
