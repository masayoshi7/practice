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
import javax.servlet.http.HttpSession;
import drinkMachine.Dao.T001_ITEMDao;

public class DelController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public DelController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id");
        T001_ITEMDao itemDao = new T001_ITEMDao();
        int count           = itemDao.Delete(id); // 商品の削除を実行

        if(count != 1) {
            request.setAttribute("kekka", "選択した商品は既に削除されている、又は削除されています");
        } else {
            request.setAttribute("kekka", "商品を削除しました");
        }

        ServletContext application = getServletContext();
        application.getRequestDispatcher("/list.jsp").forward(request,response); // ページの移動を行う
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }
}
