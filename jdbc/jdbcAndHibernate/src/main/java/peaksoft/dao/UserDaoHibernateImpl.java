package peaksoft.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import peaksoft.model.User;
import peaksoft.service.UserService;
import peaksoft.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                "id serial primary key," +
                "name varchar(100)," +
                "lastName varchar(100)," +
                "age integer)").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery("drop table if exists users");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Успешное удаление всех пользователей");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
            System.out.println("Успешное сохранение пользователей ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Пользователь с id " + id + " удалён");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {

       try {
           List<User> userList;
           Session session = Util.getSessionFactory().openSession();
           session.beginTransaction();
           userList = session.createQuery("from User").list();
           session.getTransaction().commit();
           session.close();
           return userList;
       }catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }


    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("delete FROM User");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Successfully delete all Users");
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
