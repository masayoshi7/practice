package drinkMachine.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import drinkMachine.AcountBean;
import drinkMachine.ItemBean;

public class AcountDao
{
    private Connection conn;

    public AcountDao()
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

    // データが重複していないかをチェックするメソッド
    public String check(String name,String pas)
    {
        String kensaku = "0";

        try {
          String sql =  "SELECT count(*) as count FROM acount WHERE NAME = ? AND PASWORD = ?";
          PreparedStatement pstmt = conn.prepareStatement(sql);

          //パラメーターセット
          pstmt.setString(1, name);
          pstmt.setString(2, pas);

          //SQL 実行
          ResultSet rs = pstmt.executeQuery();
          while (rs.next()) {
              kensaku = rs.getString("count");
          }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return kensaku;
    }

    // データが重複していないかをチェックするメソッド
    public String checkName(String name)
    {
        String kensaku = null;

        try {
          String sql          = "SELECT count(*) as count FROM acount WHERE NAME = ?";
          PreparedStatement pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, name);
          ResultSet resultSet = pstmt.executeQuery(); // SQL文の実行
          while (resultSet.next()) {
                kensaku = resultSet.getString("count"); // checlItemの数を数える
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
        String sql = "SELECT NO, NAME, PASWORD, MONY FROM acount WHERE NAME = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();
            System.out.println(resultSet);
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
        String sql = "UPDATE acount SET MONY = ? WHERE NAME = ?" ;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, acountbean.getMoney());
            pstmt.setString(2, acountbean.getName());
            pstmt.executeUpdate();
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
        String sql = "DELETE FROM acount WHERE NO = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, no);
            pstmt.executeUpdate(sql); // SQL文の実行
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
    }
}
