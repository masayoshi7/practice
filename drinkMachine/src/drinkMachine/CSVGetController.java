package drinkMachine;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CSVGetController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public CSVGetController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session  = request.getSession();
        List<ItemBean> list1 = (List<ItemBean>)session.getAttribute("itemList"); // 検索リストを入手

        // csvを格納するPATHを取得
        String path  = "ItemList.csv";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=" + path);
        String crlf   = System.getProperty("line.separator");
        Writer writer = new PrintWriter(new OutputStreamWriter(response.getOutputStream(),"utf-8"));
        int list1Size = list1.size();
        if (list1Size != 0) {
            try {
                
                for (int i = 0; i < list1Size; i++) {
                    ItemBean itembean = new ItemBean();
                    itembean = list1.get(i);
                    writer.write(itembean.getCode() + "," + itembean.getName() + "," + itembean.getPrice() + "," +
                                 itembean.getCount() + "," + itembean.getIsPR());
                    writer.write(crlf);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                writer.flush();
                writer.close();
            }
        } else {
            request.setAttribute("kekka", "商品を検索した状態で出力ボタンを押してください。");
        }
    }
}
