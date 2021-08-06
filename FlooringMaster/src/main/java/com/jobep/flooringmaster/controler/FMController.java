/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.controler;

import com.jobep.flooringmaster.service.FMService;
import com.jobep.flooringmaster.view.FMView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author powel
 */
@Component
public class FMController {
    
    private FMService service;
    private FMView view;
    
    @Autowired
    public FMController(FMService service, FMView view){
        this.service=service;
        this.view=view;
        
    }
    
    public void run(){
        
    }
}
