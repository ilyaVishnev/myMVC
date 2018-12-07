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
        EngineA engineA = mechanicDAO.func(session -> {
            return session.get(EngineA.class, Integer.parseInt((String) parameters.getFirst("engine")));
        });
        GearboxA gearboxA = mechanicDAO.func(session -> {
            return session.get(GearboxA.class, Integer.parseInt((String) parameters.getFirst("gearbox")));
        });
        CarBodyA carBodyA = mechanicDAO.func(session -> {
            return session.get(CarBodyA.class, Integer.parseInt((String) parameters.getFirst("carbody")));
        });
        CarA car = new CarA();
        car.setPrice(Integer.parseInt((String) parameters.getFirst("price")));
        car.setEngineA(engineA);
        car.setHolder(holder);
        car.setCarBodyA(carBodyA);
        car.setGearboxA(gearboxA);
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
