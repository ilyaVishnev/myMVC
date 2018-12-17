package cars_annot;

import avito.Views;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CarForm {


    private int price;
    private Integer carBody;
    private Integer engine;
    private Integer gearbox;
    private String description;
    private Boolean status;
    private String photo;
    private int year;
    private Car car = new Car();

    public CarForm() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
        this.car.setPrice(price);
    }

    public Integer getCarBody() {
        return carBody;
    }

    public void setCarBody(Integer carBody) {
        this.carBody = carBody;
        this.car.setCarBody(new CarBody(carBody));
    }

    public Integer getEngine() {
        return engine;
    }

    public void setEngine(Integer engine) {
        this.engine = engine;
        this.car.setEngine(new Engine(engine));
    }

    public Integer getGearbox() {
        return gearbox;
    }

    public void setGearbox(Integer gearbox) {
        this.gearbox = gearbox;
        this.car.setGearbox(new Gearbox(gearbox));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.car.setDescription(description);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
        this.car.setStatus(status);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        this.car.setPhoto(photo);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        this.car.setYear(year);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
