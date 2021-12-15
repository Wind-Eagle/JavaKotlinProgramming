import static java.lang.Math.abs;

public class ToStringVisitor implements ExpressionVisitor {
    public Object visitUnaryExpression(UnaryExpression expr) {
        String res = (String) expr.getExpression().accept(new ToStringVisitor());
        if (expr.getOperaion() == UnOpKind.UNSUB) {
            res = "-" + res;
        } else if (expr.getOperaion() == UnOpKind.ABS) {
            res = "abs" + res;
        }
        return res;
    }
    public Object visitBinaryExpression(BinaryExpression expr) {
        String left = (String) expr.getLeft().accept(new ToStringVisitor());
        String right = (String) expr.getRight().accept(new ToStringVisitor());
        String res = "";
        if (expr.getOperaion() == BinOpKind.ADD) {
            res = left + "+" + right;
        } else if (expr.getOperaion() == BinOpKind.SUB) {
            res = left + "-" + right;
        } else if (expr.getOperaion() == BinOpKind.MUL) {
            res = left + "*" + right;
        } else if (expr.getOperaion() == BinOpKind.DIV) {
            res = left + "/" + right;
        } else if (expr.getOperaion() == BinOpKind.MOD) {
            res = left + "%" + right;
        }
        return res;
    }
    public Object visitLiteral(Literal expr) {
        return expr.getLiteralName();
    }
    public Object visitParenthesis(ParenthesisExpression expr) {
        return "(" + (String) expr.getExpr().accept(new ToStringVisitor()) + ")";
    }
}