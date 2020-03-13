package cn.myapp.dao;

import cn.myapp.domain.User;

/**
 * Created by ua28 on 3/13/20.
 */
public interface IUserDAO {

    /**
     * use id to find user
     * @param id target user's id
     * @return user with given id
     */
    User findById(long id);

    /**
     * insert new user
     * @param user
     */
    void insert(User user);

    /**
     * delete indicated user
     * @param id
     */
    void deleteByid(long id);

    void update(User user);
}
