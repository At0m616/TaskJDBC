package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserDao dao = new UserDaoJDBCImpl();

        dao.createUsersTable();

        dao.saveUser("Petr", "Petrov", (byte) 54);
        dao.saveUser("Ivan", "Ivanov", (byte) 34);
        dao.saveUser("Vasiliy", "Vasilev", (byte) 39);
        dao.saveUser("Alex", "P", (byte) 41);

        List<User> userList = dao.getAllUsers();
        System.out.println(userList);

        dao.cleanUsersTable();
        dao.dropUsersTable();


    }
}
