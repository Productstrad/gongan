package common;

import java.sql.SQLException;





public class DBFactory {	
	
	public static DBObject getDBObject(String proxoolName) throws SQLException, ClassNotFoundException {			
		return new DBObject(proxoolName);
	}	
	/**
	 * main函数本地连接数据库前调用，配置连接才能访问数据库，上线前可注释掉该函数
	 * 
	 * @author mengdz
	 * 2014年7月17日
	 */
	public void configMainTestConn() {
		try {
			org.logicalcobwebs.proxool.configuration.JAXPConfigurator.configure(ClassLoader.getSystemResource("")
					.getPath() + "/config/proxool.xml", false);
			Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
