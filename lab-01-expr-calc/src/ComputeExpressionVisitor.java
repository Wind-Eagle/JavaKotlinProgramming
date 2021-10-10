import java.util.Map;

import static java.lang.Math.abs;

public class ComputeExpressionVisitor implements ExpressionVisitor {
    public Object visitUnaryExpression(UnaryExpression expr) {
        double innerExpr = (double) expr.getExpression().accept(new ComputeExpressionVisitor());
        double res = 0;
        if (expr.getOperaion() == UnOpKind.UNSUB) {
            res = -innerExpr;
        } else if (expr.getOperaion() == UnOpKind.ABS) {
            res = abs(innerExpr);
        }
        return res;
    }
    public Object visitBinaryExpression(BinaryExpression expr) {
        double left = (double) expr.getLeft().accept(new ComputeExpressionVisitor());
        double right = (double) expr.getRight().accept(new ComputeExpressionVisitor());
        double res = 0;
        if (expr.getOperaion() == BinOpKind.ADD) {
            res = left + right;
        } else if (expr.getOperaion() == BinOpKind.SUB) {
            res = left - right;
        } else if (expr.getOperaion() == BinOpKind.MUL) {
            res = left * right;
        } else if (expr.getOperaion() == BinOpKind.DIV) {
            res = left / right;
        } else if (expr.getOperaion() == BinOpKind.MOD) {
            res = left % right;
        }
        return res;
    }
    public Object visitLiteral(Literal expr) {
        return expr.getValue();
    }
    public Object visitParenthesis(ParenthesisExpression expr) {
        return expr.getExpr().accept(new ComputeExpressionVisitor());
    }
}
