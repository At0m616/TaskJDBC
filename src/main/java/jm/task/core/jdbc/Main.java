package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;


public class Main {

    public static void main(String[] args) {

        UserDao dao = new UserDaoHibernateImpl();

        dao.createUsersTable();

        dao.saveUser("Petr", "Petrov", (byte) 54);
        dao.saveUser("Ivan", "Ivanov", (byte) 34);
        dao.saveUser("Vasiliy", "Vasilev", (byte) 39);
        dao.saveUser("Alex", "Kozlov", (byte) 41);
        dao.removeUserById(1);

        dao.getAllUsers().forEach(System.out::println);

        dao.cleanUsersTable();
        dao.dropUsersTable();


    }
}
