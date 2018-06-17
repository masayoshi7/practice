package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import drinkMachine.Dao.AcountDao;

public class NewAcountController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public NewAcountController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String pas  = request.getParameter("pas");

        try {
            AcountDao acountdao = new AcountDao();
            int check           = 0;
            String next         = null;
            String count        = acountdao.checkName(name);

            if (count.equals("0")) {
                check                 = acountdao.addAcount(name, pas);
                AcountBean acountbean = new AcountBean();
                acountbean            = acountdao.getAcount(name);
                request.getSession().setAttribute("acount",acountbean);
                next = "/myPage.jsp";
            } else {
                request.setAttribute("errorm","入力されたアカウント名は既に存在します");
                next = "/NewAcount.jsp";
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher(next).forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
