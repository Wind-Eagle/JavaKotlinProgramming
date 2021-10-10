public class UnaryExpressionImpl implements UnaryExpression {
    UnaryExpressionImpl(Expression expr, UnOpKind operation) {
        expr_ = expr;
        operation_ = operation;
    }
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }
    public Object accept(DebugRepresentationExpressionVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }
    public Object accept(ToStringVisitor visitor) {
        return visitor.visitUnaryExpression(this);
    }
    public Expression getExpression() {
        return expr_;
    }
    public UnOpKind getOperaion() {
        return operation_;
    }
    UnOpKind operation_ = UnOpKind.UNDEF;
    Expression expr_ = null;
}
