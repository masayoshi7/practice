package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import drinkMachine.Dao.T001_ITEMDao;

public class CartController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public CartController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        T001_ITEMDao itemDao;
        try {
            itemDao        = new T001_ITEMDao();
            List PRList    = itemDao.isPRItem();
            List NomalList = itemDao.NomalItem();

            request.getSession().setAttribute("PRItem",PRList); // セッションスコープにPRアイテムを保存
            request.getSession().setAttribute("NormalItem",NomalList); // セッションスコープにノーマルアイテムを保存
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/cart.jsp").forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
