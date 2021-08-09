/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jobep.flooringmaster.controler;

import com.jobep.flooringmaster.dao.FMNoOrderForDateException;
import com.jobep.flooringmaster.dao.FMPersistenceException;
import com.jobep.flooringmaster.model.Order;
import com.jobep.flooringmaster.service.FMService;
import com.jobep.flooringmaster.view.FMView;
import java.time.LocalDate;
import java.util.List;
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
    
    public void run() throws FMPersistenceException{
            boolean keepGoing = true;
            service.readFiles();
            while(keepGoing){
                int answer = view.displayMainMenu();
                try{
                    switch(answer){
                        case 1:
                            displayOrders();
                            break;
                        case 2:
                            addOrder();
                            break;
                        case 3:
                            editOrder();
                            break;
                        case 4:
                            removeOrder();
                            break;
                        case 5:
                            keepGoing = false;
                            break;
                        default:
                    }
                }catch(Exception e){
                    view.displayException(e);
                }
            }
            service.writeFile();
    }
    
    public void displayOrders() throws FMNoOrderForDateException{
        LocalDate orderDate = view.displayGetDate();
        List<Order> orders;
        try{
            orders = service.getOrdersByDate(orderDate);
        }catch(FMNoOrderForDateException e){
            throw e;
        }
        view.displayOrdersList(orders);
    }
    
    public void addOrder() throws FMPersistenceException{
        Order toAdd = view.displayAddOrder(service.getAllOrders(),service.getAllTaxes(),service.getAllProducts());
        boolean toContinue = view.displayConfirmOrder(toAdd);
        if(toContinue){
            service.insertOrder(toAdd);
            view.displayAddedSuccessfully();
        }
    }
    
    public void editOrder() throws FMNoOrderForDateException,FMPersistenceException{
        Order toEdit;
        LocalDate orderDate = view.displayGetDate();
        int orderNumber = view.displayGetOrderNumber();
        try{
            toEdit = service.getOrder(orderNumber,orderDate);
        }catch(FMNoOrderForDateException e){
            throw e;
        }
        Order editedOrder = view.displayEditMenu(toEdit,service.getAllProducts(),service.getAllTaxes());
        if(view.displayConfirmOrder(editedOrder)){
            service.editOrder(editedOrder);
            view.displayEditedSuccesfully();
        }
    }
    
    public void removeOrder() throws FMNoOrderForDateException,FMPersistenceException{
        int answer;
        Order toRemove;
        LocalDate orderDate = view.displayGetDate();
        int orderNumber = view.displayGetOrderNumber();
        try{
            toRemove = service.getOrder(orderNumber,orderDate);
        }catch(FMNoOrderForDateException e){
            throw e;
        }
        answer = view.displayRemoveOrder(toRemove);
        if(answer == 1){
            service.removeOrder(toRemove);
            view.displayRemovedSuccessfully();
        }
    } 
}
