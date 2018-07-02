package drinkMachine;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOutController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public LogOutController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getSession().setAttribute("acount",null);
        String nextPage            = "/login.jsp";
        ServletContext application = getServletContext();
        application.getRequestDispatcher(nextPage).forward(request,response);
    }
}
