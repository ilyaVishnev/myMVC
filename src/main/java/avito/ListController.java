package avito;

import DAO.MechanicDAO;
import cars_annot.CarA;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    protected String sendToList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "list";
    }

    @PostMapping
    @ResponseBody
    protected void sendList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String ajax = "";
        if (br != null) {
            ajax = br.readLine();
        }
        HashMap<String, String> map = new LinkedHashMap<>();
        map.put("idBrand", "null");
        map.put("havePhoto", "null");
        map.put("today", "null");
        List<String> listFilter = Arrays.asList(ajax.split(","));
        Iterator<String> iterator = listFilter.iterator();
        Iterator EntryIterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = (Entry) EntryIterator.next();
            entry.setValue(iterator.next());
        }
        String firstResult = "from CarA c";
        boolean firstFilter = true;
        if (!map.get("idBrand").equals("null")) {
            firstResult += firstFilter ? " where c.engineA.model.brand.id=" +
                    map.get("idBrand") : " and c.engineA.model.brand.id=" +
                    map.get("idBrand");
            firstFilter = false;
        }
        if (!map.get("havePhoto").equals("null")) {
            firstResult += firstFilter ? " where c.photo!=''" : " and c.photo!=''";
            firstFilter = false;
        }
        if (!map.get("today").equals("null")) {
            firstResult += firstFilter ? " where year(c.date) = year(current_date()) and month(c.date) = month(current_date) and day(c.date)=day(current_date )" :
                    " and year(c.date) = year(current_date()) and month(c.date) = month(current_date) and day(c.date)=day(current_date )";
            firstFilter = false;
        }
        final String result = firstResult;
        JSONArray jsonArray = new JSONArray();
        JSONObject send = new JSONObject();
        List<CarA> myCars = mechanicDAO.func(session -> {
            final Query query = session.createQuery(result);
            List<CarA> cars = query.list();
            return cars;
        });
        Iterator<CarA> iterator1 = myCars.iterator();
        while (iterator1.hasNext()) {
            CarA car = iterator1.next();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", car.getId());
            jsonObject.put("model", car.getGearboxA().getModel().toString());
            jsonObject.put("price", car.getPrice());
            jsonObject.put("photo", car.getPhoto());
            jsonObject.put("status", car.getStatus());
            jsonObject.put("date", 2000 + car.getDate().getYear() - 100 + " " + car.getDate().getMonth() + " " + (car.getDate().getDate() + 1));
            jsonObject.put("brandId", car.getGearboxA().getModel().getBrand().getId());
            jsonArray.add(jsonObject);
        }
        send.put("array", jsonArray);
        resp.getWriter().println(send);
    }
}
