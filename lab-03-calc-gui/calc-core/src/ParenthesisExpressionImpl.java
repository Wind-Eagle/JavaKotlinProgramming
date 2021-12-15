public class ParenthesisExpressionImpl implements ParenthesisExpression {
    ParenthesisExpressionImpl(Expression expr) {
        expr_ = expr;
    }
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
    public Object accept(DebugRepresentationExpressionVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
    public Object accept(ToStringVisitor visitor) {
        return visitor.visitParenthesis(this);
    }
    public Expression getExpr() {
        return expr_;
    }
    Expression expr_ = null;
}

