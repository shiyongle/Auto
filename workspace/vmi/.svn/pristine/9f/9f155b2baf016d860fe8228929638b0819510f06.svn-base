package Com.Base.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

public class OracleHelper {
	private HashMap<String,Connection> conncls=new HashMap<>();
	public OracleHelper() throws DJException
	{
//		DriverManager.setLoginTimeout(60*30);
//		conncls.put("EASBaseConn", CreateConn());
	}
	public Connection GetBaseConn() throws SQLException, DJException
	{
		if (conncls.get("EASBaseConn").isClosed())
		{
			conncls.put("EASBaseConn",  CreateConn());
		}
		return conncls.get("EASBaseConn");
	}

	public Connection GetConn(HttpServletRequest request) throws DJException, SQLException {
		String sessionid = request.getSession().getId();
		if(conncls.containsKey(sessionid))
		{
			if (conncls.get(sessionid).isClosed())
			{
				conncls.put(sessionid, CreateConn());
			}
			return conncls.get(sessionid);
		}
		else
		{
			Connection conn=CreateConn();
			conncls.put(sessionid, conn);
			return conn;
		}
		
	}
	public void Destroyed() throws DJException
	{
		Iterator iterator = conncls.keySet().iterator();
		try {
			while(iterator.hasNext()) {
				conncls.get(iterator.next()).close();
				System.out.print("关闭全部oracle连接");
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DJException(e.getMessage());
		}
	}
	public void CloseConn(String sessionid) throws DJException
	{
		if(conncls.containsKey(sessionid))
		{
			try {
				conncls.get(sessionid).close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new DJException(e.getMessage());
			}
			conncls.remove(sessionid);
		}
	}
	public void CloseConn(HttpServletRequest request) throws DJException
	{
		String sessionid = request.getSession().getId();
		try {
			conncls.get(sessionid).close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DJException(e.getMessage());
		}
		conncls.remove(sessionid);
	}
	public Connection GetConn(String sessionid) throws DJException {
		if(conncls.containsKey(sessionid))
		{
			return conncls.get(sessionid);
		}
		else
		{
			Connection conn=CreateConn();
			conncls.put(sessionid, conn);
			return conn;
		}
		
	}
	private Connection CreateConn() throws DJException
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.198:1521:orcl", "dbo_update", "dbo_update");
//		    Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@192.168.2.105:1521:easdb", "dbo_djeas", "dbo_djeas");
			conn.setAutoCommit(false);
		
			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new DJException(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DJException(e.getMessage());
		}
		
	}

}
