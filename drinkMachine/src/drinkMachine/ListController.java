package drinkMachine;

import java.io.IOException;
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
        ServletContext application = getServletContext();
        application.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String code      = request.getParameter("code"); // 商品コード
        String name      = request.getParameter("name");
        String type       = request.getParameter("type");
        String isSoldout = request.getParameter("isSoldout");
        String isPR = null;
        if (type.equals("1")) {
            isPR = null;
        } else if (type.equals("2")) {

        	// おすすめ商品フラグを立てる
            isPR = "1";
        } else {
            isPR = "0";
        }
        ItemBean item = new ItemBean();
        item.setCode(code);
        item.setName(name);
        item.setIsPR(type);
        item.setIsSoldout(isSoldout);
        request.getSession().setAttribute("serchitem",item);

        T001_ITEMDao itemDao = new T001_ITEMDao();
        List<ItemBean>itemList  = new ArrayList<ItemBean>(); // リストの作成
        itemList                = itemDao.Search(code, name, isPR,isSoldout); // サーチメソッドから返ってきた値をリストに追加
        request.setAttribute("csv",itemList); // リクエストスコープに保存
        request.getSession().setAttribute("itemList", itemList);
        int listsize = itemList.size();
        if (listsize == 0) {
            request.setAttribute("kekka","条件に合う商品がありませんでした");//リクエストスコープに保存
        }

        ServletContext application = getServletContext();
        application.getRequestDispatcher("/list.jsp").forward(request,response);

    }
}
