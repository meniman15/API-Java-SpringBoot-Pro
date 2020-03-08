package poalim.project.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import poalim.project.model.Employee;

@Component
@Aspect
@EnableAspectJAutoProxy
public class SavePerformanceNotifier {

    long timeInMillis;

    @Before("execution(public * addEmployee(*))")
    public void notifyOnSaveStart(){
        timeInMillis = System.currentTimeMillis();
        System.out.println("New employee save operation started at: "+ timeInMillis + " milliseconds");

    }

    @After("execution(public * addEmployee(*))")
    public void notifyOnSaveEnd(){
        timeInMillis = System.currentTimeMillis() - timeInMillis;
        System.out.println("New employee save operation took total of: "+ timeInMillis +" milliseconds");
    }
}
