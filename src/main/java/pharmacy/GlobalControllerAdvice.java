package pharmacy;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import pharmacy.user.UserManagement;

@ControllerAdvice
public class GlobalControllerAdvice {
    
    private final UserManagement userManagement;

    public GlobalControllerAdvice(UserManagement userManagement) {
        this. userManagement = userManagement;
    }

    @ModelAttribute("picture")
    public String picture() {
        if(userManagement.currentUser().isPresent()) {
            return userManagement.currentUser().get().getPicture();
        }
        
        return null;
    }
} 
