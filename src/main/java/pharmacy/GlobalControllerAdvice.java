package pharmacy;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import pharmacy.order.OrderController;
import pharmacy.user.UserManagement;

@ControllerAdvice
public class GlobalControllerAdvice {
    
    private final UserManagement userManagement;
    private final OrderController orderController;

    public GlobalControllerAdvice(UserManagement userManagement, OrderController orderController) {
        this.userManagement = userManagement;
        this.orderController = orderController;
    }

    @ModelAttribute("picture")
    public String picture() {
        if(userManagement.currentUser().isPresent()) {
            return userManagement.currentUser().get().getPicture();
        }
        
        return null;
    }

    @ModelAttribute("counter")
    public Long counter() {
        return orderController.getCart().get().count();
    }
} 
