package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import drinkMachine.Dao.AcountDao;

public class AcountDelContoroller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AcountDelContoroller()
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

        String no = request.getParameter("Nom");

        
        AcountDao dao = new AcountDao();
        dao.AcountDel(no);
        String nextPage            = "/login.jsp";
        ServletContext application = getServletContext();

        application.getRequestDispatcher(nextPage).forward(request,response);
    }
}
