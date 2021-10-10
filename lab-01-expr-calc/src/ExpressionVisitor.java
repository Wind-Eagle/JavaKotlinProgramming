public interface ExpressionVisitor {
    Object visitUnaryExpression(UnaryExpression expr);
    Object visitBinaryExpression(BinaryExpression expr);
    Object visitLiteral(Literal expr);
    Object visitParenthesis(ParenthesisExpression expr);
}