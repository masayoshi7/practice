package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import drinkMachine.Dao.T001_ITEMDao;

public class AllController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public AllController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ItemBean itembean = new ItemBean();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");
        response.setCharacterEncoding("UTF-8"); // 文字コードの指定
        String nextPage =null;

        try {
            T001_ITEMDao itemdao = new T001_ITEMDao();
            List list            = new ArrayList();
            list                 = itemdao.allSerch();

            request.setAttribute("csv",list); // リクエストスコープに保存
            request.getSession().setAttribute("sltm",list);
            request.getSession().setAttribute("cccc","1"); // リクエストスコープに保存
            int listsize = list.size();

            if (listsize == 0) {
                request.setAttribute("kekka","条件に合う商品がありませんでした");//リクエストスコープに保存
            }

            ServletContext application = getServletContext();
            application.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
