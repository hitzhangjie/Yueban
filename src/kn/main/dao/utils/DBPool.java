package kn.main.dao.utils;

/**
 * Created by evannzhang on 2016/8/13.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.beans.PropertyVetoException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBPool{
    private static DBPool dbPool;
    private static ComboPooledDataSource dataSource;
    public final static String LOCKDB = "MUTEXDB";
    static {
        dbPool = new DBPool();
    }

    public DBPool(){
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setUser( "root");
            dataSource.setPassword( "mm4477MMT");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/yuebandb?"
                    + "autoReconnect=true&useUnicode=true&characterEncoding=UTF8");
            dataSource.setDriverClass( "com.mysql.jdbc.Driver ");
            dataSource.setInitialPoolSize(7);
            dataSource.setMinPoolSize(1);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);
        } catch (PropertyVetoException e){
            System.out.print("new fail");
            throw new RuntimeException(e);
        }
    }

    public final static DBPool getInstance(){
        return dbPool;
    }

    public final Connection getConnection(){
        try {
            synchronized (LOCKDB) {
                return dataSource.getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("无法从数据源获取连接 ",e);
        }
    }

    // 关闭链接
    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
}

//try {
//    con = DBPool.getInstance().getConnection();
//}catch (Exception   e){
//
//}finally{
//    if(con != null)
//        con.close();
//}
