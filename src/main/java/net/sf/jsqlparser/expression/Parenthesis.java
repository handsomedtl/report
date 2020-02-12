package net.sf.jsqlparser.expression;

public class Parenthesis implements Expression {

	private Expression expression;
	private boolean not = false;

	public Parenthesis() {
	}

	public Parenthesis(Expression expression) {
		setExpression(expression);
	}

	public Expression getExpression() {
		return expression;
	}

	public final void setExpression(Expression expression) {
		this.expression = expression;
	}

	@Override
	public void accept(ExpressionVisitor expressionVisitor) {
		expressionVisitor.visit(this);
	}

	public void setNot() {
		not = true;
	}

	public boolean isNot() {
		return not;
	}

	@Override
	public String toString() {
		if (this.getExpression() != null && !this.getExpression().toString().equalsIgnoreCase("")) {
			return (not ? "NOT " : "") + "(" + expression + ")";
		} else {
			return "";
		}

	}
}
