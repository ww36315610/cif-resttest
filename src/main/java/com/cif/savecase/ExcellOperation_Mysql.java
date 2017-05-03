package com.cif.savecase;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cif.utils.file.ConfigTools;
import com.cif.utils.file.ExcellOperation;
import com.cif.utils.mysql.MysqlDao;
import com.cif.utils.mysql.MysqlDaoImp;
import com.cif.utils.mysql.MysqlOperation;
import com.mysql.jdbc.Connection;

/**
 * 存储excell数据到mysql
 * 
 * @author WangJian
 *
 */
public class ExcellOperation_Mysql extends ExcellOperation implements Runnable {
	private static String excellPath = "C:\\Users\\cnbjpuhui-5051a\\Desktop\\case2.xls";
	// private static String excellSheet = "sqlcase1";
	private static String excellSheet = "resfull";
	private static List<Map<String, Object>> list;
	private static MysqlOperation mcbp;
	private static Connection conn;
	private static MysqlDao md;
	static String driver;
	static String url;
	static String user;
	static String pass;

	static {
		list = getExcellData(excellPath, excellSheet);
		// driver =
		// ConfigTools.config.getString("mysql_wj_test_utcs.jdbc.dbDriver");
		// url = ConfigTools.config.getString("mysql_wj_test_utcs.jdbc.dbUrl");
		// user =
		// ConfigTools.config.getString("mysql_wj_test_utcs.jdbc.username");
		// pass =
		// ConfigTools.config.getString("mysql_wj_test_utcs.jdbc.password");
		driver = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbDriver");
		url = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbUrl");
		user = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.username");
		pass = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.password");

		mcbp = MysqlOperation.getInstance(driver, url, user, pass);
		conn = mcbp.getConnection();
		md = new MysqlDaoImp();
	}

	public void run() {
		this.save(excellPath, excellSheet);
		System.out.println(">>>>>>>>>>>>" + Thread.currentThread().getName());
	}

	public static void main(String[] args) {
		ExcellOperation_Mysql eds = new ExcellOperation_Mysql();
		eds.save(excellPath, excellSheet);
	}

	@Override
	public void save(String excellPath, String excellSheet) {
		int i = 1;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String zuhe = "";
			Map<String, Object> map = (Map<String, Object>) iter.next();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue().toString();
				String a = value;
				zuhe = zuhe + ",\"" + a + "\"";
			}
			// String sql = "INSERT INTO utcs_case VALUES(" + i + "," + i + zuhe
			// + ",now()" + ");";
			String sql = "INSERT INTO resfull_case VALUES(" + i + zuhe + ");";
			md.insert(sql, conn);
			System.out.println(sql);
			i++;
		}
		System.out.println("执行插入sql次数：》》》》》》》》" + i);
	}
}
