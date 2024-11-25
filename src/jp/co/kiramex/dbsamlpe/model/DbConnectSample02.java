package jp.co.kiramex.dbsamlpe.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbConnectSample02 {

    public static void main(String[] args) {

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "83t9276u"
                );

            stmt = con.createStatement();

            String sql ="SELECT * FROM country where Code = 'ABW'";
            rs = stmt.executeQuery(sql);

            while( rs.next() ){
                //新要素
                System.out.println("更新前===================");
                if( rs.next() ){
                    // Name列の値を取得
                    String name = rs.getString("Name");
                    // Population列の値を取得
                    int population = rs.getInt("Population");
                    // 取得した値を表示
                    System.out.println(name + "\n" + population);
                }

                // 7-2. 更新処理を行なう
                System.out.println("更新処理実行=============");
                String updateSql = "update country set Population = 105000 where Code = 'ABW'";
                int count = stmt.executeUpdate(updateSql);
                System.out.println("更新行数：" + count);

                // 7-3. 更新後の結果を表示する
                rs.close();// 更新後の検索のため、一旦閉じる（閉じないと警告が出るため）
                System.out.println("更新後=================");
                rs = stmt.executeQuery(sql);
                if( rs.next() ){
                    // Name列の値を取得
                    String name = rs.getString("Name");
                    // Population列の値を取得
                    int population = rs.getInt("Population");
                    // 取得した値を表示
                    System.out.println(name + "\n" + population);
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();

        }finally {

            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }

            if(stmt !=null) {
                try {
                    stmt.close();
                    }catch(SQLException e) {
                        System.err.println("Statementを閉じるときにエラーが発生しました。");
                        e.printStackTrace();
                }
            }

            if(con !=null) {
                try {
                    con.close();
                }catch(SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }

    }

}
