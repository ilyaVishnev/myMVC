package avito;

import DAO.MechanicDAO;
import cars_annot.CarA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    protected void setStatus(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final CarA car = mechanicDAO.func(session -> {
            return session.get(CarA.class, Integer.parseInt(req.getParameter("id")));
        });
        car.setStatus(Boolean.parseBoolean(req.getParameter("status")));
        mechanicDAO.func(session -> {
            session.saveOrUpdate(car);
            return car;
        });
        req.getRequestDispatcher("/WEB-INF/views/description.jsp").forward(req, resp);
    }
}
