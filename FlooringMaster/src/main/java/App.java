
import com.jobep.flooringmaster.controler.FMController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author powel
 */
public class App {
    public static void main(String[] args){ 
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.jobep.flooringmaster");
        appContext.refresh();
        FMController controller = appContext.getBean("FMController",FMController.class);
        controller.run();
    }
}
