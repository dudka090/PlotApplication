package pl.myapplication.plot.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggedCheck {
    private  Map<String, Date> logged = new HashMap<>();

    public void insertLoggedPerson(String login, Date expiration){
        logged.put(login, expiration);
    }

    public boolean checkWhoIsLogged(String login){
        if(logged.containsKey(login)){
            return true;
        }else{
            return false;
        }
    }

    @Scheduled(fixedRate = 1000)
    public void schduledExpirationCheck(){
        logged.forEach((k,v) ->{
            if(v.before(new Date(System.currentTimeMillis()))){
                logged.remove(k);
            }
        });
    }
}
