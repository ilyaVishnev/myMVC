package avito;

import DAO.MechanicDAO;
import cars_annot.*;
import org.hibernate.query.Query;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    MechanicDAO mechanicDAO;

    @GetMapping
    @ResponseBody
    protected JSONObject doGet() {
        final JSONArray brandArray = new JSONArray();
        final JSONArray modelArray = new JSONArray();
        final JSONArray gearboxArray = new JSONArray();
        final JSONArray engineArray = new JSONArray();
        final JSONArray carbodyArray = new JSONArray();
        final JSONArray yearsArray = new JSONArray();
        JSONObject jsonSend = new JSONObject();
        Iterator<Brand> iterator = mechanicDAO.func(session -> {
            final Query query = session.createQuery("from Brand ");
            List<Brand> brands = query.list();
            return brands;
        }).iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            Brand brand = iterator.next();
            jsonObj.put("id", brand.getId());
            jsonObj.put("name", brand.getName());
            brandArray.add(jsonObj);
        }
        jsonSend.put("brandArray", brandArray);
        Iterator<Model> iterator1 = mechanicDAO.func(session -> {
            final Query query = session.createQuery("from Model ");
            List<Model> models = query.list();
            return models;
        }).iterator();
        while (iterator1.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            Model model = iterator1.next();
            jsonObj.put("id", model.getId());
            jsonObj.put("name", model.getName());
            jsonObj.put("IdBrand", model.getBrand().getId());
            modelArray.add(jsonObj);
        }
        jsonSend.put("modelArray", modelArray);
        Iterator<GearboxA> iterator2 = mechanicDAO.func(session -> {
            final Query query = session.createQuery("from GearboxA ");
            List<GearboxA> boxes = query.list();
            return boxes;
        }).iterator();
        while (iterator2.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            GearboxA gearboxA = iterator2.next();
            jsonObj.put("id", gearboxA.getId());
            jsonObj.put("desc", gearboxA.getDescription());
            jsonObj.put("IdM", gearboxA.getModel().getId());
            jsonObj.put("year", gearboxA.getYear());
            gearboxArray.add(jsonObj);
        }
        jsonSend.put("gearboxArray", gearboxArray);
        Iterator<EngineA> iterator3 = mechanicDAO.func(session -> {
            final Query query = session.createQuery("from EngineA ");
            List<EngineA> engineAS = query.list();
            return engineAS;
        }).iterator();
        while (iterator3.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            EngineA engineA = iterator3.next();
            jsonObj.put("id", engineA.getId());
            jsonObj.put("desc", engineA.getDescription());
            jsonObj.put("IdM", engineA.getModel().getId());
            jsonObj.put("year", engineA.getYear());
            engineArray.add(jsonObj);
        }
        jsonSend.put("engineArray", engineArray);
        Iterator<CarBodyA> iterator4 = mechanicDAO.func(session -> {
            final Query query = session.createQuery("from CarBodyA ");
            List<CarBodyA> carBodyAS = query.list();
            return carBodyAS;
        }).iterator();
        while (iterator4.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            CarBodyA carBodyA = iterator4.next();
            jsonObj.put("id", carBodyA.getId());
            jsonObj.put("desc", carBodyA.getDescription());
            jsonObj.put("IdM", carBodyA.getModel().getId());
            jsonObj.put("year", carBodyA.getYear());
            carbodyArray.add(jsonObj);
        }
        jsonSend.put("carbodyArray", carbodyArray);
        Iterator<Year> iterator5 = mechanicDAO.func(session -> {
            final Query query = session.createQuery("from Year ");
            List<Year> years = query.list();
            return years;
        }).iterator();
        while (iterator5.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            Year year = iterator5.next();
            jsonObj.put("year", year.getYear());
            yearsArray.add(jsonObj);
        }
        jsonSend.put("yearsArray", yearsArray);
        return jsonSend;
    }
}
