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

public class BuyController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public BuyController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String code      = request.getParameter("code");
        String count     = request.getParameter("count");

        T001_ITEMDao Dao  = new T001_ITEMDao();
        ItemBean itembean = new ItemBean();
        itembean = Dao.edit(code);

        // セッションオブジェクトの取得
        HttpSession session = request.getSession(false);
        AcountBean acount   = (AcountBean)session.getAttribute("acount");
        
        if (acount == null) {
            request.setAttribute("error", "ログインしてください");
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/login.jsp").forward(request,response);
        } else {
            AcountDao acountdao = new AcountDao();
            int acountmoney     = acountdao.shoping(acount,itembean); // ショッピングメソッドで金額を計算

            if (acountmoney != -1) {
                acount.setMoney(acountmoney); // 計算した金額をビーンにセット
                acountdao.acountUpdate(acount);
                session.setMaxInactiveInterval(1800);
                session.setAttribute("acount",acount);
                Dao  = new T001_ITEMDao();
                Dao.mathUpDate(code, count);
            } else {
                request.setAttribute("errormoney", "お金が足りません");
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/CartController").forward(request,response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        
    }
}
