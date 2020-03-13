import cn.myapp.dao.IUserDAO;
import cn.myapp.dao.factory.DAOFactory;
import cn.myapp.domain.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        IUserDAO userDAO = DAOFactory.getUserDAO();
        User user = userDAO.findById(100);
        System.out.println(user.toString());
    }
}
