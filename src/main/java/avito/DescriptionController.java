package avito;

import DAO.MechanicDAO;
import cars_annot.CarA;
import org.json.simple.JSONObject;
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
@RequestMapping("/desc")
public class DescriptionController {

    @Autowired
    MechanicDAO mechanicDAO;
    final String[] parametrs = new String[1];

    @GetMapping
    protected void getDesc(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException {
        CarA car = mechanicDAO.func(session -> {
            return session.get(CarA.class, Integer.parseInt(parametrs[0]));
        });
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("desc", car.getDescription());
        jsonObject.put("carbody", car.getCarBodyA().getDescription());
        jsonObject.put("engine", car.getEngineA().getDescription());
        jsonObject.put("gearbox", car.getGearboxA().getDescription());
        jsonObject.put("photo", car.getPhoto());
        jsonObject.put("price", car.getPrice());
        jsonObject.put("status", car.getStatus());
        jsonObject.put("idHolder", car.getHolder().getId());
        jsonObject.put("id", car.getId());
        resp.getWriter().println(jsonObject);
    }

    @PostMapping
    protected void postDesc(HttpServletRequest req,HttpServletResponse resp) throws IOException,ServletException {
        parametrs[0] = req.getParameter("carId");
        req.getRequestDispatcher("/WEB-INF/views/description.jsp").forward(req, resp);
    }
}
