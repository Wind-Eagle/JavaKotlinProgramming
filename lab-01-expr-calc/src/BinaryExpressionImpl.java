public class BinaryExpressionImpl implements BinaryExpression {
    BinaryExpressionImpl(Expression left, Expression right, BinOpKind operation) {
        left_ = left;
        right_ = right;
        operation_ = operation;
    }
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }
    public Object accept(DebugRepresentationExpressionVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }
    public Object accept(ToStringVisitor visitor) {
        return visitor.visitBinaryExpression(this);
    }
    public Expression getLeft() {
        return left_;
    }
    public Expression getRight() {
        return right_;
    }
    public BinOpKind getOperaion() {
        return operation_;
    }
    Expression left_ = null;
    Expression right_ = null;
    BinOpKind operation_ = BinOpKind.UNDEF;
}
