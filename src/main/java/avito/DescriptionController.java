package avito;

import DAO.MechanicDAO;
import cars_annot.Car;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    protected JSONObject getDesc() {
        Car car = mechanicDAO.func(session -> {
            return session.get(Car.class, Integer.parseInt(parametrs[0]));
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("desc", car.getDescription());
        jsonObject.put("carbody", car.getCarBody().getDescription());
        jsonObject.put("engine", car.getEngine().getDescription());
        jsonObject.put("gearbox", car.getGearbox().getDescription());
        jsonObject.put("photo", car.getPhoto());
        jsonObject.put("price", car.getPrice());
        jsonObject.put("status", car.getStatus());
        jsonObject.put("idHolder", car.getHolder().getId());
        jsonObject.put("id", car.getId());
        return jsonObject;
    }

    @PostMapping
    protected String postDesc(@RequestParam("carId") String carId) throws IOException, ServletException {
        parametrs[0] = carId;
        return "description";
    }
}
