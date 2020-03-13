package cn.myapp.dao.impl;

import cn.myapp.dao.IUserDAO;
import cn.myapp.domain.User;
import cn.myapp.jdbc.JDBCHelper;

import java.sql.ResultSet;

/**
 * Created by ua28 on 3/13/20.
 */
public class UserDAOImpl implements IUserDAO {

    @Override
    public User findById(final long id) {
        final User user = new User();

        String sql = "select * from user where userid = ?";
        Object[] params = new Object[]{id};

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(sql, params, new JDBCHelper.QueryCallBack() {
            @Override
            public void process(ResultSet rs) throws Exception {
                if (rs.next()) {
                    long userId = rs.getLong(1);
                    String username = rs.getString(2);
                    String sextual = rs.getString(3);
                    String avatar = rs.getString(4);
                    String birthday = rs.getString(5);

                    user.setUserId(userId);
                    user.setUserName(username);
                    user.setSexual(sextual);
                    user.setAvatar(avatar);
                    user.setBirthday(birthday);
                }
            }
        });

        return user;
    }

    @Override
    public void insert(User user) {

    }

    @Override
    public void deleteByid(long id) {

    }

    @Override
    public void update(User user) {

    }
}
