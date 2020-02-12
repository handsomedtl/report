package com.asiainfo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class SQLTest extends BaseTest{
	@Test
	public void sqltest() throws JSQLParserException {
		File file = new File("E:/reportsql.txt");
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		InputStreamReader reader = new InputStreamReader(stream);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		String line;
		try {
			while (null != (line = br.readLine())) {
				sb.append(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		Statement stmt = CCJSqlParserUtil.parse(sb.toString().trim());  
	    Select select = (Select) stmt;  
	    SelectBody selectBody=select.getSelectBody();
	    if(selectBody instanceof PlainSelect){
	    	String str=selectBody.toString();
	    	str.substring(str.indexOf(":"),str.indexOf(" "));
	    	List<SelectItem> selectItems=((PlainSelect) selectBody).getSelectItems();
	    	for(SelectItem selectItem:selectItems){
	    		if(selectItem instanceof AllColumns){
	    		}
	    		if(selectItem instanceof SelectExpressionItem){
	    			((SelectExpressionItem)selectItem).getAlias();
	    		}
	    	}
	    	
	    }
		}catch(JSQLParserException e){
			System.out.print(e.getStackTrace());
		}
	}
}
