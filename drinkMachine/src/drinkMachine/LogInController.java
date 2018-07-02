package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        String name = request.getParameter("name");
        String pas  = request.getParameter("pas");
        AcountBean acountbean = new AcountBean();

        AcountDao acountdao = new AcountDao();
        String kensaku      = acountdao.check(name, pas);
        String  nextPage    = null;
        if (kensaku.equals("1")) {
            acountbean = acountdao.getAcount(name);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1800);
            session.setAttribute("acount",acountbean);
            nextPage = "/CartController";
        } else {
            request.setAttribute("error", "ユーザ名、又はパスワードが異なります");
            nextPage = "/login.jsp";
        }
        ServletContext application = getServletContext();
        application.getRequestDispatcher(nextPage).forward(request,response); // ä¸è¨ã®çµæã«ãããã¼ã¸ã®ç§»åãè¡ã

    }
}
