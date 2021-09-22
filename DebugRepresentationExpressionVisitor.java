import static java.lang.Math.abs;

public class DebugRepresentationExpressionVisitor implements ExpressionVisitor {
    public Object visitUnaryExpression(UnaryExpression expr) {
        String res = (String) expr.getExpression().accept(new DebugRepresentationExpressionVisitor());
        if (expr.getOperaion() == UnOpKind.UNSUB) {
            res = "-" + res;
        } else if (expr.getOperaion() == UnOpKind.ABS) {
            res = "abs(" + res + ")";
        }
        return res;
    }
    public Object visitBinaryExpression(BinaryExpression expr) {
        String left = (String) expr.getLeft().accept(new DebugRepresentationExpressionVisitor());
        String right = (String) expr.getRight().accept(new DebugRepresentationExpressionVisitor());
        String res = "";
        if (expr.getOperaion() == BinOpKind.ADD) {
            res = "add("+left+", "+right+")";
        } else if (expr.getOperaion() == BinOpKind.SUB) {
            res = "sub("+left+", "+right+")";
        } else if (expr.getOperaion() == BinOpKind.MUL) {
            res = "mul("+left+", "+right+")";
        } else if (expr.getOperaion() == BinOpKind.DIV) {
            res = "div("+left+", "+right+")";
        } else if (expr.getOperaion() == BinOpKind.MOD) {
            res = "mod("+left+", "+right+")";
        }
        return res;
    }
    public Object visitLiteral(Literal expr) {
        return String.valueOf(expr.getValue());
    }
    public Object visitParenthesis(ParenthesisExpression expr) {
        return "paran-expr(" + (String) expr.getExpr().accept(new DebugRepresentationExpressionVisitor()) + ")";
    }
}