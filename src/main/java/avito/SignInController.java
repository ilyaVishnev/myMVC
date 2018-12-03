package avito;

import DAO.MechanicDAO;
import cars_annot.Holder;
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
@RequestMapping("/sign")
public class SignInController {

    @Autowired
    MechanicDAO mechanicDAO;

    @GetMapping
    protected void goToAccount(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException {
        req.getRequestDispatcher("/WEB-INF/views/signIn.jsp").forward(req, resp);
    }

    @PostMapping
    protected void toList(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException{
        Holder holder = mechanicDAO.isCredential(req.getParameter("login"), req.getParameter("password"));
        if (holder == null) {
            req.setAttribute("error", "there isnt such user");
            req.getRequestDispatcher("/WEB-INF/views/signIn.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("user", holder);
            req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
        }
    }
}
