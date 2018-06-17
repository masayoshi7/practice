package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import drinkMachine.Dao.AcountDao;

public class LogInController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public LogInController()
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
        response.getContentType();
        AcountBean acountbean = new AcountBean();
        try {
            AcountDao acountdao = new AcountDao();
            String kensaku      = acountdao.check(name, pas);
            String  nextPage    = null;
            if (kensaku.equals("1")) {
                acountbean = acountdao.getAcount(name);
                request.getSession().setAttribute("acount",acountbean);
                nextPage = "/myPage.jsp";
            } else {
                request.setAttribute("error", "入力された情報は正しくありません");
                nextPage = "/login.jsp";
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher(nextPage).forward(request,response); // 上記の結果によりページの移動を行う
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
