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


public class T001_ITEMDao
{
    // DBインスタンス変数
    private Connection conn;

    /*
     * DBインスタンスをメンバ変数の格納する
     */
    public T001_ITEMDao()
    {
    	// dbアダプターの取得
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nishiguchi?useUnicode=true&characterEncoding=utf8","nishiguchi","nishiguchi"); // 接続の確立
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * DBにアイテムを挿入する
     *
     * @param String name  商品名
     * @param String price 商品の価格
     * @param String count 商品の在庫数
     * @param String isPR  おすすめ商品フラグ
     * @param String image 商品画像のパス
     *
     * @return int 登録された件数を返す
     */
    public int registItem(String name, String price, String count, String isPR, String image)
    {
        // データベースに入力されたデータを格納するメソッド
        PreparedStatement pstmt = null;
        int result              = 0;
        String sql              = "INSERT INTO `t001_item`" +
                                  "(`ITEM_NM`, `UNIT_PRICE`, `STOCK_COUNT`, `IS_PR`, `ITEM_IMAGE_FILE_PATH`, `RECORD_DATE`) " +
                                  "VALUES (?, ?, ?, ?, ?, now())";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setString(2,price);
            pstmt.setString(3,count);
            pstmt.setString(4,isPR);
            pstmt.setString(5,image);
            result = pstmt.executeUpdate(); // SQL文の実行
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /*
     * データが重複していないかをチェックする
     *
     * @param String name  商品名
     *
     * @return String 重複している商品の数を返す
     */
    public String duplicationCheck(String name)
    {
        String duplicationCount = null;
        String sql              = "SELECT count(*) as checkItem FROM t001_item WHERE ITEM_NM = ?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            ResultSet resultSet = pstmt.executeQuery(); // SQL文の実行
            while (resultSet.next()) {
                duplicationCount = resultSet.getString("checkItem"); // checlItemの数を数える
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return duplicationCount;
    }

    /*
     * 商品情報を検索する
     *
     * @param String name      商品名
     * @param String price     商品の価格
     * @param String count     商品の在庫数
     * @param String isPR      おすすめ商品フラグ
     * @param String isSoldout 売り切れフラグ
     *
     * @return String 検索した商品のリストを返す
     */
    public List<ItemBean> Search (String code, String name, String isPR, String isSoldout)
    {
        PreparedStatement pstmt;
        List<ItemBean> list = new ArrayList<ItemBean>();
        try {
            String sql = null;
            sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH, IS_SOLD_OUT" + // 商品検索のSQL文
                  " FROM t001_item WHERE ITEM_NO Like ? AND ITEM_NM Like ?";
            if (isPR != null) {
                sql += " AND is_PR = ?";
            }
            if (isSoldout.equals("1")) {
                sql += " AND IS_SOLD_OUT = 1";
            }
            pstmt = conn.prepareStatement(sql);
            if (code != null) {
                pstmt.setString(1, code + "%");
            } else {
                pstmt.setString(1, code);
            }
            if (name != null) {
                pstmt.setString(2, "%" + name + "%");
            } else {
                pstmt.setString(2, name);
            }
            if (isPR != null) {
                pstmt.setString(3, isPR);
            }

            ResultSet resultSet = pstmt.executeQuery(); // SQL文の実行
            while (resultSet.next()) {
                ItemBean selectedItm = new ItemBean(); // javabeansのインスタンス化
                selectedItm.setCode(resultSet.getString("ITEM_NO"));
                selectedItm.setName(resultSet.getString("ITEM_NM"));
                selectedItm.setPrice(resultSet.getString("UNIT_PRICE"));
                selectedItm.setCount(resultSet.getString("STOCK_COUNT"));
                selectedItm.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
                list.add(selectedItm);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /*
     * 商品更新の際に表示する情報を取得する
     *
     * @param String code 商品id
     *
     * @return ItemBean 商品情報のクラスを返す
     */
    public ItemBean edit(String code)
    {
        ItemBean itembean = new ItemBean();
        String sql        = "SELECT `ITEM_NO`, `ITEM_NM`, `UNIT_PRICE`, `STOCK_COUNT`, `IS_PR`, `ITEM_IMAGE_FILE_PATH` " +
                            "FROM `t001_item` WHERE ITEM_NO = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            ResultSet resultSet = pstmt.executeQuery();
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
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return itembean;
    }

    /*
     * 商品更新の際に表示する情報を取得する
     *
     * @param  ItemBean 更新する情報の入った商品情報クラス
     * @return ItemBean 更新結果を返す
     */
    public int UpDate(ItemBean itembean)
    {
        String name = itembean.getCount(); // 更新対象の名前
        System.out.println(itembean.getCount());
        String soldOutFlag = "0";
        PreparedStatement pstmt;

        // 商品の在庫数が0ならば売り切れフラグを立てる
        if (!(itembean.getCount().equals("0"))) {
        	soldOutFlag = "0";
        } else {
        	soldOutFlag = "1";
        }
        String sql;
        int updateResult = 0;
        try {
        	/*
            T001_ITEMDao itemdao = new T001_ITEMDao();

        	//画像パスが入力された場合にパスを更新する
            if (itembean.getImage() != null) {
                sql = "UPDATE t001_item SET" +
                      "ITEM_NM = ?," +
                      "UNIT_PRICE = ?," +
                      "STOCK_COUNT = ?," +
                      "IS_SOLD_OUT = ?," +
                      "IS_PR= ?," +
                      "ITEM_IMAGE_FILE_PATH = ?,"+
                      "RECORD_DATE = CURRENT_TIMESTAMP " +
                      "WHERE ITEM_NO = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, itembean.getName());
                pstmt.setString(2, itembean.getPrice());
                pstmt.setString(3, itembean.getCount());
                pstmt.setString(4, soldOutFlag);
                pstmt.setString(5, itembean.getIsPR());
                pstmt.setString(6, itembean.getImage());
                pstmt.setString(7, itembean.getCode());

			*/
            // 画像パスが入力されなかった場合画像パスは更新しない
            //} else {
            	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbababab");
                 sql =   "UPDATE t001_item SET " +
                         "ITEM_NM = ? ," +
                         "UNIT_PRICE = ? ," +
                         "STOCK_COUNT = ? ," +
                         "IS_SOLD_OUT = ? ," +
                         "IS_PR = ? , " +
                         "RECORD_DATE = CURRENT_TIMESTAMP " +
                         "WHERE ITEM_NO = ? ";

                 pstmt = conn.prepareStatement(sql);
                 pstmt.setString(1, itembean.getName());
                 pstmt.setString(2, itembean.getPrice());
                 pstmt.setString(3, itembean.getCount());
                 pstmt.setString(4, soldOutFlag);
                 pstmt.setString(5, itembean.getIsPR());
                 pstmt.setString(6, itembean.getCode());
            //}
            updateResult = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateResult;
    }

    public ResultSet Date(String code)
    {
        String sql              = "SELECT * FROM t001_item WHERE ITEM_NO = ?"; // RECORD_DATEを入手
        PreparedStatement pstmt;
        ResultSet resultSet1 = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            resultSet1 = pstmt.executeQuery(); // sql文の実行
        } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return resultSet1; // 商品を格納した値を返す
    }

    public ResultSet getRecordDate(String code)
    {

        String sql           = "SELECT * FROM t001_item WHERE ITEM_NO = ?"; // RECORD_DATEを入手
        ResultSet resultSet = null;
        try {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, code);
            resultSet = pstmt.executeQuery(); // sql文の実行
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    // 登録した商品を削除するメソッド
    public int Delete(String code)
    {
        String sql   = "DELETE FROM t001_item WHERE ITEM_NO = ?";
        int result = 0;
        try {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, code);
            result = pstmt.executeUpdate(); // sql文の実行
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<ItemBean> isPRItem()
    {
        List<ItemBean> PRItemList = new ArrayList<ItemBean>();
        String sql      = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT,ITEM_IMAGE_FILE_PATH" +//商品検索のSQL文
                          " FROM t001_item WHERE IS_PR = 1";
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PRItemList;
    }

    public List<ItemBean> NomalItem()
    {
        List<ItemBean> NomalItemList = new ArrayList<ItemBean>();
        String sql         = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT, ITEM_IMAGE_FILE_PATH" +//商品検索のSQL文
                             " FROM t001_item WHERE IS_PR = 0";

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return NomalItemList;
    }

    public void mathUpDate(String code, String count)
    {
        try {
            String sql = "SELECT ITEM_NO, ITEM_NM, UNIT_PRICE, STOCK_COUNT" +//商品検索する検索するSQL文
                         " FROM t001_item WHERE ITEM_NO = ?";
            int math      = Integer.parseInt(count);
            if (math > 0) {
                math = (math - 1);
            }
            String mathString    = String.valueOf(math);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, code);
            ResultSet resultSet  = pstmt.executeQuery(); // sql文の実行
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

            sql     = "UPDATE t001_item SET " +
                      "ITEM_NM = ? ," +
                      "UNIT_PRICE = ? ," +
                      "STOCK_COUNT = ? ," +
                      "IS_SOLD_OUT = ? ," +
                      "RECORD_DATE = CURRENT_TIMESTAMP " +
                      "WHERE ITEM_NO = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, selectedItm.getName());
            pstmt.setString(2, selectedItm.getPrice());
            pstmt.setString(3, selectedItm.getCount());
            pstmt.setString(4, set);
            pstmt.setString(5, selectedItm.getCode());
            pstmt.executeUpdate(); // sql文の実行
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
    }

    public List<ItemBean> allSerch()
    {
        List<ItemBean> list  = new ArrayList<ItemBean>();
        String sql = "SELECT * FROM t001_item";
        try {
            Statement stmt      = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql); // SQL文の実行

            while (resultSet.next()) {
                ItemBean selectedItm = new ItemBean(); // javabeansのインスタンス化
                selectedItm.setCode(resultSet.getString("ITEM_NO"));
                selectedItm.setName(resultSet.getString("ITEM_NM"));
                selectedItm.setPrice(resultSet.getString("UNIT_PRICE"));
                selectedItm.setCount(resultSet.getString("STOCK_COUNT"));
                selectedItm.setImage(resultSet.getString("ITEM_IMAGE_FILE_PATH"));
                list.add(selectedItm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
            // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        return list;
    }
}
