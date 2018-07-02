package drinkMachine;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import drinkMachine.Dao.T001_ITEMDao;

public class AddController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public AddController()
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

        // アップロードファイルを格納するPATHを取得
        String path = "/var/lib/tomcat/webapps/image";
        System.out.println(path);

        // ServletFileUploadオブジェクトを生成
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

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
        List<ItemBean> list;
        /*
        * ファイルデータ(FileItemオブジェクト)を取得し、
        * Listオブジェクトとして返す
        */
        try {
            list              = upload.parseRequest(request);
            Iterator iterator = list.iterator(); // ファイルデータ(FileItemオブジェクト)を順に処理

            while (iterator.hasNext()) {
                FileItem fItem = (FileItem) iterator.next();

                if (!(fItem.isFormField())) {

                    // ファイルデータのファイル名(PATH名含む)を取得
                    fileName = fItem.getName();

                    if ((fileName != null) && (!fileName.equals(""))) {
                        fileName = (new File(fileName)).getName(); // PATH名を除くファイル名のみを取得
                        fItem.write(new File(path + "/" + fileName)); // ファイルデータを指定されたファイルに書き出し

                        if (fItem.getFieldName().equals("image")) {
                            image = fItem.getName();
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
        int math = path.indexOf("image");
        path     = "/image/" + fileName;

            T001_ITEMDao itemDao = new T001_ITEMDao();
            String checkItem     = itemDao.duplicationCheck(name); // 重複していないかチェック
            String nextPage      = null;

            // 重複している場合は登録画面に戻る
            if (!checkItem.equals("0")) {
                request.setAttribute("result", "登録した商品は既に存在しています");
                request.setAttribute("kensaku",checkItem);
                nextPage = "/add.jsp";
            } else if (fileName.endsWith("jpg") || path.equals("image/")) {

                // 値をデータベースに格納する
                int result = itemDao.registItem(name, unitPrice, count, isPR, path);

                // 正しくデータベースに格納された場合商品一覧へ
                if (result == 1) {
                    request.setAttribute("result", "");
                    nextPage = "/list.jsp";
                } else {
                    request.setAttribute("result", "商品の登録に失敗しました");
                    nextPage = "/add.jsp"; // 正しく格納されなかった場合は登録画面に戻る
                }
            } else {
                request.setAttribute("result", "jpg形式のファイルを登録してください");
                nextPage = "/add.jsp";
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher(nextPage).forward(request,response);// 上記の結果によりページの移動を行う

    }
}
