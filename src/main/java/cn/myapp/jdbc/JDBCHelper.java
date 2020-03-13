package cn.myapp.jdbc;

import cn.myapp.conf.ConfigurationManager;
import cn.myapp.constant.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ua28 on 3/13/20.
 */
public class JDBCHelper {

    static {
        try {
            String driver = ConfigurationManager.getProperty(Constants.JDBC_DRIVER);
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JDBCHelper instance = null;

    public static JDBCHelper getInstance() {
        if (instance == null) {
            synchronized (JDBCHelper.class) {
                if (instance == null) {
                    instance = new JDBCHelper();
                }
            }
        }
        return instance;
    }

    private LinkedList<Connection> datasource = new LinkedList<>();

    private JDBCHelper() {
        int datasourceSize = ConfigurationManager.getInterger(Constants.JDBC_DATASOURCE_SIZE);

        for (int i = 0; i < datasourceSize; i++) {
            String url = ConfigurationManager.getProperty(Constants.JDBC_URL);
            String user = ConfigurationManager.getProperty(Constants.JDBC_USER);
            String password = ConfigurationManager.getProperty(Constants.JDBC_PASSWORD);

            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                datasource.push(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() {
        while (datasource.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return datasource.poll();
    }

    public int executeUpdate(String sql, Object[] params) {
        int rtn = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i+1, params[i]);
                }
            }

            rtn = pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                datasource.push(conn);
            }
        }
        return rtn;
    }

    public void executeQuery(String sql, Object[] params, QueryCallBack queryCallBack) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i+1, params[i]);
                }
            }

            rs = pstmt.executeQuery();

            // call-back function
            queryCallBack.process(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                datasource.push(conn);
            }
        }
    }

    public int[] executeBatch(String sql, List<Object[]> paramlist) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int[] rtn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement(sql);

            if (paramlist != null && paramlist.size()>0) {
                for (Object[] object : paramlist) {
                    for (int i = 0; i < object.length; i++) {
                        pstmt.setObject(i+1, object[i]);
                    }
                    pstmt.addBatch();
                }
            }

            rtn = pstmt.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                datasource.push(conn);
            }
        }

        return rtn;
    }

    public interface QueryCallBack {

        /**
         * process query results
         * @param rs
         * @throws Exception
         */
        void process(ResultSet rs) throws Exception;
    }
}
