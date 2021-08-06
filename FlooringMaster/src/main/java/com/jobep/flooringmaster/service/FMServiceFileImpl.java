/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.service;

import com.jobep.flooringmaster.dao.FMAuditDao;
import com.jobep.flooringmaster.dao.FMDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author powel
 */
@Component
public class FMServiceFileImpl implements FMService{
    
    private FMDao dao;
    private FMAuditDao audit;
    
    @Autowired
    public FMServiceFileImpl(FMDao dao,FMAuditDao audit){
        this.dao = dao;
        this.audit = audit;
    }
}
