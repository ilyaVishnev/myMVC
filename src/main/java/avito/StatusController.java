package avito;

import DAO.MechanicDAO;
import cars_annot.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/status")
public class StatusController {

    @Autowired
    MechanicDAO mechanicDAO;

    @PostMapping
    protected String setStatus(@RequestParam(required = false, name = "id") String id, @RequestParam(required = false, name = "status") String status) {
        final Car car = mechanicDAO.func(session -> {
            return session.get(Car.class, Integer.parseInt(id));
        });
        car.setStatus(Boolean.parseBoolean(status));
        mechanicDAO.func(session -> {
            session.saveOrUpdate(car);
            return car;
        });
        return "description";
    }
}
