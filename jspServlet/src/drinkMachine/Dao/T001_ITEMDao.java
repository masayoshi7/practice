package drinkMachine.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import drinkMachine.ItemBean;

/*
* TODO ・プリペアードステートメントを使わずにsqlに直接結合している所が
*      　多々あるので直す
*      ・DB接続をcloseしてない箇所が多々ある。
*        ガベージコレクションに頼らず明示的に示すべきか。
*/

public class T001_ITEMDao
{
    private Connection conn;

    public T001_ITEMDao() throws ClassNotFoundException,SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost/EDU_M_NISIGUTI";
        conn = DriverManager.getConnection("jdbc:mysql://localhost/EDU_M_NISIGUTI","root","MASAYOSHI777"); // 接続の確立
    }

    public int addItem(String code,String name,String price,String count, String isPR, String image)
    {
        // データベースに入力されたデータを格納するメソッド
        PreparedStatement pstmt = null;
        int result              = 0;

        try {
            String query = "INSERT INTO T001_ITEM(ITEM_NM,UNIT_PRICE,STOCK_COUNT,IS_PR,ITEM_IMAGE_FILE_PATH,RECORD_DATE) VALUES (?,?,?,?,?,now())";
            pstmt        = conn.prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setString(2,price);
            pstmt.setString(3,count);
            pstmt.setString(4,isPR);
            pstmt.setString(5,image);

            result = pstmt.executeUpdate(); // SQL文の実行
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result; // 登録できたかどうかの数値を返す
    }

    public String checkAdd(String name) throws SQLException
    {
        // データが重複していないかをチェックするメソッド
        String kensaku = null;

        try {
          String sql          = "SELECT count(*) as checkItem" + " FROM T001_ITEM" +
                                "WHERE ITEM_NM ='" + name + "'";
          Statement stmt      = conn.prepareStatement(sql);
          ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行
          while (resultSet.next()) {
                kensaku = resultSet.getString("checkItem"); // checlItemの数を数える
          }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return kensaku;
    }

    // 商品を検索するメソッド
    public List<ItemBean> Search (String code, String name, String isPR,String isSoldout)
    {
        String kensaku      = null;
        List<ItemBean> list = new ArrayList<ItemBean>();
        try {
            String sql = null;
            if (isPR == null) {
                // 商品検索のSQL文
                sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH, IS_SOLD_OUT" +
                      "FROM T001_ITEM " + "WHERE ITEM_NO Like '" + code + "%'" +
                      "AND ITEM_NM Like '%" + name + "%'" + "AND IS_SOLD_OUT Like '" + isSoldout + "'";

                if (isSoldout.equals("0")) {
                    sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH, IS_SOLD_OUT" + // 商品検索のSQL文
                          "FROM T001_ITEM " + "WHERE ITEM_NO Like '" + code + "%'" + "AND ITEM_NM Like '%" + name + "%'";
                }
            } else if (isPR.equals("1") || isPR.equals("0")) {
                sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH, IS_SOLD_OUT" + // 商品検索のSQL文
                      "FROM T001_ITEM " + "WHERE ITEM_NO Like '" + code + "%'" + "AND ITEM_NM Like '%" + name + "%'" +
                      "AND IS_PR Like '" + isPR + "'" + "AND IS_SOLD_OUT Like '" + isSoldout + "'";

                if (isSoldout.equals("0")) {
                    sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH, IS_SOLD_OUT" + // 商品検索のSQL文
                          " FROM T001_ITEM " + "WHERE ITEM_NO Like '" + code + "%'" + "AND ITEM_NM Like '%" + name + "%'" + "AND IS_PR Like '" + isPR + "'";
                }
            }
            Statement stmt      = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行

            while (resultSet.next()) {
                ItemBean selectedItm = new ItemBean(); // javabeansのインスタンス化
                kensaku              = resultSet.getString("ITEM_NM");
                selectedItm.setCode(resultSet.getString("ITEM_NO"));
                selectedItm.setName(resultSet.getString("ITEM_NM"));
                selectedItm.setPrice(resultSet.getString("UNIT_PRICE"));
                selectedItm.setCount(resultSet.getString("STOCK_COUNT"));
                selectedItm.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
                list.add(selectedItm);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 商品変更画面にデータを表示するメソッド
    public ItemBean edit(String code)
    {
        ItemBean itembean = new ItemBean();
        try {
            String sql          = "SELECT ITEM_NO,ITEM_NM,UNIT_PRICE,STOCK_COUNT,IS_PR,ITEM_IMAGE_FILE_PATH" +
                                  "FROM T001_ITEM WHERE ITEM_NO ='" + code + "'";
            Statement stmt      = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                itembean.setCode(resultSet.getString("ITEM_NO"));
                itembean.setName(resultSet.getString("ITEM_NM"));
                itembean.setPrice(resultSet.getString("UNIT_PRICE"));
                itembean.setCount(resultSet.getString("STOCK_COUNT"));
                itembean.setIsPR(resultSet.getString("IS_PR"));
                itembean.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itembean;
    }

    // 商品を更新するメソッド
    public String UpDate(ItemBean itembean)
    {
        String chk = itembean.getName();
        String a = "0";
        if (!(itembean.getCount().equals("0"))) {
            a = "0";
        } else {
            a = "1";
        }
        String sql = null;

        // 画像パスが入力されなかった場合画像パスも更新する
        if (!(itembean.getImage().equals("image\\\\"))) {
            sql = "UPDATE EDU_M_NISIGUTI.T001_ITEM SET" +
                  "ITEM_NM = '"+itembean.getName()+"'," +
                  "UNIT_PRICE = " +itembean.getPrice()+ " , " +
                  "STOCK_COUNT = " +itembean.getCount()+ " ," +
                  "IS_SOLD_OUT = " +a+ "," +
                  "IS_PR= " + itembean.getIsPR() + " , " +
                  "ITEM_IMAGE_FILE_PATH ='"+itembean.getImage()+"',"+
                  "RECORD_DATE = CURRENT_TIMESTAMP " +
                  "WHERE ITEM_NO = " + itembean.getCode();

        // 画像パスが入力された場合画像パスも更新する
        } else if (itembean.getImage().equals("image\\\\")) {
             sql = "UPDATE EDU_M_NISIGUTI.T001_ITEM SET " +
                   "ITEM_NM = '"+itembean.getName()+"'," +
                   "UNIT_PRICE = " +itembean.getPrice()+ " , " +
                   "STOCK_COUNT = " +itembean.getCount()+ " ," +
                   "IS_SOLD_OUT = " +a+ "," +
                   "IS_PR= " + itembean.getIsPR() + " , " +
                   "RECORD_DATE = CURRENT_TIMESTAMP " +
                   "WHERE ITEM_NO = " + itembean.getCode();
        }
        int count     = 0;
        String count2 = null;
        try {
            T001_ITEMDao itemdao = new T001_ITEMDao();
            count2               = itemdao.checkAdd(chk);

            if (count2.equals("1")) {
                Statement stmt = conn.createStatement();
                stmt = conn.prepareStatement(sql);
                count = stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count2;
    }

    public ResultSet Date(String code) throws SQLException, ClassNotFoundException
    {
        String sql1          = "SELECT * FROM T001_ITEM WHERE ITEM_NO ='" + code + "'"; // RECORD_DATEを入手
        Statement stmt       = conn.createStatement();
        ResultSet resultSet1 = stmt.executeQuery(sql1); // sql文の実行

        return resultSet1; // 商品を格納した値を返す
    }

    public ResultSet getRecordDate(String code)
    {

        String sql1         = "SELECT * FROM T001_ITEM WHERE ITEM_NO ='" + code + "'";//RECORD_DATEを入手
        ResultSet resultSet = null;
        Statement stmt;
        try {
            stmt      = conn.createStatement();
            resultSet = stmt.executeQuery(sql1); // sql文の実行
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // 登録した商品を削除するメソッド
    public int Delete(String code)
    {
         String sql   = "SELECT count(*) as checkItem"+" FROM T001_ITEM" +
                        "WHERE ITEM_NO ='" + code + "'";

        String sql1   = "DELETE FROM T001_ITEM WHERE ITEM_NO = '" + code + "'";
        int count     = 0;
        String countd = null;
        Statement stmt;
        try {
            stmt                = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行
            while (resultSet.next()) {
                countd = resultSet.getString("checkItem"); // checlItemの数を数える
            }

            if (countd.equals("1")) {
                count = stmt.executeUpdate(sql1); // sql文の実行
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public List isPRItem()
    {
        List PRItemList = new ArrayList();
        int i           = 1;
        String sql      = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT,ITEM_IMAGE_FILE_PATH" +//商品検索のSQL文
                          "FROM T001_ITEM " + "WHERE IS_PR Like '" + i + "'";

        try {
            Statement stmt      = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行

            while (resultSet.next()) {
                ItemBean item = new ItemBean(); // javabeansのインスタンス化;
                item.setCode(resultSet.getString("ITEM_NO"));
                item.setName(resultSet.getString("ITEM_NM"));
                item.setPrice(resultSet.getString("UNIT_PRICE"));
                item.setCount(resultSet.getString("STOCK_COUNT"));
                item.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
                PRItemList.add(item);
            }
            ItemBean test = (ItemBean) PRItemList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PRItemList;
    }

    public List NomalItem()
    {
        List NomalItemList = new ArrayList();
        int i              = 0;
        String sql         = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH" +//商品検索のSQL文
                             " FROM T001_ITEM " + "WHERE IS_PR Like '" + i + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行

            while (resultSet.next()) {
                ItemBean item = new ItemBean(); // javabeansのインスタンス化;
                item.setCode(resultSet.getString("ITEM_NO"));
                item.setName(resultSet.getString("ITEM_NM"));
                item.setPrice(resultSet.getString("UNIT_PRICE"));
                item.setCount(resultSet.getString("STOCK_COUNT"));
                item.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
                NomalItemList.add(item);
            }
            ItemBean test = (ItemBean) NomalItemList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NomalItemList;
    }

    public void mathUpDate(String code, String count)
    {
        try {
            String sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT" +//商品検更新するために検索するSQL文
                         " FROM T001_ITEM " + "WHERE ITEM_NO = '" + code + "'";
            int math      = Integer.parseInt(count);
            if (math > 0) {
                math = (math - 1);
            }
            String mathString    = String.valueOf(math);
            Statement stmt       = conn.createStatement();
            ResultSet resultSet  = stmt.executeQuery(sql); // SQL文の実行
            ItemBean selectedItm = new ItemBean(); // javabeansのインスタンス化
            while (resultSet.next()) {
                selectedItm.setCode(resultSet.getString("ITEM_NO"));
                selectedItm.setName(resultSet.getString("ITEM_NM"));
                selectedItm.setPrice(resultSet.getString("UNIT_PRICE"));
                selectedItm.setCount(mathString);
            }

            String test = selectedItm.getCount();
            String set  = "0";
            // 在庫数０の場合売り切れフラグを追加
            if (test.equals(set)) {
                set = "1";
            }

            String sql1     = "UPDATE EDU_M_NISIGUTI.T001_ITEM SET " +
                              "ITEM_NM = '" + selectedItm.getName() + "'," +
                              "UNIT_PRICE = " + selectedItm.getPrice() + "," +
                              "STOCK_COUNT = " + selectedItm.getCount() + "," +
                              "IS_SOLD_OUT = " + set + "," +
                              "RECORD_DATE = CURRENT_TIMESTAMP " +
                              "WHERE ITEM_NO = " + selectedItm.getCode();
            int count1      = 0;
            Statement stmt1 = conn.createStatement();
            stmt1           = conn.prepareStatement(sql1);
            count1          = stmt1.executeUpdate(sql1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List allSerch()
    {
        List list  = new ArrayList();
        String sql = "SELECT * FROM T001_ITEM";
        String kensaku;
        try {
            Statement stmt      = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行

            while (resultSet.next()) {
                ItemBean selectedItm = new ItemBean(); // javabeansのインスタンス化
                kensaku              = resultSet.getString("ITEM_NM");
                selectedItm.setCode(resultSet.getString("ITEM_NO"));
                selectedItm.setName(resultSet.getString("ITEM_NM"));
                selectedItm.setPrice(resultSet.getString("UNIT_PRICE"));
                selectedItm.setCount(resultSet.getString("STOCK_COUNT"));
                selectedItm.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
                list.add(selectedItm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
