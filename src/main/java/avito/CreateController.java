package avito;

import DAO.MechanicDAO;
import cars_annot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller
@RequestMapping("/create")
public class CreateController {

    final Logger logger = Logger.getLogger(CreateController.class.getName());
    @Autowired
    MechanicDAO mechanicDAO;

    @GetMapping
    protected String goToCreate() {
        return "createCar";
    }

    @PostMapping
    protected String saveCar(@SessionAttribute("user") Holder holder, @ModelAttribute("car") CarForm carForm) {
        Car car = carForm.getCar();
        car.setHolder(holder);
        mechanicDAO.func(session -> {
            session.saveOrUpdate(car);
            return car;
        });
        return "createCar";
    }
}
