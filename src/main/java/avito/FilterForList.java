package avito;

import DAO.MechanicDAO;
import cars_annot.Brand;
import org.hibernate.query.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/filterFlist")
public class FilterForList {

    @Autowired
    private MechanicDAO mechanicDAO;
    private final String[] arrayParametrs = new String[3];

    @GetMapping
    @ResponseBody
    protected void getContextMenu(HttpServletResponse resp) throws IOException {
        JSONObject send = new JSONObject();
        JSONArray array = new JSONArray();
        Iterator<Brand> iterator = mechanicDAO.func(session -> {
            Query query = session.createQuery("from Brand ");
            return query.list();
        }).iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObject = new JSONObject();
            Brand brand = iterator.next();
            jsonObject.put("id", brand.getId());
            jsonObject.put("brand", brand.getName());
            array.add(jsonObject);
        }
        send.put("idBrand", arrayParametrs[0]);
        send.put("havePhoto", arrayParametrs[1]);
        send.put("today", arrayParametrs[2]);
        send.put("array", array);
        resp.getWriter().println(send);
    }

    @PostMapping
    protected void goToList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        arrayParametrs[0] = req.getParameter("brands");
        arrayParametrs[1] = req.getParameter("photo");
        arrayParametrs[2] = req.getParameter("today");
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }
}
