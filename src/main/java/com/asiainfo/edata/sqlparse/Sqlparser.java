package com.asiainfo.edata.sqlparse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asiainfo.edata.EStaticConstant;
import com.asiainfo.edata.dataset.ParseResult;
import com.asiainfo.edata.exception.ErrorDatasetDefineException;
import com.asiainfo.edata.exception.ParamNotFoundException;
import com.asiainfo.edata.meta.ConditionMeta;
import com.asiainfo.edata.meta.EDatasetMeta;
import com.asiainfo.edata.meta.FieldMeta;

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
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import net.sf.jsqlparser.statement.select.WithItem;

class Sqlparser implements SelectVisitor, ExpressionVisitor, FromItemVisitor, ItemsListVisitor, SelectItemVisitor {

	/**
	 * sql解析
	 */
	public static final int SQL_ACTION_PARESE = 1;
	/**
	 * sql执行
	 */
	public static final int SQL_ACTION_EXECUTE = 2;

	public static final int LEFTANDRIGHT_LEFT = 2;
	public static final int LEFTANDRIGHT_RIGHT = 1;

	private static Logger logger = LoggerFactory.getLogger(Sqlparser.class);
	private boolean exisexception = false;
	private List<String> notfoundparams = new ArrayList<String>();
	private String errorMess;
	private Map<String, ConditionMeta> contionmetas;
	private List<String> multiselectFields = new ArrayList<String>();

	/**
	 * 用户查询条件
	 */
	private Map<String, Object> userParams = new HashMap<String, Object>();
	private EDatasetMeta datasetMeta;
	private int actionType;

	public Sqlparser(EDatasetMeta datasetMeta, Map<String, Object> userParams, int actionType) {
		this.datasetMeta = datasetMeta;
		this.userParams = userParams;
		this.actionType = actionType;
		this.contionmetas = this.datasetMeta.getConditionMetas();
		this.uppercaseMapKey(this.contionmetas);
	}

	public ParseResult parserSql() throws ErrorDatasetDefineException, ParamNotFoundException {
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		try {
			Statement stamt = parserManager.parse(new StringReader(this.datasetMeta.getContent()));
			Select plainSelect = (Select) stamt;
			plainSelect.getSelectBody().accept(this);
			if (plainSelect.getSelectBody() instanceof SetOperationList) {
				Statement stamt1 = parserManager.parse(new StringReader(
						((SetOperationList) plainSelect.getSelectBody()).getSelects().get(0).toString()));
				Select plainSelecti = (Select) stamt1;
				parseSelectColunm(((PlainSelect) plainSelecti.getSelectBody()).getSelectItems());
			} else if (plainSelect.getSelectBody() instanceof WithItem) {

			} else if (plainSelect.getSelectBody() instanceof PlainSelect) {
				parseSelectColunm(((PlainSelect) plainSelect.getSelectBody()).getSelectItems());
			}
			String newStr = plainSelect.toString();
			newStr = newStr.trim();
			if (newStr.startsWith("(")) {
				newStr = "select * from " + newStr + "";
			}
			if (exisexception) {
				if (!this.notfoundparams.isEmpty()) {
					String exceptionMess = "请选择";
					for (String paramname : notfoundparams) {
						exceptionMess = exceptionMess + paramname + ",";
					}
					exceptionMess = exceptionMess.substring(0, exceptionMess.length() - 1);
					exceptionMess = exceptionMess + "。";
					throw new ParamNotFoundException(exceptionMess, "");
				} else {
					throw new ErrorDatasetDefineException(this.errorMess);
				}

			}
			if (logger.isDebugEnabled()) {
				logger.debug("最终的sql:" + newStr);
			}
			ParseResult parseResult = new ParseResult();
			parseResult.setConditions(this.datasetMeta.getConditionMetaList());
			parseResult.setFinalsql(newStr);
			parseResult.setMultiselectFields(multiselectFields);
			return parseResult;
		} catch (JSQLParserException e) {
			e.printStackTrace();
			logger.error("error", e);
			throw new ErrorDatasetDefineException(e.getMessage());
		}
	}

	public void parseSelectColunm(List<SelectItem> selectitems) {
		for (SelectItem selectItem : selectitems) {
			if (selectItem instanceof SelectExpressionItem) {
				FieldMeta fieldMeta = new FieldMeta();
				SelectExpressionItem selectExpressionItem = ((SelectExpressionItem) selectItem);
				if (selectExpressionItem.getAlias() != null) {
					fieldMeta.setDatasetId(this.datasetMeta.getId());
					fieldMeta.setName(selectExpressionItem.getAlias().getName());
				} else {
					fieldMeta.setDatasetId(this.datasetMeta.getId());
					fieldMeta.setName(selectExpressionItem.getExpression().toString());
				}
			}
		}
	}

	/**
	 * 加法处理
	 */
	@Override
	public void visit(Addition addition) {
		Expression rigth = addition.getRightExpression();
		rigth.accept(this);
		Expression expression = addition.getLeftExpression();
		expression.accept(this);
	}

	@Override
	public void visit(AllComparisonExpression allComparisonExpression) {
		allComparisonExpression.getSubSelect().getSelectBody().accept(this);
	}

	@Override
	public void visit(AndExpression andExpression) {
		Expression rigth = andExpression.getRightExpression();
		if (Sqlparser.SQL_ACTION_PARESE == this.actionType) {
			rigth.accept(this);
			Expression expression = andExpression.getLeftExpression();
			expression.accept(this);
		} else {
			Expression expression = andExpression.getLeftExpression();
			handelExpression(rigth, andExpression, Sqlparser.LEFTANDRIGHT_RIGHT);
			handelExpression(expression, andExpression, Sqlparser.LEFTANDRIGHT_LEFT);
			if (rigth == null && expression == null) {
				andExpression = null;
			}
		}

	}

	private void handelExpression(Expression expression, BinaryExpression parentexp, int lefandright) {
		try {
			if (expression instanceof ExistsExpression) {
				handelJdbcNamedParameterExistsExpression((ExistsExpression) expression, parentexp, lefandright);
			} else if (expression instanceof InExpression) {
				handelJdbcNamedParameterInExpression((InExpression) expression, parentexp, lefandright);
			} else if (expression instanceof LikeExpression) {
				handelJdbcNamedParameter((LikeExpression) expression, parentexp, lefandright);
			} else if (expression instanceof EqualsTo) {
				handelJdbcNamedParameter((EqualsTo) expression, parentexp, lefandright);
			} else if (expression instanceof GreaterThan) {
				handelJdbcNamedParameter((GreaterThan) expression, parentexp, lefandright);
			} else if (expression instanceof GreaterThanEquals) {
				handelJdbcNamedParameter((GreaterThanEquals) expression, parentexp, lefandright);
			} else if (expression instanceof NotEqualsTo) {
				handelJdbcNamedParameter((NotEqualsTo) expression, parentexp, lefandright);
			} else if (expression instanceof IsNullExpression) {
				// handelJdbcNamedParameter(logictype, (IsNullExpression)
				// expression, parentexp, lefandright);
				// handelJdbcNamedParameter(logictype, (IsNullExpression)
				// expression, parentexp, lefandright);

			} else if (expression instanceof Between) {
				handelJdbcNamedParamBetween((Between) expression, parentexp, lefandright);
			} else if (expression instanceof Parenthesis) {
				((Parenthesis) expression).getExpression().accept(this);
			} else {
				expression.accept(this);
			}
		} catch (ParamNotFoundException exception) {
			this.exisexception = true;
			if (!notfoundparams.contains(exception.getParamsname())) {
				notfoundparams.add(exception.getParamsname());
			}
			return;
		} catch (ErrorDatasetDefineException eex) {
			this.exisexception = true;
			this.setErrorMess(eex.getMessage());
		}
	}

	private void handelJdbcNamedParamBetween(Between between, BinaryExpression parentexp, int lefandright) {

		try {
			if (between.getBetweenExpressionEnd() instanceof JdbcNamedParameter
					&& between.getBetweenExpressionStart() instanceof JdbcNamedParameter) {
				boolean endparamIsExists = paramIsExists(between.getBetweenExpressionEnd());
				boolean startparamIsExists = paramIsExists(between.getBetweenExpressionEnd());
				if (!endparamIsExists && !startparamIsExists) {
					parentexp.setRightExpression(null);
				} else {
					between.getBetweenExpressionStart().accept(this);
					between.getBetweenExpressionEnd().accept(this);
				}

			} else {
				between.getBetweenExpressionStart().accept(this);
				between.getBetweenExpressionEnd().accept(this);
			}
		} catch (ParamNotFoundException exception) {
			this.exisexception = true;
			if (!notfoundparams.contains(exception.getParamsname())) {
				notfoundparams.add(exception.getParamsname());
			}
			return;
		} catch (ErrorDatasetDefineException eex) {
			this.exisexception = true;
			this.setErrorMess(eex.getMessage());
		}
	}

	// private void handelJdbcNamedParameter(int logictype, Expression
	// expression, BinaryExpression parentexp,
	// int lefandright) {
	// if (expression instanceof IsNullExpression) {
	// try {
	// Expression leftexpression = ((IsNullExpression)
	// expression).getLeftExpression();
	// if (leftexpression instanceof JdbcNamedParameter) {
	// boolean endparamIsExists = paramIsExists(expression);
	//
	// if (!endparamIsExists) {
	// // if (Sqlparser.LOGIC_ACTION_AND == logictype) {
	// if (lefandright == 1) {
	// parentexp.setRightExpression(null);
	// } else {
	// parentexp.setLeftExpression(null);
	// }
	//
	// // }
	// } else {
	// expression.accept(this);
	// }
	// } else {
	// expression.accept(this);
	// }
	//
	// } catch (ParamNotFoundException exception) {
	// this.exisexception = true;
	// notfoundparams.add(exception.getParamsname());
	// return;
	// }
	//
	// } else {
	// expression.accept(this);
	// }
	//
	// }

	private void handelJdbcNamedParameter(BinaryExpression expression, BinaryExpression parentexp, int lefandright) {
		try {
			if (expression.getRightExpression() instanceof JdbcNamedParameter
					|| expression.getRightExpression() instanceof Concat
					|| isSimpleFunction(expression.getRightExpression())
					|| isSimpleSubSelect(expression.getRightExpression())) {
				boolean paramIsExists = paramIsExists(expression.getRightExpression());
				if (!paramIsExists) {
					// if (Sqlparser.LOGIC_ACTION_AND == logictype) {

					if (lefandright == Sqlparser.LEFTANDRIGHT_RIGHT) {
						parentexp.setRightExpression(null);
					} else if (lefandright == Sqlparser.LEFTANDRIGHT_LEFT) {
						parentexp.setLeftExpression(null);
					} else {
						parentexp.setRightExpression(null);
					}

					// } else {
					// if (lefandright == Sqlparser.LEFTANDRIGHT_RIGHT) {
					// parentexp.setRightExpression(null);
					// } else if (lefandright == Sqlparser.LEFTANDRIGHT_LEFT) {
					// parentexp.setLeftExpression(null);
					// } else {
					// parentexp.setRightExpression(null);
					// }
					//
					// }
				}

			} else {
				expression.accept(this);
			}
		} catch (ParamNotFoundException ex) {
			this.exisexception = true;
			if (!notfoundparams.contains(ex.getParamsname())) {
				notfoundparams.add(ex.getParamsname());
			}

		} catch (ErrorDatasetDefineException eex) {
			this.exisexception = true;
			this.setErrorMess(eex.getMessage());
		}
	}

	/**
	 * 判断是否简单的数据转换函数
	 * 
	 * @param expression
	 * @return
	 */
	private boolean isSimpleFunction(Expression expression) {
		if (expression instanceof Function) {
			String functionname = ((Function) expression).getName();
			Expression firstexp = ((Function) expression).getParameters().getExpressions().get(0);
			if ((functionname.equalsIgnoreCase("TO_CHAR") || functionname.equalsIgnoreCase("TO_DATE")
					|| functionname.equalsIgnoreCase("TO_NUMBER")) && firstexp instanceof JdbcNamedParameter) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 判断是否只有一个查询条件且是JdbcNamedParameter
	 * 
	 * @param expression
	 * @return
	 */
	private boolean isSimpleSubSelect(Expression expression) {
		if (expression instanceof SubSelect) {
			SelectBody seletbody = ((SubSelect) expression).getSelectBody();
			if (seletbody instanceof PlainSelect) {
				Expression where = ((PlainSelect) seletbody).getWhere();
				if (where == null) {
					return false;
				}
				boolean ismultilogic = isMultilogicExpression(where);
				if (isContainJdbcNamedParameterExp(where) && !ismultilogic) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	private void handelJdbcNamedParameterInExpression(InExpression expression, BinaryExpression parentexp,
			int lefandright) {
		try {
			boolean ismultilogic = isMultilogicExpression(expression);
			if (ismultilogic) {
				expression.accept(this);
			} else {
				if (isContainJdbcNamedParameterExp(expression)) {

					if (!paramIsExists(expression)) {
						parentexp.setRightExpression(null);
					} else {
						expression.accept(this);
					}

				} else {
					expression.accept(this);
				}
			}
		} catch (ParamNotFoundException ex) {
			if (!notfoundparams.contains(ex.getParamsname())) {
				notfoundparams.add(ex.getParamsname());
			}
			this.exisexception = true;
		} catch (ErrorDatasetDefineException eex) {
			this.exisexception = true;
			this.setErrorMess(eex.getMessage());
		}
	}

	private void handelJdbcNamedParameterExistsExpression(ExistsExpression expression, BinaryExpression parentexp,
			int lefandright) throws ParamNotFoundException, ErrorDatasetDefineException {
		boolean ismultilogic = isMultilogicExpression(expression);
		if (ismultilogic) {
			expression.accept(this);
		} else {
			if (isContainJdbcNamedParameterExp(expression)) {
				if (!paramIsExists(expression)) {
					parentexp.setRightExpression(null);
				}
			} else {
				expression.accept(this);
			}
		}

	}

	private boolean isMultilogicExpression(Expression existsExpression) {
		String expressionstr = existsExpression.toString();
		String matchstr = "\\s+(and|And|ANd|AND|aNd|aND|anD|or|Or|OR|oR)\\s+";
		Pattern matchst = Pattern.compile(matchstr);// ^
		Matcher m = matchst.matcher(expressionstr);
		boolean ismultilogic = false;
		while (m.find()) {
			ismultilogic = true;
		}
		return ismultilogic;
	}

	private boolean isContainJdbcNamedParameterExp(Expression existsExpression) {
		boolean iscontain = false;
		String matchstr = "\\s*:\\w+\\s*";
		Pattern matchst = Pattern.compile(matchstr);// ^
		Matcher m = matchst.matcher(existsExpression.toString());
		while (m.find()) {
			iscontain = true;
		}
		return iscontain;
	}

	private boolean paramIsExists(Expression existsExpression)
			throws ParamNotFoundException, ErrorDatasetDefineException {
		String matchstr = "\\s*:\\w+\\s*";
		Pattern matchst = Pattern.compile(matchstr);// ^
		Matcher m = matchst.matcher(existsExpression.toString());
		boolean paramIsExists = true;
		while (m.find()) {
			String paramnameexp = m.group(0).trim();
			String paramname = paramnameexp.substring(paramnameexp.indexOf(":") + 1, paramnameexp.length());
			paramname = paramname.trim().toUpperCase();
			ConditionMeta condition = this.datasetMeta.getConditionMetas().get(paramname.trim().toUpperCase());
			if (condition == null) {
				throw new ErrorDatasetDefineException("数据集定义不正确，请检查。");
			}
			if (condition.getSrcType() == EStaticConstant.PARAM_SOURCE_TYPE_FORM && condition.getRequired()
					&& (!userParams.containsKey(paramname) || userParams.get(paramname) == null
							|| userParams.get(paramname).toString().equalsIgnoreCase(""))) {
				throw new ParamNotFoundException("缺少必要的参数" + condition.getName() + ",请检查", condition.getName());
			} else if (condition.getSrcType() == EStaticConstant.PARAM_SOURCE_TYPE_FORM && !condition.getRequired()
					&& (!userParams.containsKey(paramname) || userParams.get(paramname) == null
							|| userParams.get(paramname).toString().equalsIgnoreCase(""))) {
				paramIsExists = false;
			}
		}
		return paramIsExists;
	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		System.out.print(anyComparisonExpression);
	}

	@Override
	public void visit(Between between) {
		between.getBetweenExpressionEnd().accept(this);
		between.getBetweenExpressionStart().accept(this);
	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		bitwiseAnd.getLeftExpression().accept(this);
		bitwiseAnd.getRightExpression().accept(this);
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		System.out.print("visit(BitwiseOr):" + bitwiseOr);
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		System.out.print("visit(BitwiseXor):" + bitwiseXor);
	}

	@Override
	public void visit(CaseExpression caseExpression) {
		if (caseExpression.getElseExpression() != null) {
			Expression elseExpression = caseExpression.getElseExpression();
			elseExpression.accept(this);
		}
		List<Expression> whenClauses = caseExpression.getWhenClauses();
		for (int i = 0; i < whenClauses.size(); i++) {
			whenClauses.get(i).accept(this);
		}
	}

	@Override
	public void visit(Column tableColumn) {

	}

	@Override
	public void visit(Concat concat) {
		concat.getLeftExpression().accept(this);
		concat.getRightExpression().accept(this);

	}

	@Override
	public void visit(DateValue dateValue) {

	}

	@Override
	public void visit(Division division) {
		division.getLeftExpression().accept(this);
		division.getRightExpression().accept(this);
	}

	@Override
	public void visit(DoubleValue doubleValue) {

	}

	@Override
	public void visit(EqualsTo equalsTo) {
		Expression rightExpression = equalsTo.getRightExpression();
		Expression leftExpression = equalsTo.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);
	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		Expression rightExpression = existsExpression.getRightExpression();
		PlainSelect plainSelect = (PlainSelect) ((SubSelect) rightExpression).getSelectBody();
		plainSelect.accept(this);
	}

	@Override
	public void visit(Function function) {
		ExpressionList parameters = function.getParameters();
		List<Expression> expressions = parameters.getExpressions();
		for (int i = 0; i < expressions.size(); i++) {
			expressions.get(i).accept(this);
		}
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		Expression rightExpression = greaterThan.getRightExpression();
		Expression leftExpression = greaterThan.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		Expression rightExpression = greaterThanEquals.getRightExpression();
		Expression leftExpression = greaterThanEquals.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);

	}

	@Override
	public void visit(InExpression inExpression) {
		inExpression.getLeftExpression().accept(this);
		ItemsList itemlist = inExpression.getRightItemsList();
		if (itemlist instanceof ExpressionList) {
			List<Expression> exps = ((ExpressionList) itemlist).getExpressions();
			List<Expression> newExps = new ArrayList<Expression>();
			for (Expression expression : exps) {
				if (expression instanceof JdbcNamedParameter) {
					String paramname = expression.toString();
					paramname = paramname.substring(paramname.indexOf(":") + 1, paramname.length());
					paramname = paramname.trim().toUpperCase();
					Object paramvalue = this.userParams.get(paramname);
					if (paramvalue != null && !paramvalue.toString().equalsIgnoreCase("")) {
						String stringParamValue = paramvalue.toString();
						String[] stringParamValues = stringParamValue.split(EStaticConstant.MULTISELECTCONDSPLITSTR);
						if (stringParamValues.length > 1) {
							for (int i = 1; i < stringParamValues.length; i++) {
								JdbcNamedParameter newparams = new JdbcNamedParameter();
								newparams.setName(paramname + i);
								newExps.add(newparams);
								if (!multiselectFields.contains(paramname)) {
									multiselectFields.add(paramname);
								}

							}

						}
					}

				} else {
					expression.accept(this);
				}
			}
			exps.addAll(newExps);
		} else {
			itemlist.accept(this);
		}

	}

	@Override
	public void visit(IsNullExpression isNullExpression) {
		isNullExpression.getLeftExpression().accept(this);
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		System.out.print(jdbcParameter);
	}

	@Override
	public void visit(LikeExpression likeExpression) {
		Expression rightExpression = likeExpression.getRightExpression();
		Expression leftExpression = likeExpression.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);
	}

	@Override
	public void visit(LongValue longValue) {

	}

	@Override
	public void visit(Matches matches) {

	}

	@Override
	public void visit(MinorThan minorThan) {

		Expression rightExpression = minorThan.getRightExpression();
		Expression leftExpression = minorThan.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);

	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		Expression rightExpression = minorThanEquals.getRightExpression();
		Expression leftExpression = minorThanEquals.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);

	}

	@Override
	public void visit(Multiplication multiplication) {
		multiplication.getLeftExpression().accept(this);
		multiplication.getRightExpression().accept(this);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		Expression rightExpression = notEqualsTo.getRightExpression();
		Expression leftExpression = notEqualsTo.getLeftExpression();
		rightExpression.accept(this);
		leftExpression.accept(this);
	}

	@Override
	public void visit(NullValue nullValue) {
		System.out.println(nullValue);
	}

	@Override
	public void visit(OrExpression orExpression) {
		Expression rigth = orExpression.getRightExpression();
		if (Sqlparser.SQL_ACTION_PARESE == this.actionType) {
			rigth.accept(this);
			Expression expression = orExpression.getLeftExpression();
			expression.accept(this);
		} else {
			Expression expression = orExpression.getLeftExpression();
			handelExpression(rigth, orExpression, Sqlparser.LEFTANDRIGHT_RIGHT);
			handelExpression(expression, orExpression, Sqlparser.LEFTANDRIGHT_LEFT);
			if (orExpression.getLeftExpression() == null && orExpression.getRightExpression() == null) {
				orExpression = null;
			}
		}

	}

	@Override
	public void visit(Parenthesis parenthesis) {
		parenthesis.getExpression().accept(this);
	}

	@Override
	public void visit(StringValue stringValue) {
		System.out.println(stringValue);
	}

	@Override
	public void visit(SubSelect subSelect) {
		subSelect.getSelectBody().accept(this);
	}

	@Override
	public void visit(Subtraction subtraction) {
		subtraction.getLeftExpression().accept(this);
		subtraction.getRightExpression().accept(this);
	}

	@Override
	public void visit(TimestampValue timestampValue) {

	}

	@Override
	public void visit(TimeValue timeValue) {

	}

	@Override
	public void visit(WhenClause whenClause) {

		whenClause.getThenExpression().accept(this);
		whenClause.getWhenExpression().accept(this);
	}

	@Override
	public void visit(PlainSelect plainSelect) {
		try {
			if (plainSelect.getWhere() != null) {
				handelWherExp(plainSelect.getWhere(), plainSelect);
			}
			if (plainSelect.getOracleHierarchical() != null) {
				plainSelect.getOracleHierarchical().accept(this);
			}
			List<SelectItem> selectitems = plainSelect.getSelectItems();
			for (int i = 0; i < selectitems.size(); i++) {
				SelectItem selectitem = selectitems.get(i);
				if (selectitem instanceof AllColumns) {
					continue;
				}
				if (selectitem instanceof AllTableColumns) {
					continue;
				}
				SelectExpressionItem items = (SelectExpressionItem) selectitems.get(i);
				items.getExpression().accept(this);
			}
			FromItem fromItem = plainSelect.getFromItem();
			fromItem.accept(this);
			List<Join> joins = plainSelect.getJoins();
			if (joins != null) {// 对from后面出现的进行处理
				for (int i = 0; i < joins.size(); i++) {
					Join join = (Join) joins.get(i);
					join.getRightItem().accept(this);
					if (join.getOnExpression() != null) {
						join.getOnExpression().accept(this);
					}

				}
			}
		} catch (ParamNotFoundException ex) {
			this.exisexception = true;
			if (!notfoundparams.contains(ex.getParamsname())) {
				notfoundparams.add(ex.getParamsname());
			}
			return;
		} catch (ErrorDatasetDefineException eex) {
			this.exisexception = true;
			this.setErrorMess(eex.getMessage());
			return;
		}
	}

	private void handelWherExp(Expression expression, PlainSelect plainSelect)
			throws ParamNotFoundException, ErrorDatasetDefineException {
		if (Sqlparser.SQL_ACTION_PARESE == this.actionType) {
			plainSelect.getWhere().accept(this);
		} else {
			if (plainSelect.getWhere() instanceof LikeExpression) {
				handelJdbcNamedParameter((LikeExpression) plainSelect.getWhere(), plainSelect,
						Sqlparser.LEFTANDRIGHT_RIGHT);
			} else if (plainSelect.getWhere() instanceof EqualsTo) {
				handelJdbcNamedParameter((EqualsTo) plainSelect.getWhere(), plainSelect, Sqlparser.LEFTANDRIGHT_RIGHT);
			} else if (plainSelect.getWhere() instanceof GreaterThan) {
				handelJdbcNamedParameter((GreaterThan) plainSelect.getWhere(), plainSelect,
						Sqlparser.LEFTANDRIGHT_RIGHT);
			} else if (plainSelect.getWhere() instanceof GreaterThanEquals) {
				handelJdbcNamedParameter((GreaterThanEquals) plainSelect.getWhere(), plainSelect,
						Sqlparser.LEFTANDRIGHT_RIGHT);
			} else if (plainSelect.getWhere() instanceof NotEqualsTo) {
				handelJdbcNamedParameter((NotEqualsTo) plainSelect.getWhere(), plainSelect,
						Sqlparser.LEFTANDRIGHT_RIGHT);
			} else {
				plainSelect.getWhere().accept(this);
				if (plainSelect.getWhere().toString().equalsIgnoreCase("")) {
					Column c = new Column(new Table(null, null), "1=1");// 不应该出现这个情况
					Expression[] exps = new Expression[] { c };
					plainSelect.setWhere(exps[0]);
				}

			}
		}

	}

	private void handelJdbcNamedParameter(BinaryExpression expression, PlainSelect parentexp, int lefandright)
			throws ParamNotFoundException, ErrorDatasetDefineException {
		if (expression.getRightExpression() instanceof JdbcNamedParameter) {
			boolean paramIsExists = paramIsExists(expression.getRightExpression());
			if (!paramIsExists) {
				Column c = new Column(new Table(null, null), "1=1");// 不应该出现这个情况
				Expression[] exps = new Expression[] { c };
				parentexp.setWhere(exps[0]);
			}
		} else {
			expression.accept(this);
		}
	}

	@Override
	public void visit(SubJoin subjoin) {
		subjoin.getLeft().accept(this);
	}

	@Override
	public void visit(Table tableName) {

	}

	@Override
	public void visit(LateralSubSelect arg0) {
		arg0.getSubSelect().getSelectBody().accept(this);
	}

	@Override
	public void visit(ValuesList arg0) {

	}

	@Override
	public void visit(TableFunction arg0) {
		arg0.getFunction().accept(this);
	}

	@Override
	public void visit(SignedExpression arg0) {
		arg0.getExpression().accept(this);
	}

	@Override
	public void visit(JdbcNamedParameter arg0) {
		arg0.getName();
		ConditionMeta conditionMeta = new ConditionMeta();
		conditionMeta.setDatasetId(datasetMeta.getId());
		conditionMeta.setName(arg0.getName());
		conditionMeta.setId(arg0.getName());
		if (Sqlparser.SQL_ACTION_PARESE == this.actionType) {
			datasetMeta.addConditionMeta(conditionMeta);
		}

	}

	@Override
	public void visit(HexValue arg0) {
		arg0.setValue(null);
	}

	@Override
	public void visit(CastExpression arg0) {
		arg0.getLeftExpression().accept(this);
	}

	@Override
	public void visit(Modulo arg0) {
		arg0.getLeftExpression().accept(this);
		arg0.getRightExpression().accept(this);
	}

	@Override
	public void visit(AnalyticExpression arg0) {
		arg0.getExpression().accept(this);
	}

	@Override
	public void visit(WithinGroupExpression arg0) {

	}

	@Override
	public void visit(ExtractExpression arg0) {

	}

	@Override
	public void visit(IntervalExpression arg0) {

	}

	@Override
	public void visit(OracleHierarchicalExpression arg0) {
		try {
			if (Sqlparser.SQL_ACTION_PARESE == this.actionType) {
				arg0.getConnectExpression().accept(this);
				arg0.getStartExpression().accept(this);
			} else {
				arg0.getConnectExpression().accept(this);
				handelOracleStartExp(arg0.getStartExpression(), arg0);
			}
		} catch (ParamNotFoundException ex) {
			this.exisexception = true;
			if (!notfoundparams.contains(ex.getParamsname())) {
				notfoundparams.add(ex.getParamsname());
			}
			return;
		} catch (ErrorDatasetDefineException eex) {
			this.exisexception = true;
			this.setErrorMess(eex.getMessage());
			return;
		}

	}

	private void handelOracleStartExp(Expression expression, OracleHierarchicalExpression arg0)
			throws ParamNotFoundException, ErrorDatasetDefineException {

		if (expression instanceof ExistsExpression || expression instanceof InExpression) {
			boolean ismultilogic = isMultilogicExpression(expression);
			if (ismultilogic) {
				expression.accept(this);
			} else {
				if (isContainJdbcNamedParameterExp(expression)) {
					if (!paramIsExists(expression)) {
						Column c = new Column(new Table(null, null), "1=1");// 不应该出现这个情况
						Expression[] exps = new Expression[] { c };
						arg0.setStartExpression(exps[0]);
					} else {
						expression.accept(this);
					}

				} else {
					expression.accept(this);
				}
			}

		} else if (expression instanceof LikeExpression || expression instanceof EqualsTo
				|| expression instanceof GreaterThan || expression instanceof GreaterThanEquals
				|| expression instanceof NotEqualsTo) {
			BinaryExpression binaryExpression = (BinaryExpression) expression;
			if (binaryExpression.getRightExpression() instanceof JdbcNamedParameter
					|| binaryExpression.getRightExpression() instanceof Concat
					|| isSimpleFunction(binaryExpression.getRightExpression())
					|| isSimpleSubSelect(binaryExpression.getRightExpression())) {
				boolean paramIsExists = paramIsExists(expression);
				if (!paramIsExists) {
					Column c = new Column(new Table(null, null), "1=1");// 不应该出现这个情况
					Expression[] exps = new Expression[] { c };
					arg0.setStartExpression(exps[0]);
				}

			} else {
				expression.accept(this);
			}

		} else {
			expression.accept(this);
		}

	}

	@Override
	public void visit(RegExpMatchOperator arg0) {

	}

	@Override
	public void visit(JsonExpression arg0) {

	}

	@Override
	public void visit(RegExpMySQLOperator arg0) {

	}

	@Override
	public void visit(UserVariable arg0) {

	}

	@Override
	public void visit(NumericBind arg0) {

	}

	@Override
	public void visit(KeepExpression arg0) {

	}

	@Override
	public void visit(MySQLGroupConcat arg0) {

	}

	@Override
	public void visit(RowConstructor arg0) {

	}

	@Override
	public void visit(OracleHint arg0) {

	}

	@Override
	public void visit(SetOperationList arg0) {
		List<SelectBody> selects = arg0.getSelects();
		for (SelectBody selectBody : selects) {
			selectBody.accept(this);
		}
	}

	@Override
	public void visit(WithItem arg0) {
		arg0.getSelectBody().accept(this);
		List<SelectItem> selectitems = arg0.getWithItemList();
		for (SelectItem selectItem : selectitems) {
			selectItem.accept(this);
		}
	}

	@Override
	public void visit(ExpressionList expressionList) {
		List<Expression> expressions = expressionList.getExpressions();
		for (Expression expression : expressions) {
			expression.accept(this);
		}
	}

	@Override
	public void visit(MultiExpressionList multiExprList) {
		for (ExpressionList list : multiExprList.getExprList()) {
			visit(list);
		}
	}

	@Override
	public void visit(AllColumns allColumns) {

	}

	@Override
	public void visit(AllTableColumns allTableColumns) {

	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		selectExpressionItem.getExpression().accept(this);

	}

	private void uppercaseMapKey(Map<String, ConditionMeta> conditionMetas) {
		Map<String, ConditionMeta> newParams = new HashMap<String, ConditionMeta>();
		Set<String> keyset = conditionMetas.keySet();
		Iterator<String> iterator = keyset.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			ConditionMeta value = conditionMetas.get(key);
			newParams.put(key.toUpperCase(), value);
		}
		conditionMetas.clear();
		conditionMetas.putAll(newParams);
	}

	public void setErrorMess(String errorMess) {
		this.errorMess = errorMess;
	}

}
