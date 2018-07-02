package drinkMachine;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.omg.CORBA.Request;
import drinkMachine.*;
import drinkMachine.Dao.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import drinkMachine.Dao.T001_ITEMDao;

public class EditController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public EditController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String id = request.getParameter("id");

        T001_ITEMDao itemDao = new T001_ITEMDao(); //アイテムビーンをインスタンス化
        ItemBean itembean    = new ItemBean();
        itembean             = itemDao.edit(id); // 商品変更画面に情報を持っていくのに使うアイテムビーン
        if (itembean.getName() == null) {
            request.setAttribute("msg", "この商品は既に削除されています");
        }

        request.setAttribute("itembean", itembean); // リクエストスコープに保存し、商品変更画面へ送る
        ServletContext application = getServletContext();
        application.getRequestDispatcher("/edit.jsp").forward(request,response);
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        
        
    }
}
