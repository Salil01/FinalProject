package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.RoleDao;
import com.agungsetiawan.finalproject.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
    
    @Override
    public Role findOne(Long id) {
        return roleDao.findOne(id);
    }
    
}
