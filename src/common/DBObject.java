package common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;



import com.sun.rowset.CachedRowSetImpl;

public class DBObject {

	private static Logger log = Logger.getLogger(DBObject.class);

	protected Connection conn = null;
	protected Statement stmt = null;
	protected PreparedStatement pstmt = null;

	public DBObject(String dbLink) throws SQLException {
		conn = getInitConnection(dbLink);
		stmt = conn.createStatement();
	}

	private Connection getInitConnection(String dbLink) {
		Connection conn = null;		
		try {			
//			dbLink="jdbc:mysql://sqld.duapp.com:4050/HJPgdUrVstGRfKbQbRVT?user=cztCWE9APisyvKIaF0b4qWwZ&password=cwLEG8hmDxYGVFCUbXNpL0PuO5lLkz1v";
			dbLink="jdbc:mysql://192.168.33.59:3306/pss?user=mengdz&password=mengdz";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbLink);
		} catch (Exception sqle) {
			log.error("get db connection error : " + sqle);
		}
		return conn;
	}

	public Connection getConnection() {
		return conn;
	}

	public PreparedStatement getPreparedStatement(String sql)
			throws SQLException {
		pstmt = conn.prepareStatement(sql);
		return pstmt;
	}

	// public Statement getStatement() throws SQLException {
	// return conn.createStatement();
	// }

	// private Statement getStatement() throws SQLException {
	// if(stmt==null){
	// stmt=conn.createStatement();
	// }
	// return stmt;
	// }

	public void beginTransition() throws SQLException {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException sqle) {
			log.error("begin transition error: " + sqle);
			throw sqle;
		}
	}

	public void commit() throws SQLException {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException sqle) {
			log.error("commit error: " + sqle);
			throw sqle;
		}
	}

	public void rollback() throws SQLException {
		try {
			conn.rollback();
			conn.setAutoCommit(true);
		} catch (SQLException sqle) {
			log.error("rollback error: " + sqle);
			throw sqle;
		}
	}

	/**
	 * 未关闭ResultSet
	 * 
	 * @throws SQLException
	 * @author mengdz 2014年7月2日
	 */
	public void close() throws SQLException {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException sqle) {
			log.error("close error: " + sqle);
			throw sqle;
		}
	}

	/**
	 * 同时关闭ResultSet,stmt,pstmt,conn
	 * 
	 * @param rs
	 * @throws SQLException
	 * @author mengdz 2014年7月2日
	 */
	public void close(ResultSet rs) throws SQLException {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			log.error("关闭ResultSet出错", e);
			throw e;
		}
		close();
	}

	public RowSet query(SqlBuilder sql) throws SQLException {
		CachedRowSet crset = new CachedRowSetImpl();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql.setParams());
			crset.populate(rs);
			return crset;
		} catch (SQLException sqle) {
			log.error("executeQuery error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
	}

	public <T> List<T> query(SqlBuilder sql,Class<T> claz) throws Exception {
		return VOKit.rs2BeanList(query(sql), claz);
	}
	
	public Long getCountL(SqlBuilder sql) throws SQLException {		
		ResultSet rs = null;
		long count=0L;
		try {
			rs = stmt.executeQuery(sql.setParams());
			while (rs.next()) {
				count=rs.getLong(1);				
			}			
		} catch (SQLException sqle) {
			log.error("executeQuery error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
		return count;
	}
	
	public Integer getCount(SqlBuilder sql) throws SQLException {	
		return Integer.valueOf(getCountL(sql).toString());
	}
	
	public int update(SqlBuilder sql) throws SQLException {
		try {
			return stmt.executeUpdate(sql.setParams());
		} catch (SQLException sqle) {
			log.error("executeUpdate error: " + sqle);
			throw sqle;
		} finally {
			close();
		}
	}

	/**
	 * 多条update、delete语句用;隔开
	 * 
	 * @param sqls
	 * @throws SQLException
	 * @author mengdz 2014年7月17日
	 */
	public void updateBatch(SqlBuilder sqls) throws SQLException {
		try {
			String[] sqlsStrings = sqls.setParams().split(";");
			for (String sql : sqlsStrings) {
				stmt.executeUpdate(sql);
			}
		} catch (SQLException sqle) {
			log.error("executeUpdate error: " + sqle);
			throw sqle;
		} finally {
			close();
		}
	}

	public Long insertL(SqlBuilder sql) throws SQLException {
		long result = 0;
		ResultSet rs = null;
		try {
			stmt.execute(sql.setParams());
			String sqlId = "select last_insert_id()";
			rs = stmt.executeQuery(sqlId);
			if (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException sqle) {
			log.error("executeInsert error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
		return result;
	}

	public int insert(SqlBuilder sql) throws SQLException {
		return Integer.valueOf(insertL(sql).toString());
	}

	/**
	 * 多条insert语句用;隔开
	 * 
	 * @param sqls
	 * @throws SQLException
	 * @author mengdz 2014年7月17日
	 */
	public void insertBatch(SqlBuilder sqls) throws SQLException {
		try {
			String[] sqlsStrings = sqls.setParams().split(";");
			for (String sql : sqlsStrings) {
				stmt.execute(sql);
			}
		} catch (SQLException sqle) {
			log.error("executeInsert error: " + sqle);
			throw sqle;
		} finally {
			close();
		}
	}

	/**
	 * 需详细控制pstmt构造、传参过程时，使用本方法执行准备好的pstmt
	 * 
	 * @return
	 * @throws SQLException
	 * @author mengdz 2014年7月7日
	 */
	public RowSet pstmtQuery() throws SQLException {
		CachedRowSet crset = new CachedRowSetImpl();
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();
			crset.populate(rs);
			return crset;
		} catch (SQLException sqle) {
			log.error("executePstmtQuery error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
	}

	/**
	 * 无需细控制pstmt构造、传参过程时，使用本方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @author mengdz 2014年7月7日
	 */
	public RowSet pstmtQuery(SqlBuilder sql) throws SQLException {
		CachedRowSet crset = new CachedRowSetImpl();
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			sql.setParams(pstmt);
			rs = pstmt.executeQuery();
			crset.populate(rs);
			return crset;
		} catch (SQLException sqle) {
			log.error("executePstmtQuery error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
	}

	/**
	 * 需详细控制pstmt构造、传参过程时，使用本方法执行准备好的pstmt
	 * 
	 * @return
	 * @throws SQLException
	 * @author mengdz 2014年7月7日
	 */
	public int pstmtUpdate() throws SQLException {
		try {
			return pstmt.executeUpdate();
		} catch (SQLException sqle) {
			log.error("executePstmtUpdate error: " + sqle);
			throw sqle;
		} finally {
			close();
		}
	}

	/**
	 * 无需细控制pstmt构造、传参过程时，使用本方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @author mengdz 2014年7月7日
	 */
	public int pstmtUpdate(SqlBuilder sql) throws SQLException {
		try {
			pstmt = conn.prepareStatement(sql.toString());
			sql.setParams(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException sqle) {
			log.error("executePstmtUpdate error: " + sqle);
			throw sqle;
		} finally {
			close();
		}
	}

	/**
	 * 需详细控制pstmt构造、传参过程时，使用本方法执行准备好的pstmt
	 * 
	 * @return
	 * @throws SQLException
	 * @author mengdz 2014年7月7日
	 */
	public int pstmtInsert() throws SQLException {
		int result = 0;
		ResultSet rs = null;
		try {
			pstmt.execute();

			String sqlId = "select last_insert_id()";
			rs = pstmt.executeQuery(sqlId);
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			log.error("executePstmtInsert error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
		return result;
	}

	/**
	 * 无需细控制pstmt构造、传参过程时，使用本方法
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @author mengdz 2014年7月17日
	 */
	public int pstmtInsert(SqlBuilder sql) throws SQLException {
		int result = 0;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.toString(),
					PreparedStatement.RETURN_GENERATED_KEYS);
			sql.setParams(pstmt);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			log.error("executePstmtInsert error: " + sqle);
			throw sqle;
		} finally {
			close(rs);
		}
		return result;
	}
	/**
	 * 获得数据库、表信息
	 * @return
	 * @throws SQLException
	 * @author mengdz
	 * 2014年7月18日
	 */
	public DatabaseMetaData getTablesInfo() throws SQLException {		
		try {			
			return conn.getMetaData();
		} catch (SQLException sqle) {
			log.error("executeQuery error: " + sqle);			
		} finally {
			close();
		}
		return null;
	}
}
