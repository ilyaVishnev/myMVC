package DAO;

import cars_annot.Holder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

@Service
public class MechanicDAO {
    private static final MechanicDAO mechanicDAO = new MechanicDAO();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private final String[] array = new String[5];
    private final Logger logger = Logger.getLogger(MechanicDAO.class.getName());

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static MechanicDAO getMechanicDAO() {
        return mechanicDAO;
    }

    public <T> T func(final Function<Session, T> command) {
        T t = null;
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            t = command.apply(session);
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.info(ex.getMessage());
        } finally {
            transaction.commit();
            session.close();
        }
        return t;
    }

    public Holder isCredential(String login, String password) {
        return this.func(session -> {
            Criteria userCriteria = session.createCriteria(Holder.class);
            userCriteria.add(Restrictions.eq("login", login));
            userCriteria.add(Restrictions.eq("password", password));
            Holder holder = (Holder) userCriteria.uniqueResult();
            return holder;
        });
    }

    public <T> void saveEntyty(T t) {
        this.func(session -> {
            session.save(t);
            return t;
        });
    }

    public <T> void deleteCar(T t) {
        this.func(session -> {
            session.delete(t);
            return null;
        });
    }

    public <T> void updateCar(T t) {
        this.func(session -> {
            session.saveOrUpdate(t);
            return t;
        });
    }
}
