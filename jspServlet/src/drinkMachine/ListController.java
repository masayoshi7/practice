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

public class ListController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public ListController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            ItemBean itembean = new ItemBean();
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset = UTF-8");
            response.setCharacterEncoding("UTF-8"); // 文字コードの指定

            String code      = request.getParameter("code");
            String name      = request.getParameter("name");
            String hot       = request.getParameter("hot");
            String cool      = request.getParameter("cool");
            String isSoldout = request.getParameter("isSoldout");
            String isPR      = null;

            if (hot.equals("1")) {
                isPR = "1";
            }
            if (cool.equals("1")) {
                isPR = "0";
            }

            ItemBean item = new ItemBean();
            item.setCode(code);
            item.setName(name);
            item.setIsPR(isPR);
            item.setIsSoldout(isSoldout);
            request.getSession().setAttribute("serchitem",item);

            T001_ITEMDao itemDao = new T001_ITEMDao();
            String nextPage      = null; // ページ変遷のための変数
            List<ItemBean>list  = new ArrayList<ItemBean>(); // リストの作成
            list                 = itemDao.Search(code, name, isPR,isSoldout); // サーチメソッドから返ってきた値をリストに追加
            request.getSession().setAttribute("cccc","0"); // リクエストスコープに保存
            request.setAttribute("csv",list); // リクエストスコープに保存
            request.getSession().setAttribute("sltm",list);

            int listsize = list.size();
            if (listsize == 0) {
                request.setAttribute("kekka","条件に合う商品がありませんでした");//リクエストスコープに保存
            }
            if (list == null) {
                nextPage = "/list.jsp";
            } else {
                nextPage = "/list.jsp";
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (SQLException e) {
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/list.jsp").forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
