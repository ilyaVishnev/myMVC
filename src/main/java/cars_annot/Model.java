package cars_annot;

import avito.Views;
import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Entity
@Table(name = "model")
public class Model {

    @JsonView(Views.Public.class)
    private int id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Public.class)
    private Brand brand;
    private List<CarBodyA> bodies = new ArrayList<>();
    private List<EngineA> engines = new ArrayList<>();
    private List<GearboxA> gerboxes = new ArrayList<>();

    public Model() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "model")
    public List<CarBodyA> getBodies() {
        return bodies;
    }

    public void setBodies(List<CarBodyA> bodies) {
        this.bodies = bodies;
    }

    @OneToMany( cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "model")
    public List<EngineA> getEngines() {
        return engines;
    }

    public void setEngines(List<EngineA> engines) {
        this.engines = engines;
    }

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "model")
    public List<GearboxA> getGerboxes() {
        return gerboxes;
    }

    public void setGerboxes(List<GearboxA> gerboxes) {
        this.gerboxes = gerboxes;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_br", referencedColumnName = "id")

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Model model = (Model) o;
        if (this.getId() == model.getId()) {
            return true;
        }
        return false;
    }
}
