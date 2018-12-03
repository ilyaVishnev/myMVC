package avito;

import DAO.MechanicDAO;
import cars_annot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    protected String saveCar(HttpSession mySession, HttpServletRequest req) {

        EngineA engineA = mechanicDAO.func(session -> {
            return session.get(EngineA.class, Integer.parseInt(req.getParameter("engine")));
        });
        GearboxA gearboxA = mechanicDAO.func(session -> {
            return session.get(GearboxA.class, Integer.parseInt(req.getParameter("gearbox")));
        });
        CarBodyA carBodyA = mechanicDAO.func(session -> {
            return session.get(CarBodyA.class, Integer.parseInt(req.getParameter("carbody")));
        });
        Holder holder = (Holder) mySession.getAttribute("user");
        CarA car = new CarA();
        car.setPrice(Integer.parseInt(req.getParameter("price")));
        car.setEngineA(engineA);
        car.setHolder(holder);
        car.setCarBodyA(carBodyA);
        car.setGearboxA(gearboxA);
        car.setDescription(req.getParameter("desc"));
        car.setStatus(Boolean.parseBoolean(req.getParameter("status")));
        car.setYear(Integer.parseInt(req.getParameter("year")));
        car.setPhoto(req.getParameter("myimage"));
        mechanicDAO.func(session -> {
            session.saveOrUpdate(car);
            return car;
        });
        return "createCar";
    }
}
