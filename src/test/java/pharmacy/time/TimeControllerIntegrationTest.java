package pharmacy.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

import org.springframework.validation.Errors;
import org.junit.jupiter.api.Test;
import org.salespointframework.time.BusinessTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import pharmacy.AbstractIntegrationTests;
import pharmacy.inventory.ErrorTest;

public class TimeControllerIntegrationTest extends AbstractIntegrationTests {
    
    @Autowired
    private TimeController timeController;

    @Autowired
    private BusinessTime businessTime;

    @Test
    @WithMockUser(roles = "BOSS")
    void timeTest(){
        Model model = new ExtendedModelMap();
        DurationForm f = new DurationForm((long)0);
        String res = this.timeController.time(model, f);
        assertEquals(res, "time");
        LocalDateTime t = (LocalDateTime) model.asMap().get("time");
        assertTrue(t.toLocalDate().isEqual(businessTime.getTime().toLocalDate()));
        assertEquals(f, model.asMap().get("durationForm"));

    }
    @Test
    @WithMockUser(roles = "BOSS")
    void changeTimeTest(){
        businessTime.reset();
        Model model = new ExtendedModelMap();
        DurationForm f = new DurationForm((long)3);
        Errors e = new ErrorTest();
        
        String res = this.timeController.changeTime(model, f, e);
        assertEquals(res, "redirect:/time");
        Duration d = Duration.ofHours(3);
        int zero = businessTime.getOffset().compareTo(d);
        assertTrue(zero==0);
        e.addAllErrors(null);
        res = this.timeController.changeTime(model, f, e);
        assertEquals(res, "time");
        LocalDateTime t = (LocalDateTime) model.asMap().get("time");
        assertTrue(t.toLocalDate().isEqual(businessTime.getTime().toLocalDate()));

    }
    
}
