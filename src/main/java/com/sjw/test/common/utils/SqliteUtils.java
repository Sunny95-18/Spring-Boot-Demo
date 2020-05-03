package com.sjw.test.common.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;


@Slf4j
public class SqliteUtils {
	private static final String DB_FILE_PATH= "E:\\dn\\ccee.db";
	//private static final String DB_FILE_PATH= "d:\\ccee.db";
	//" CreatedTime datetime NOT NULL default (datetime(\'now\', \'localtime\')) "+
	private static final String TABLE_LOG_SQL = "CREATE TABLE logs "+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            " TYPE   INT NOT NULL, "+
            " CONTENT VARCHAR (2000), "+
            " CreatedTime datetime NOT NULL "+
            ");";

    private static final String TABLE_WHITELIST_SQL = "CREATE TABLE whitelist "+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            " IP VARCHAR (50) "+
            ");";

    private static final String TABLE_SYMM_SQL = "CREATE TABLE symm "+
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
            " SYMMINDEX INT NOT NULL, "+
            " SYMMINDEXLEN INT NOT NULL, " +
            " SYMMINDEXSTATUS INT NOT NULL " +
            ");";

	private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String dbFilePath;
    
    public SqliteUtils() throws ClassNotFoundException, SQLException {
    	this.dbFilePath = DB_FILE_PATH;
    	File file = new File(this.dbFilePath);
    	if (!file.exists()) {
    		log.info("===================dbfilePath:" + this.dbFilePath);
    		connection = getConnection(dbFilePath);
    		executeUpdate("drop table if exists logs;");
    		executeUpdate(TABLE_LOG_SQL);
            executeUpdate("drop table if exists whitelist;");
            executeUpdate(TABLE_WHITELIST_SQL);
            executeUpdate("drop table if exists symm;");
            executeUpdate(TABLE_SYMM_SQL);
    	}else{
    		//logger.info("====================getconnection");
    		connection = getConnection(dbFilePath);
    	}
    }
    /**
     * 构造函数
     * @param dbFilePath sqlite db 文件路径
     * @return 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public SqliteUtils(String dbFilePath) throws ClassNotFoundException, SQLException {
        this.dbFilePath = dbFilePath;
        connection = getConnection(dbFilePath);
    }
    
    /**
     * 获取数据库连接
     * @param dbFilePath db文件路径
     * @return 数据库连接
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        return conn;
    }
    
    /**
     * 执行sql查询
     * @param sql sql select 语句
     * @param rse 结果集处理类对象
     * @return 查询结果
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public <T> T executeQuery(String sql, ResultSetExtractor<T> rse) throws SQLException, ClassNotFoundException {
        try {
            resultSet = getStatement().executeQuery(sql);
            T rs = rse.extractData(resultSet);
            return rs;
        } finally {
            destroyed();
        }
    }
    
    /**
     * 执行select查询，返回结果列表
     * 
     * @param sql sql select 语句
     * @param rm 结果集的行数据处理类对象
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public <T> List<T> executeQuery(String sql, RowMapper<T> rm) throws SQLException, ClassNotFoundException {
        List<T> rsList = new ArrayList<T>();
        try {
            resultSet = getStatement().executeQuery(sql);
            while (resultSet.next()) {
                rsList.add(rm.mapRow(resultSet, resultSet.getRow()));
            }
        } finally {
            destroyed();
        }
        return rsList;
    }
    
    /**
     * 执行数据库更新sql语句
     * @param sql
     * @return 更新行数
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        try {
            int c = getStatement().executeUpdate(sql);
            return c;
        } finally {
            destroyed();
        }
        
    }

    /**
     * 执行多个sql更新语句
     * @param sqls
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void executeUpdate(String...sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }
    
    /**
     * 执行数据库更新 sql List
     * @param sqls sql列表
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (null == connection) connection = getConnection(dbFilePath);
        return connection;
    }
    
    private Statement getStatement() throws SQLException, ClassNotFoundException {
        if (null == statement) statement = getConnection().createStatement();
        return statement;
    }
    
    /**
     * 数据库资源关闭和释放
     */
    public void destroyed() {
        try {
            if (null != connection) {
                connection.close();
                connection = null;
            }
            
            if (null != statement) {
                statement.close();
                statement = null;
            }
            
            if (null != resultSet) {
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e) {
            log.error("Sqlite数据库关闭时异常", e);
        }
    }

    public static void main(String[] args) throws Exception{
           SqliteUtils sqliteUtils=new SqliteUtils();
    }
}
