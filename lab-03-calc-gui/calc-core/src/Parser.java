class ExpressionParseException extends Exception{
    ExpressionParseException(String code) {
        code_ = code;
    }
    String getCode() {
        return code_;
    }
    private String code_ = "";
}

public interface Parser {
    Expression parseExpression(String input) throws ExpressionParseException;
}
