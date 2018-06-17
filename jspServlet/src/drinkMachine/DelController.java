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
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String code = request.getParameter("code");
        T001_ITEMDao itemDao;
        try {
            itemDao             = new T001_ITEMDao();
            int count           = itemDao.Delete(code); // 商品の削除を実行
            HttpSession session = request.getSession(false);
            ItemBean item       = (ItemBean)session.getAttribute("serchitem"); // 商品情報を再検索
            String code1        = item.getCode();
            String name1        = item.getName();
            String isPR1        = item.getIsPR();
            String isSoldOut    = item.getIsSoldout();
            T001_ITEMDao dao    = new T001_ITEMDao();
            List list1          = new ArrayList();

            if (session.getAttribute("cccc").equals("0")) {
                list1 = dao.Search(code1, name1, isPR1, isSoldOut);
                request.getSession().setAttribute("sltm",list1);
            } else if (session.getAttribute("cccc").equals("1")) {
                list1 = dao.allSerch();
                request.getSession().setAttribute("sltm",list1);
            }

            if(count != 1) {
                request.setAttribute("kekka", "選択した商品は既に削除されています");
            } else {
                request.setAttribute("kekka", "商品を削除しました");
            }

            ServletContext application = getServletContext();
            application.getRequestDispatcher("/list.jsp").forward(request,response); // ページの移動を行う
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
