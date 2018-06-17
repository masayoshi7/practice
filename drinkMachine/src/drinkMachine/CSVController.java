package drinkMachine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
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

public class CSVController extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public CSVController()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // (1)アップロードファイルを格納するPATHを取得
        String path = "C:\\eclipse\\workspece2\\jspServlet\\WebContent\\csv";

        // (2)ServletFileUploadオブジェクトを生成
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // (3)アップロードする際の基準値を設定
        factory.setSizeThreshold(1024);
        upload.setSizeMax(-1);
        upload.setHeaderEncoding("MS932");

        String code      = null;
        String name      = null;
        String unitPrice = null;
        String count     = null;
        String isPR      = null;
        String image     = null;
        String fileName  = null;
        String error     = ""; // エラーログ用
        int errorCount   = 0;  // エラーログ用
        List errorlist   = new ArrayList();

        /*
        * (4)ファイルデータ(FileItemオブジェクト)を取得し、
        * Listオブジェクトとして返す
        */
        List list;
        try {
            list = upload.parseRequest(request);

            // (5)ファイルデータ(FileItemオブジェクト)を順に処理
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                FileItem fItem = (FileItem) iterator.next();

                // (6)ファイルデータの場合、if内を実行
                if (!(fItem.isFormField())) {

                    // (7)ファイルデータのファイル名(PATH名含む)を取得
                    fileName = fItem.getName();
                    if ((fileName != null) && (!fileName.equals(""))) {

                        // (8)PATH名を除くファイル名のみを取得
                        fileName = (new File(fileName)).getName();

                        // (9)ファイルデータを指定されたファイルに書き出し
                        fItem.write(new File(path + "\\" + fileName));
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
                if (fileName.endsWith("csv")) {
                    FileReader fr     = new FileReader(path + "\\" + fileName);//指定されたパスのファイルを操作
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path + "\\" + fileName),"SJIS"));//文字コードの調整

                    // BufferedReaderオブジェクトの生成
                    T001_ITEMDao itemdao    = new T001_ITEMDao();
                    String image1           = "csv";
                    String str              = null;
                    List <ItemBean> csvlist = new ArrayList();

                    // ファイルから一行だけ読み込み
                    while ((str = br.readLine()) != null) {
                        StringTokenizer token = new StringTokenizer(str, ",");

                        // 分割した文字を画面出力する
                        while (token.hasMoreTokens()) {
                            ItemBean itembean = new ItemBean();
                            itembean.setCode(token.nextToken());
                            itembean.setName(token.nextToken());
                            itembean.setPrice(token.nextToken());
                            itembean.setCount(token.nextToken());
                            itembean.setIsPR(token.nextToken());
                            itembean.setImage("csv");
                            csvlist.add(itembean);
                        }
                    }
                    int csvlistSize = csvlist.size();
                    for (int i = 0; i < csvlistSize; i++) {
                        if (csvlist.get(i).getCode().equals("0")) {
                            if (itemdao.checkAdd(csvlist.get(i).getName()).equals("0")) {
                                itemdao.addItem((csvlist.get(i).getCode()) ,csvlist.get(i).getName(),
                                                 csvlist.get(i).getPrice(), csvlist.get(i).getCount(), csvlist.get(i).getIsPR(),image1);
                            } else {
                                errorCount++;
                                errorlist.add(i);
                            }
                        } else {

                            // アップデート文へ
                            itemdao.UpDate(csvlist.get(i));
                        }
                    }
                }
                error = "登録した商品の重複は" + errorCount + "個です";
            }

            request.setAttribute("kekka",error);
            if (!(fileName.endsWith("csv"))) {
                request.setAttribute("kekka","csv形式のファイルを登録してください");
            }
            ServletContext application = getServletContext();
            application.getRequestDispatcher("/list.jsp").forward(request,response); // ページの移動を行う

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("kekka", "ファイルの形式が正しくないか,ファイル内の書式が正しくありません");
        }
    }
}
