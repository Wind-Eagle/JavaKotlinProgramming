public interface UnaryExpression extends Expression {
    Expression getExpression();
    UnOpKind getOperaion();
}