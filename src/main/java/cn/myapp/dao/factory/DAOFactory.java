package cn.myapp.dao.factory;

import cn.myapp.dao.IUserDAO;
import cn.myapp.dao.impl.UserDAOImpl;

/**
 * Created by ua28 on 3/13/20.
 */
public class DAOFactory {

    public static IUserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
