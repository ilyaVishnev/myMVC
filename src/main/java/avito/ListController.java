package avito;

import DAO.MechanicDAO;
import cars_annot.Car;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

@Controller
@RequestMapping("/list")
public class ListController {

    @Autowired
    private MechanicDAO mechanicDAO;

    @GetMapping
    protected String sendToList() {
        return "list";
    }

    @PostMapping
    @ResponseBody
    protected String sendList(@RequestBody(required = false) String text) {
        HashMap<String, String> map = new Gson().fromJson(text, new TypeToken<HashMap<String, String>>() {
        }.getType());
        String firstResult = "from Car c";
        boolean firstFilter = true;
        if (!map.get("idBrand").equals("off")) {
            firstResult += firstFilter ? " where c.engine.model.brand.id=" +
                    map.get("idBrand") : " and c.engine.model.brand.id=" +
                    map.get("idBrand");
            firstFilter = false;
        }
        if (!map.get("photo").equals("off")) {
            firstResult += firstFilter ? " where c.photo!=''" : " and c.photo!=''";
            firstFilter = false;
        }
        if (!map.get("today").equals("off")) {
            firstResult += firstFilter ? " where year(c.date) = year(current_date()) and month(c.date) = month(current_date) and day(c.date)=day(current_date )" :
                    " and year(c.date) = year(current_date()) and month(c.date) = month(current_date) and day(c.date)=day(current_date )";
            firstFilter = false;
        }
        final String result = firstResult;
        JSONArray jsonArray = new JSONArray();
        JSONObject send = new JSONObject();
        List<Car> myCars = mechanicDAO.func(session -> {
            final Query query = session.createQuery(result);
            List<Car> cars = query.list();
            return cars;
        });
        Iterator<Car> iterator1 = myCars.iterator();
        while (iterator1.hasNext()) {
            Car car = iterator1.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", car.getId());
            jsonObject.put("model", car.getGearbox().getModel().toString());
            jsonObject.put("price", car.getPrice());
            jsonObject.put("photo", car.getPhoto());
            jsonObject.put("status", car.getStatus());
            jsonObject.put("date", 2000 + car.getDate().getYear() - 100 + " " + car.getDate().getMonth() + " " + (car.getDate().getDate() + 1));
            jsonObject.put("brandId", car.getGearbox().getModel().getBrand().getId());
            jsonArray.add(jsonObject);
        }
        send.put("array", jsonArray);
        return send.toString();
    }
}
