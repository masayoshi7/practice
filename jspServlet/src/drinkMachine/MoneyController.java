package drinkMachine;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import drinkMachine.Dao.AcountDao;

public class MoneyController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public MoneyController()
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
        response.setCharacterEncoding("UTF-8"); // 文字形式の設定

        String money = request.getParameter("name");
        response.getContentType();
        int addmoney        = Integer.parseInt(money); // 入力した金額を取得し、intに変換
        HttpSession session = request.getSession(false);
        AcountBean acount   = (AcountBean)session.getAttribute("acount");
        String acountname   = acount.getName(); // アカウント名を取得
        int acountmoney     = acount.getMoney(); // データベースより所持金を取得
        int totalmoney      = addmoney + acountmoney; // データベースに挿入する金額
        acount.setMoney(totalmoney); // 合計金額をアカウントビーンにセット

        try {
            AcountDao acountdao = new AcountDao();
            acountdao.acountUpdate(acount); // アカウント情報を更新
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            String nextPage            = "/myPage.jsp";
            ServletContext application = getServletContext();
            application.getRequestDispatcher(nextPage).forward(request,response);
        }
    }
}
