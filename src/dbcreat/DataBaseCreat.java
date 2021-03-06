package dbcreat;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import kn.main.dao.utils.DBPool;

/**
 * Created by evannzhang on 2016/8/13.
 */
public class DataBaseCreat {

    public static void main (String [] args) throws Exception{
        createTables();
    }

    public static void createTables() throws SQLException {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
        String url = "jdbc:mysql://localhost:3306/yuebandb?"
                + "user=root&password=mm4477MMT&useUnicode=true&characterEncoding=UTF8";

        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
           // Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();

            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            //conn = DriverManager.getConnection(url);
            conn = DBPool.getInstance().getConnection();
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            sql = "CREATE TABLE `yueban_song` (\n" +
                    "  `songId` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `songName` varchar(255) CHARACTER SET utf8 NOT NULL,\n" +
                    "  `songSinger` varchar(255) CHARACTER SET utf8 NOT NULL,\n" +
                    "  `songUrl` varchar(255) CHARACTER SET utf8 NOT NULL,\n" +
                    "  `songCoverUrl` varchar(255) CHARACTER SET utf8 NOT NULL,\n" +
                    "  PRIMARY KEY (`songId`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n";
            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result == -1) {
                System.out.print("create yueban_song fail");
            }

            sql = "CREATE TABLE `yueban_songlist` (\n" +
                    "  `songListId` int(10) unsigned NOT NULL,\n" +
                    "  `songId` int(10) unsigned NOT NULL\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            if (result == -1) {
                System.out.print("create yueban_songlist fail");
            }

            sql = "CREATE TABLE `yueban_songlisttag` (\n" +
                    "  `songListId` int(10) unsigned NOT NULL,\n" +
                    "  `songMoodTag` tinyint(3) unsigned NOT NULL,\n" +
                    "  `songEnvTag` tinyint(3) unsigned NOT NULL,\n" +
                    "  `songLangTag` tinyint(3) unsigned NOT NULL,\n" +
                    "  PRIMARY KEY (`songListId`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            result = stmt.executeUpdate(sql);
            if (result == -1){
                System.out.print("create yueban_songlisttag fail");
            }

            sql = "CREATE TABLE `yueban_user` (\n" +
                    "  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `userPraisedCount` int(10) unsigned DEFAULT '0',\n" +
                    "  `userCriticizedCount` int(10) unsigned DEFAULT '0',\n" +
                    "  PRIMARY KEY (`userId`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            result = stmt.executeUpdate(sql);
            if (result == -1){
                System.out.print("create yueban_user fail");
            }

        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //conn.close();
            DBPool.closeConnection(conn);
        }
    }
}
