package drinkMachine;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import drinkMachine.Dao.T001_ITEMDao;

public class UpdateController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public UpdateController()
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
        response.setCharacterEncoding("UTF-8"); // 文字コードの指定

        // アップロードファイルを格納するPATHを取得
        String path = "C:\\ecrips_workspace\\jspServlet\\WebContent\\image";

        // ServletFileUploadオブジェクトを生成
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload    = new ServletFileUpload(factory);

        // アップロードする際の基準値を設定
        factory.setSizeThreshold(1024);
        upload.setSizeMax(-1);
        upload.setHeaderEncoding("UTF-8");

        String code      = null;
        String name      = null;
        String unitPrice = null;
        String count     = null;
        String isPR      = null;
        String image     = null;
        String fileName  = null;

        /*
        * ファイルデータ(FileItemオブジェクト)を取得し、
        * Listオブジェクトとして返す
        */
        List list;
        try {
            list = upload.parseRequest(request);

            // ファイルデータ(FileItemオブジェクト)を順に処理
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                FileItem fItem = (FileItem) iterator.next();

                // ファイルデータの場合、if内を実行
                if (!(fItem.isFormField())) {

                    // ファイルデータのファイル名(PATH名含む)を取得
                    fileName = fItem.getName();
                    if ((fileName != null) && (!fileName.equals(""))) {

                        // PATH名を除くファイル名のみを取得
                        fileName = (new File(fileName)).getName();
                        if (fileName.endsWith("jpg")) {

                            // ファイルデータを指定されたファイルに書き出し
                            fItem.write(new File(path + "\\" + fileName));
                            if (fItem.getFieldName().equals("image")) {
                                image = fItem.getName();
                            }
                        }
                    }
                } else {
                    String data = fItem.getString("utf-8");

                    // パラメーター"name"の値を取り出し、変数Nameに代入(商品名の入力)
                    if (fItem.getFieldName().equals("code")) {
                        code = data;
                    } else if (fItem.getFieldName().equals("name")) {
                        name = data;
                    } else if (fItem.getFieldName().equals("unitPrice")) {
                        unitPrice = data;
                    } else if (fItem.getFieldName().equals("count")) {
                        count = data;
                    } else if (fItem.getFieldName().equals("isPR")) {
                        isPR = data;
                    }
                }
            }
        } catch (FileUploadException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int math          = path.indexOf("image");
        path              = path.substring(math) + "\\\\" + fileName;
        ItemBean itembean = new ItemBean();
        itembean.setCode(code);
        itembean.setName(name);
        itembean.setPrice(unitPrice);
        itembean.setCount(count);
        itembean.setImage(path);
        itembean.setIsPR(isPR);
        T001_ITEMDao itemDao;
        try {
            itemDao             = new T001_ITEMDao();
            String beforeDate   = (String) request.getSession().getAttribute("RECORD_DATE"); // 商品を格納した日時を入手
            ResultSet resultSet = new T001_ITEMDao().getRecordDate(code); // 商品を格納した日時を入手
            String afterDate    = null;
            while (resultSet.next()) {
                afterDate = resultSet.getString("RECORD_DATE");
            }
            if (afterDate != null && !equals("")) {
                if (beforeDate.equals(afterDate)) {
                    String updateResult = new T001_ITEMDao().UpDate(itembean); // 更新成功
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
                    if (updateResult.equals("0")) {
                        request.setAttribute("kekka", "更新した商品は削除されています");
                    } else {
                            request.setAttribute("kekka", "商品を更新しました");
                    }
                    ServletContext application = getServletContext();
                    application.getRequestDispatcher("/list.jsp").forward(request,response);
                } else {
                    request.setAttribute("errormessage","更新済みエラー"); // 更新失敗
                    ServletContext application = getServletContext();
                    application.getRequestDispatcher("/list.jsp").forward(request,response);
                }
            } else if(afterDate == null || afterDate == "") {
                request.setAttribute("errormessage","削除済みエラー");
                ServletContext application = getServletContext();
                application.getRequestDispatcher("/list.jsp").forward(request,response);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
