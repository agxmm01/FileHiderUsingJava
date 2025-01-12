package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(User user) {
        try {
            if(UserDAO.isExist(user.getEmail())) {
                return 0;
            } else {
                UserDAO.saveUser(user);
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
