package com.asiainfo.ereport.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asiainfo.ereport.meta.CustomReportBindField;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.WithinGroupExpression;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;

public class FunctionExpressionParse implements SelectVisitor, ExpressionVisitor, SelectItemVisitor {
	private boolean existserror = false;
	private String mess;
	private Map<String, String> colkeyAndNameMap = new HashMap<String, String>();
	private String sql;
	private Integer parseType = 1;
	public static final Integer PARASETYPE_TAIL = 1;
	public static final Integer PARASETYPE_BODY = 2;
	/**
	 * 绑定字段列表
	 */
	private List<CustomReportBindField> bindFields;
	Map<String, ArrayList<String>> functionAndExpMap = new HashMap<String, ArrayList<String>>();

	public FunctionExpressionParse(List<CustomReportBindField> fields, String sql, Integer parseType) {
		for (CustomReportBindField customReportBindField : fields) {
			colkeyAndNameMap.put(customReportBindField.getColIndexName().toUpperCase(),
					customReportBindField.getAlias().toUpperCase());
		}
		this.sql = sql;
		this.bindFields = fields;
		this.parseType = parseType;
	}

	public String parse() throws Exception {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		String returnExp = "";
		try {
			Statement stamt = parserManager.parse(new StringReader(sql));
			Select plainSelect = (Select) stamt;
			plainSelect.getSelectBody().accept(this);
			if (existserror) {
				throw new Exception(mess);
			}
			String newSql = plainSelect.toString();
			stamt = parserManager.parse(new StringReader(newSql));
			PlainSelect plainSelect1 = (PlainSelect) ((Select) stamt).getSelectBody();
			List<SelectItem> selectitems = plainSelect1.getSelectItems();
			for (int i = 0; i < selectitems.size(); i++) {
				if (!(((SelectExpressionItem) selectitems.get(i)).getExpression() instanceof Function)) {
					throw new Exception("表达式不正确，请填写正确的函数表达式。");
				}
				returnExp = returnExp + ((SelectExpressionItem) selectitems.get(i)).getExpression();
			}
			Set<String> set = functionAndExpMap.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String function = iterator.next();
				List<String> exps = functionAndExpMap.get(function);
				for (String colname : exps) {
					for (CustomReportBindField reportBindField : bindFields) {
						if (reportBindField.getFieldName().equalsIgnoreCase(colname)) {
							if (function.toUpperCase().startsWith("SUM") && !isDigital(reportBindField)) {
								throw new Exception("非数字列不能做合计(sum)操作。");
							}
							if (function.toUpperCase().startsWith("AVG") && !isDigital(reportBindField)) {
								throw new Exception("非数字列不能平均值(avg)操作。");
							}
						}
					}
				}
			}

			return returnExp;
		} catch (Exception ex) {
			ex.printStackTrace();
			if(ex instanceof JSQLParserException){
				throw new Exception("请填写正确的表达式。");
			}
			throw ex;
		}
	}

	private boolean isDigital(CustomReportBindField checkField) {
		if (checkField.getDataType() != null && (checkField.getDataType().equals(java.sql.Types.INTEGER)
				|| checkField.getDataType().equals(java.sql.Types.DOUBLE)
				|| checkField.getDataType().equals(java.sql.Types.FLOAT)
				|| checkField.getDataType().equals(java.sql.Types.DECIMAL)
				|| checkField.getDataType().equals(java.sql.Types.BIGINT)
				|| checkField.getDataType().equals(java.sql.Types.NUMERIC))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void visit(NullValue nullValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Function function) {
		ExpressionList parameters = function.getParameters();
		if (parameters == null) {// 分组函数必须有参数，如果没有则认为是错误，但是实际情况中函数可能没有参数。
			existserror = true;
			this.mess = "函数中缺少必要的参数。";
		} else {
			List<Expression> expressions = parameters.getExpressions();
			ArrayList<String> exs = new ArrayList<String>();
			for (int i = 0; i < expressions.size(); i++) {
				if (expressions.get(i) instanceof BinaryExpression) {
					// 如果是简单的加法直接验证
					Expression leftexp = ((BinaryExpression) expressions.get(i)).getLeftExpression();
					Expression rightexp = ((BinaryExpression) expressions.get(i)).getRightExpression();
					isComputeAble(leftexp.toString(), rightexp.toString());
					expressions.get(i).accept(this);

				} else if (expressions.get(i) instanceof Function) {
					existserror = true;
					this.mess = "系统不支持嵌套函数。";
				} else {
					expressions.get(i).accept(this);
				}
				exs.add(expressions.get(i).toString());
			}
			this.functionAndExpMap.put(function.toString(), exs);
		}

	}

	private boolean isComputeAble(String left, String expright) {
		for (CustomReportBindField reportBindField : this.bindFields) {
			if (reportBindField.getColIndexName().equalsIgnoreCase(left) && !this.isDigital(reportBindField)) {
				this.existserror = true;
				this.mess = "列" + left + "不是数字列，不能参与表达式。";
				return false;
			}
			if (reportBindField.getColIndexName().equalsIgnoreCase(expright) && !this.isDigital(reportBindField)) {
				this.existserror = true;
				this.mess = "列" + expright + "不是数字列，不能参与表达式。";
				return false;
			}
		}
		return true;
	}

	@Override
	public void visit(SignedExpression signedExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JdbcNamedParameter jdbcNamedParameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DoubleValue doubleValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LongValue longValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(HexValue hexValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DateValue dateValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimeValue timeValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimestampValue timestampValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Parenthesis parenthesis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringValue stringValue) {

	}

	@Override
	public void visit(Addition addition) {
		addition.getLeftExpression().accept(this);
		addition.getRightExpression().accept(this);
	}

	@Override
	public void visit(Division division) {
		division.getLeftExpression().accept(this);
		division.getRightExpression().accept(this);
	}

	@Override
	public void visit(Multiplication multiplication) {
		multiplication.getLeftExpression().accept(this);
		multiplication.getRightExpression().accept(this);
	}

	@Override
	public void visit(Subtraction subtraction) {
		subtraction.getLeftExpression().accept(this);
		subtraction.getRightExpression().accept(this);
	}

	@Override
	public void visit(AndExpression andExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OrExpression orExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Between between) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EqualsTo equalsTo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(GreaterThan greaterThan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(InExpression inExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IsNullExpression isNullExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LikeExpression likeExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MinorThan minorThan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Column tableColumn) {
		String colindex = tableColumn.getColumnName();
		String colname = colkeyAndNameMap.get(colindex);
		if (colname == null) {
			existserror = true;
			this.mess = "函数中填写的列索引'" + colindex + "'没有绑定数据。";
		}
		tableColumn.setColumnName(colname);
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CaseExpression caseExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(WhenClause whenClause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Concat concat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Matches matches) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpression cast) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Modulo modulo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AnalyticExpression aexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(WithinGroupExpression wgexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExtractExpression eexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntervalExpression iexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OracleHierarchicalExpression oexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RegExpMatchOperator rexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JsonExpression jsonExpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RegExpMySQLOperator regExpMySQLOperator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UserVariable var) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NumericBind bind) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(KeepExpression aexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MySQLGroupConcat groupConcat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RowConstructor rowConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OracleHint hint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlainSelect plainSelect) {
		try {
			List<SelectItem> selectitems = plainSelect.getSelectItems();
			for (int i = 0; i < selectitems.size(); i++) {
				SelectItem selectitem = selectitems.get(i);
				selectitem.accept(this);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void visit(SetOperationList setOpList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(WithItem withItem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AllColumns allColumns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AllTableColumns allTableColumns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(this);
	}

}
