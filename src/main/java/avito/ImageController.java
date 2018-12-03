package avito;

import DAO.MechanicDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/image")
public class ImageController {

    final Logger logger = Logger.getLogger(ImageController.class.getName());
    @Autowired
    MechanicDAO mechanicDAO;
    private final String[] arrayParametrs = new String[1];

    @GetMapping
    protected void getImage(HttpServletResponse resp) throws IOException {
        JSONObject object = new JSONObject();
        String fileway = arrayParametrs[0];
        if (fileway.equals("")) {
            fileway = "/pictures/emptyPhoto.JPG";
        } else {
            fileway = "/pictures/" + fileway.substring(fileway.lastIndexOf("\\") + 1);
        }
        object.put("image", fileway);
        resp.getWriter().println(object);

    }

    @PostMapping
    protected void writeImage(HttpServletRequest req, HttpServletResponse resp, @RequestParam("file") MultipartFile mFile) throws IOException, ServletException {
        if (!mFile.isEmpty()) {
            File file = new File(arrayParametrs[0] = "C:\\projects\\myMVC\\src\\main\\web\\pictures\\" + mFile.getOriginalFilename());
            mFile.transferTo(file);
        }
        req.getRequestDispatcher("/WEB-INF/views/createCar.jsp").forward(req, resp);
    }
}
