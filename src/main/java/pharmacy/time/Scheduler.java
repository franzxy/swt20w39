package pharmacy.time;

import java.time.Duration;
import java.time.LocalDateTime;

import org.salespointframework.time.BusinessTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@EnableScheduling
@Component
public class Scheduler {
    @Autowired
    private BusinessTime time;
    private LocalDateTime now;
    public Scheduler(BusinessTime time){
        this.time=time;
        this.now=LocalDateTime.now();
    }

    @Scheduled(fixedRate = 500)
    public void update(){
        if(time.getTime().isAfter(now)){
            //Days that arent computed
            long days = time.getTime().getDayOfYear() - now.getDayOfYear();

            //Months that arent computed
            long months = time.getTime().getMonthValue() - now.getMonthValue();
            if(time.getTime().getYear()!=now.getYear()){
                months = (12 - now.getMonthValue()) + time.getTime().getMonthValue();
                months += 12 * (( time.getTime().getYear() - now.getYear()) - 1);

                days = (365 - now.getDayOfYear()) + time.getTime().getDayOfYear();
                days += 365 * (( time.getTime().getYear() - now.getYear()) - 1);
            }
            while(months>0){
                monthly();
                months--;
            }
            while(days>0){
                daily();
                days--;
            }
            
            this.now=time.getTime();
        }
    }
    public void monthly(){
        System.out.println("Monat vorbei");
    }
    public void daily(){
        System.out.println("Tag vorbei");
    }
}
