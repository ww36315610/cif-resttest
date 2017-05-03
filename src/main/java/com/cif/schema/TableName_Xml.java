package com.cif.schema;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.puhui.nbsp.cif.Column;
import com.puhui.nbsp.cif.ColumnFamily;
import com.puhui.nbsp.cif.ParsePluginXml;

public class TableName_Xml {

	private static List<ColumnFamily> getColumnFamily(String filePath) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(filePath));
		Element node = document.getRootElement();
		List<ColumnFamily> familyList = ParsePluginXml.listNodes(node);
		return familyList;
	}

	public static List<String> getColumn(String filePath) throws DocumentException {
		List<String> hbaseNaeList = new ArrayList();
		for (ColumnFamily cf : getColumnFamily(filePath)) {
			String befor = cf.getCfName();
			for (Column column : cf.getCloumns()) {
				hbaseNaeList.add(befor + "." + column.getHbaseName());
			}
		}
		return hbaseNaeList;
	}

	public static void main(String[] args) {
		String filePath = TableName_Xml.class.getClassLoader().getResource("nirvana-entity.xml").getPath();
		try {
			System.out.println(getColumn(filePath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
