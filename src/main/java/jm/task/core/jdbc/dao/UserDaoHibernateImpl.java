package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory = getSessionFactory();
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(Id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "Name VARCHAR(80), LastName VARCHAR(80), Age TINYINT);").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DROP TABLE if exists User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM User WHERE id = :id";
            session.createQuery(hql).setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                transaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        CriteriaBuilder criteriaBuilder = sessionFactory.openSession().getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        userCriteriaQuery.from(User.class);
        return sessionFactory.openSession().createQuery(userCriteriaQuery)
                .getResultList();
    }

     @Override
     public void cleanUsersTable() {
         try (Session session = sessionFactory.openSession()) {
             transaction = session.beginTransaction();
             session.createQuery("DELETE FROM User").executeUpdate();
             transaction.commit();
         } catch (Exception e) {
             e.printStackTrace();
             try {
                 transaction.rollback();
             } catch (Exception ex) {
                 ex.printStackTrace();
             }
         }
     }
}
