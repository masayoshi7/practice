package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import drinkMachine.Dao.AcountDao;
import drinkMachine.Dao.T001_ITEMDao;

public class CartController2 extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public CartController2()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset = UTF-8");
        response.setCharacterEncoding("UTF-8");

        String PRCode      = request.getParameter("PRcode");
        String PRCount     = request.getParameter("PRcount");
        String NomalCode   = request.getParameter("Nomalcode");
        String NomalCount  = request.getParameter("Nomalcount");
        String acountMoney = request.getParameter("Money");

        try {
            T001_ITEMDao Dao  = new T001_ITEMDao();
            ItemBean itembean = new ItemBean();

            if(!(NomalCode == null && NomalCount == null && PRCode == null && PRCount == null)) {
                if (NomalCode == null && NomalCount == null) {
                    itembean = Dao.edit(PRCode);
                } else if (PRCode == null && PRCount == null) {
                    itembean = Dao.edit(NomalCode);
                }
                HttpSession session = request.getSession(false);
                AcountBean acount   = (AcountBean)session.getAttribute("acount");
                AcountDao acountdao = new AcountDao();
                int acountmoney     = acountdao.shoping(acount,itembean); // ショッピングメソッドで金額を計算

                if (acountmoney != -1) {
                    acount.setMoney(acountmoney); // 計算した金額をビーンにセット
                    acountdao.acountUpdate(acount);
                    request.getSession().setAttribute("acount",acount);

                    if (NomalCode == null && NomalCount == null) {
                        Dao.mathUpDate(PRCode, PRCount);
                    } else if (PRCode == null && PRCount == null) {
                        Dao.mathUpDate(NomalCode, NomalCount);
                    }

                    T001_ITEMDao itemDao = new T001_ITEMDao();
                    List PRList          = itemDao.isPRItem();
                    List NomalList       = itemDao.NomalItem();
                    request.getSession().setAttribute("PRItem",PRList); // セッションスコープにPRアイテムを保存
                    request.getSession().setAttribute("NormalItem",NomalList); // セッションスコープにノーマルアイテムを保存
                } else {
                    request.setAttribute("errormoney", "お金が足りません");
                }
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/cart.jsp").forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
