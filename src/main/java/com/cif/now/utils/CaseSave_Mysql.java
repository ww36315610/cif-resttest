package com.cif.now.utils;

import java.util.List;
import java.util.Map;

import com.cif.now.utils.resource.ConfigTools;
import com.cif.now.utils.resource.ExcellTools;
import com.cif.now.utils.resource.MysqlTools;
import com.google.common.collect.Lists;
import com.mysql.jdbc.Statement;

/**
 * 存储excell数据到mysql
 * 
 * @author WangJian
 *此类事存case到mysql的功能
 *如果运行到服务器或者容器上，需要把所有配置都写入到配置文件，或者
 */
public class CaseSave_Mysql extends ExcellTools {

	private static final String EXCELLPATH = "C:\\Users\\cnbjpuhui-5051a\\Desktop\\case2.xls";
	private static final String EXCELLSHELL = "QQ";
	private static final String TABLENAME = "tee";
	int i = 1;
	MysqlTools tools;
	private String combination;

	public CaseSave_Mysql() {
	}
	
	public CaseSave_Mysql(String driver, String url, String user, String pass) {
		tools = MysqlTools.getInstance(driver, url, user, pass);
	}

	public static void main(String[] args) {
		String driver = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbDriver");
		String url = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.dbUrl");
		String user = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.username");
		String pass = ConfigTools.config.getString("mysql_wj_test_resfull.jdbc.password");

		CaseSave_Mysql csm = new CaseSave_Mysql(driver, url, user, pass);
		// 1.创建mysql链接
//		Connection conn = csm.tools.getConnection();
		// 2.read excell --l
		List<Map<String, Object>> listRead = csm.getExcellData(EXCELLPATH, EXCELLSHELL);
		// 3. 拼装sq
		List<String> sqls = csm.buildSql(listRead);
		// 4.插入mysql
		csm.save(sqls);
		// 5.关闭资源链接
		csm.tools.closeConnection(csm.tools.getRs(), (Statement) csm.tools.pre, csm.tools.getConnection());
	}
	

	@Override
	public void save(List<String> list) {
		this.tools.batchInsert(list);
	}

	// readExcell 得到返回值,拼装sql保存到List<String>中
	public List<String> buildSql(List<Map<String, Object>> listRead) {
		List<String> listSql = Lists.newArrayList();
		listRead.forEach(l -> {
			String sql = "";
			StringBuilder combination = new StringBuilder();
			l.forEach((k, v) -> {
				combination.append(",'");
				combination.append(v.toString());
				combination.append("'");
			});
			sql = "INSERT INTO " + TABLENAME + " VALUES(" + i + combination + ");";
			listSql.add(sql);
			System.out.println(sql);
			i++;
		});
		return listSql;
	}
}
