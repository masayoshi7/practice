package drinkMachine;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drinkMachine.Dao.T001_ITEMDao;

public class ViewController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public ViewController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       String code          = request.getParameter("id");
       ItemBean itembean    = new ItemBean();
       T001_ITEMDao itemDao = new T001_ITEMDao();
       itembean             = itemDao.edit(code);
       request.setAttribute("viewDate", itembean); // リクエストスコープでビューに送る

       if (code == null) {

           // TODO エラーメッセージを作成する
           ServletContext application = getServletContext();
           application.getRequestDispatcher("/list.jsp").forward(request,response);
       } else {
           ServletContext application = getServletContext();
           application.getRequestDispatcher("/view.jsp").forward(request,response);
       }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}
