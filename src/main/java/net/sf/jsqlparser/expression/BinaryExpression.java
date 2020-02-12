package net.sf.jsqlparser.expression;

public abstract class BinaryExpression implements Expression {

	private Expression leftExpression;
	private Expression rightExpression;
	private boolean not = false;

	public BinaryExpression() {
	}

	public Expression getLeftExpression() {
		return leftExpression;
	}

	public Expression getRightExpression() {
		return rightExpression;
	}

	public void setLeftExpression(Expression expression) {
		leftExpression = expression;
	}

	public void setRightExpression(Expression expression) {
		rightExpression = expression;
	}

	public void setNot() {
		not = true;
	}

	public boolean isNot() {
		return not;
	}

	@Override
	public String toString() {
		if ((getRightExpression() == null||getRightExpression().toString().equalsIgnoreCase("")) && this.getLeftExpression() != null) {
			return (not ? "NOT " : "") + getLeftExpression() + " ";
		} else if ((this.getLeftExpression() == null||this.getLeftExpression().toString().equalsIgnoreCase("")) && getRightExpression() != null) {
			return (not ? "NOT " : "") + getRightExpression() + " ";
		} else if (getRightExpression() == null && this.getLeftExpression() == null) {
			return "";
		} else {
			return (not ? "NOT " : "") + getLeftExpression() + " " + this.getStringExpression() + " "
					+ this.getRightExpression();
		}

	}

	public abstract String getStringExpression();
}
