import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {
    static ParserImpl parser = new ParserImpl();
    public static Expression checkParseCorrectness(String s, Double res) {
        Expression expr = null;
        try {
            expr = parser.parseExpression(s);
        }
        catch(ExpressionParseException ex) {
            System.out.println(ex.getCode());
            return null;
        }
        String strRes = (String)(expr.accept(new ToStringVisitor()));
        if (strRes.replaceAll(" ", "").equals(s.replaceAll(" ", ""))) {
            return expr;
        } else {
            return null;
        }
    }
    public static boolean checkCalculationCorrectness(Expression expr, Double res) {
        return abs((Double)expr.accept(new ComputeExpressionVisitor()) - res) <= 0.00001;
    }
    public static void main(String[] args) {
        ArrayList<String> list_str = new ArrayList<>();
        parser.addValue("x", -3.5);
        parser.addValue("y", 2.3);
        parser.addValue("z", 0.0);
        parser.addValue("d", 5.6);
        parser.addValue("e", 1.0);
        list_str.add("2");
        list_str.add("5   *(6 - 3)");
        list_str.add("-5 * (   6-3   )");
        list_str.add("abs(5 * (6- 3))");
        list_str.add("2 + 2 * 2 - 5 * (3 - 2) + 6 / 2 + 1");
        list_str.add("-3 * abs(5 - 12 / 1 * 1)");
        list_str.add("0 + 0 * 0");
        list_str.add("(-5) * abs(-2 + (-2)) - (-19 + 1)");
        list_str.add("x * abs(y + (-d)) - (-e + z)");
        list_str.add("-6 / 2 + 5825 - 2575 * abs(-525 + abs(52)) - 1 / 1 + 104810 / (18450 - 18460) + 6 * (2 + 20)");
        ArrayList<Double> list_val = new ArrayList<>();
        list_val.add(2.0);
        list_val.add(15.0);
        list_val.add(-15.0);
        list_val.add(15.0);
        list_val.add(5.0);
        list_val.add(-21.0);
        list_val.add(0.0);
        list_val.add(-2.0);
        list_val.add(-10.55);
        list_val.add(-1222503.0);
        for (int i = 0; i < list_str.size(); i++) {
            Expression expr = checkParseCorrectness(list_str.get(i), list_val.get(i));
            if (expr == null) {
                System.out.println("Parse problem in: " + list_str.get(i) + " " + list_val.get(i));
                break;
            }
            if (!checkCalculationCorrectness(expr, list_val.get(i))) {
                System.out.println("Calculation problem in: " + list_str.get(i) + " " + list_val.get(i));
                break;
            }
        }
    }
}