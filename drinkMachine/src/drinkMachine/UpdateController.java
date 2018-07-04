package drinkMachine;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drinkMachine.Dao.T001_ITEMDao;

public class UpdateController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public UpdateController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       request.setCharacterEncoding("utf-8");

       ItemBean itemBean = new ItemBean();
       itemBean.setCode(request.getParameter("code"));
       itemBean.setName(request.getParameter("name"));
       itemBean.setPrice(request.getParameter("unitPrice"));
       itemBean.setCount(request.getParameter("count"));
       itemBean.setIsPR(request.getParameter("isPR"));
       T001_ITEMDao dao  = new T001_ITEMDao();
       int updateResult = dao.UpDate(itemBean);

       if (updateResult == 1) {
    	   request.setAttribute("kekka", "更新に成功しました");
    	   ServletContext application = getServletContext();
           application.getRequestDispatcher("/list.jsp").forward(request,response);
       } else {
    	   request.setAttribute("kekka", "更新に失敗しました");
    	   ServletContext application = getServletContext();
           application.getRequestDispatcher("/list.jsp").forward(request,response);
       }
    }
}
