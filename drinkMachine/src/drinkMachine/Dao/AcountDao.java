package drinkMachine.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import drinkMachine.AcountBean;
import drinkMachine.ItemBean;

public class AcountDao
{
    private Connection conn;

    public AcountDao() throws ClassNotFoundException,SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost/EDU_M_NISIGUTI";
        conn = DriverManager.getConnection("jdbc:mysql://localhost/EDU_M_NISIGUTI","root","MASAYOSHI777");
    }

    // アカウントを登録するメソッド
    public int addAcount(String name,String pas)
    {
        PreparedStatement pstmt = null;
        int result = 0;

        try{
            String query ="INSERT INTO acount(NAME,PASWORD) VALUES (?,?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setString(2,pas);
            result = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result; // 登録できたかどうかの数値を返す
    }

    public String check(String name,String pas) throws SQLException
    {
        // データが重複していないかをチェックするメソッド
        String kensaku = null;

        try {
          String sql          = "SELECT count(*) as checkItem" + " FROM acount" +
                                "WHERE NAME ='" + name + "'"+
                                "AND PASWORD ='" + pas + "'";
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

    public String checkName(String name) throws SQLException
    {
        // データが重複していないかをチェックするメソッド
        String kensaku = null;

        try {
          String sql          = "SELECT count(*) as checkItem" + " FROM acount" +
                                "WHERE NAME ='" + name + "'";
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

    // アカウント情報を取得するメソッド
    public AcountBean getAcount(String name)
    {
        AcountBean acountbean = new AcountBean();
        String sql            = "SELECT NO,NAME,PASWORD,MONY" +
                                "FROM ACOUNT WHERE NAME ='" + name + "'";
        Statement stmt;
        try {
            stmt                = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                acountbean.setNo(resultSet.getInt("NO"));
                acountbean.setName(resultSet.getString("NAME"));
                acountbean.setPas(resultSet.getString("PASWORD"));
                acountbean.setMoney(resultSet.getInt("MONY"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acountbean;
    }

    // アカウントの金額情報を更新するメソッド
    public void acountUpdate(AcountBean acountbean)
    {
        String sql = "UPDATE EDU_M_NISIGUTI.ACOUNT SET " +
                     "MONY = " + acountbean.getMoney() +
                     "WHERE NAME = " + "'" + acountbean.getName()+"'" ;
        try {
            Statement stmt = conn.createStatement();
            stmt           = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 商品購入の金額を計算するメソッド
    public int shoping(AcountBean acount,ItemBean item)
    {
        int price = Integer.parseInt(item.getPrice());
        int money = acount.getMoney() - price;

        // 所持金がマイナスになる場合、-1を代入して所持金が足りていないフラグを立てる
        if (money < -1) {
            money = -1;
        }
        return money;
    }

    // アカウントを削除するメソッド
    public void AcountDel(String no)
    {
        String sql = "DELETE FROM ACOUNT WHERE NO = '" + no + "'";

        try {
            Statement stmt = conn.createStatement();
            stmt           = conn.createStatement();
            int a          = stmt.executeUpdate(sql); // SQL文の実行
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
