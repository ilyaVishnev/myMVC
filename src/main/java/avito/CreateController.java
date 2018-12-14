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
    protected String saveCar(@SessionAttribute("user") Holder holder, @RequestParam MultiValueMap parameters) {
        Car car = new Car();
        car.setPrice(Integer.parseInt((String) parameters.getFirst("price")));
        car.setEngine(new Engine(Integer.parseInt((String) parameters.getFirst("engine"))));
        car.setHolder(holder);
        car.setCarBody(new CarBody(Integer.parseInt((String) parameters.getFirst("carbody"))));
        car.setGearbox(new Gearbox(Integer.parseInt((String) parameters.getFirst("gearbox"))));
        car.setDescription((String) parameters.getFirst("desc"));
        car.setStatus(Boolean.parseBoolean((String) parameters.getFirst("status")));
        car.setYear(Integer.parseInt((String) parameters.getFirst("year")));
        car.setPhoto((String) parameters.getFirst("myimage"));
        mechanicDAO.func(session -> {
            session.saveOrUpdate(car);
            return car;
        });
        return "createCar";
    }
}
