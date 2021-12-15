import java.util.ArrayList;
import java.util.*;

public class ParserImpl implements Parser {
    enum TokenType {
        UNDEF,
        LITERAL,
        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
        ABS,
        UNSUB,
        OPENBR,
        CLOSEBR
    }
    class Token {
        TokenType type_;
        double value_ = 0;
        String name_ = "";
        Token(TokenType type) {
            type_ = type;
        }
        Token(char input) {
            value_ = 0;
            switch (input) {
                case '+': {
                    type_ = TokenType.ADD;
                    break;
                }
                case '-': {
                    type_ = TokenType.SUB;
                    break;
                }
                case '*': {
                    type_ = TokenType.MUL;
                    break;
                }
                case '/': {
                    type_ = TokenType.DIV;
                    break;
                }
                case '%': {
                    type_ = TokenType.MOD;
                    break;
                }
                case '(': {
                    type_ = TokenType.OPENBR;
                    break;
                }
                case ')': {
                    type_ = TokenType.CLOSEBR;
                    break;
                }
                default: {
                    type_ = TokenType.UNDEF;
                    break;
                }
            }
        }
        Token(String input) throws ExpressionParseException {
            type_ = TokenType.UNDEF;
            value_ = 0;
            name_ = "";
            if (input.length() == 0) return;
            if (input.length() == 1) {
                Token parsedToken = new Token(input.charAt(0));
                type_ = parsedToken.type_;
            }
            if (input.equals("abs")) {
                type_ = TokenType.ABS;
                return;
            }
            if (type_ == TokenType.UNDEF) {
                type_ = TokenType.LITERAL;
                for (int i = 0; i < input.length(); i++) {
                    if (input.charAt(i) < '0' || input.charAt(i) > '9') {
                        type_ = TokenType.UNDEF;
                        value_ = 0;
                        break;
                    }
                    value_ *= 10;
                    value_ += input.charAt(i) - '0';
                }
                name_ = Integer.toString((int)value_);
                if (type_ == TokenType.UNDEF) {
                    type_ = TokenType.LITERAL;
                    for (int i = 0; i < input.length(); i++) {
                        if (input.charAt(i) < 'a' || input.charAt(i) > 'z') {
                            type_ = TokenType.UNDEF;
                            value_ = 0;
                            break;
                        }
                    }
                    if (type_ == TokenType.UNDEF) {
                        throw new ExpressionParseException("Unknown literal");
                    }
                    if (values_.containsKey(input)) {
                        value_ = values_.get(input);
                    } else {
                        /*System.out.println("Enter a value for literal " + input);
                        Scanner in = new Scanner(System.in);
                        String s = in.nextLine();
                        Double value = Double.parseDouble(s);
                        value_ = value;
                        values_.put(input, value);*/
                        throw new ExpressionParseException("No value for the variable");
                    }
                    name_ = input;
                }
            }
        }
    }
    Map<String, Double> values_ = new HashMap<>();
    ArrayList<Token> infixTokens = new ArrayList<>();
    ArrayList<Token> postfixTokens = new ArrayList<>();
    void addValue(String str, Double val) {
        values_.put(str, val);
    }
    void InsertToken(ArrayList<Token> tokens, String input) throws ExpressionParseException {
        Token tryToken = new Token(input);
        if (tryToken.type_ != TokenType.UNDEF) {
            tokens.add(tryToken);
        }
    }
    void parseFirstStage(String input) throws ExpressionParseException {
        infixTokens.clear();
        StringBuilder currentToken = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '-') {
                if (infixTokens.size() == 0
                        || (infixTokens.get(infixTokens.size() - 1).type_ == TokenType.OPENBR && currentToken.length() == 0)) {
                    InsertToken(infixTokens, currentToken.toString());
                    currentToken = new StringBuilder();
                    infixTokens.add(new Token(TokenType.UNSUB));
                    continue;
                }
            }
            if (input.charAt(i) == ' ') {
                InsertToken(infixTokens, currentToken.toString());
                currentToken = new StringBuilder();
                continue;
            }
            if (input.charAt(i) == '(' || input.charAt(i) == ')') {
                InsertToken(infixTokens, currentToken.toString());
                currentToken = new StringBuilder();
                infixTokens.add(new Token(input.charAt(i)));
                continue;
            }
            if (input.charAt(i) == '-' || input.charAt(i) == '+' || input.charAt(i) == '*' || input.charAt(i) == '/'
                    || input.charAt(i) == '%') {
                InsertToken(infixTokens, currentToken.toString());
                currentToken = new StringBuilder();
                infixTokens.add(new Token(input.charAt(i)));
                continue;
            }
            currentToken.append(input.charAt(i));
        }
        if (currentToken.length() != 0) {
            infixTokens.add(new Token(currentToken.toString()));
        }
    }
    void parseSecondStage() throws ExpressionParseException {
        postfixTokens.clear();
        Stack<Token> stackTokens = new Stack<>();
        stackTokens.push(new Token('z'));
        for (Token i : infixTokens) {
            switch (i.type_) {
                case LITERAL: {
                    postfixTokens.add(i);
                    break;
                }
                case UNSUB:
                case ABS: {
                    stackTokens.add(i);
                    break;
                }
                case OPENBR: {
                    stackTokens.add(i);
                    postfixTokens.add(i);
                    break;
                }
                case CLOSEBR: {
                    while (stackTokens.peek().type_ != TokenType.OPENBR) {
                        postfixTokens.add(stackTokens.peek());
                        if (stackTokens.size() == 0) {
                            throw new ExpressionParseException("Wrong expression");
                        }
                        stackTokens.pop();
                    }
                    postfixTokens.add(i);
                    if (stackTokens.size() == 0) {
                        throw new ExpressionParseException("Wrong expression");
                    }
                    stackTokens.pop();
                    break;
                }
                case ADD:
                case SUB: {
                    while (stackTokens.peek().type_ == TokenType.ADD ||
                            stackTokens.peek().type_ == TokenType.ABS ||
                            stackTokens.peek().type_ == TokenType.SUB ||
                            stackTokens.peek().type_ == TokenType.UNSUB ||
                            stackTokens.peek().type_ == TokenType.MUL ||
                            stackTokens.peek().type_ == TokenType.DIV ||
                            stackTokens.peek().type_ == TokenType.MOD) {
                        postfixTokens.add(stackTokens.peek());
                        if (stackTokens.size() == 0) {
                            throw new ExpressionParseException("Wrong expression");
                        }
                        stackTokens.pop();
                    }
                    stackTokens.add(i);
                    break;
                }
                case MUL:
                case DIV:
                case MOD: {
                    while (stackTokens.peek().type_ == TokenType.UNSUB ||
                            stackTokens.peek().type_ == TokenType.ABS ||
                            stackTokens.peek().type_ == TokenType.MUL ||
                            stackTokens.peek().type_ == TokenType.DIV ||
                            stackTokens.peek().type_ == TokenType.MOD) {
                        postfixTokens.add(stackTokens.peek());
                        if (stackTokens.size() == 0) {
                            throw new ExpressionParseException("Wrong expression");
                        }
                        stackTokens.pop();
                    }
                    stackTokens.push(i);
                    break;
                }
                default:
                    break;
            }
        }
        while (stackTokens.size() > 1) {
            postfixTokens.add(stackTokens.peek());
            if (stackTokens.size() == 0) {
                throw new ExpressionParseException("Wrong expression");
            }
            stackTokens.pop();
        }
    }
    Expression getExpression() {
        Stack<Expression> stack = new Stack<>();
        for (Token postfixToken : postfixTokens)
            switch (postfixToken.type_) {
                case ABS: {
                    Expression exprOld = stack.peek();
                    stack.pop();
                    Expression exprNew = new UnaryExpressionImpl(exprOld, UnOpKind.ABS);
                    stack.push(exprNew);
                    break;
                }
                case UNSUB: {
                    Expression exprOld = stack.peek();
                    stack.pop();
                    Expression exprNew = new UnaryExpressionImpl(exprOld, UnOpKind.UNSUB);
                    stack.push(exprNew);
                    break;
                }
                case ADD: {
                    Expression exprOldLeft = stack.peek();
                    stack.pop();
                    Expression exprOldRight = stack.peek();
                    stack.pop();
                    Expression exprNew = new BinaryExpressionImpl(exprOldRight, exprOldLeft, BinOpKind.ADD);
                    stack.push(exprNew);
                    break;
                }
                case SUB: {
                    Expression exprOldLeft = stack.peek();
                    stack.pop();
                    Expression exprOldRight = stack.peek();
                    stack.pop();
                    Expression exprNew = new BinaryExpressionImpl(exprOldRight, exprOldLeft, BinOpKind.SUB);
                    stack.push(exprNew);
                    break;
                }
                case MUL: {
                    Expression exprOldLeft = stack.peek();
                    stack.pop();
                    Expression exprOldRight = stack.peek();
                    stack.pop();
                    Expression exprNew = new BinaryExpressionImpl(exprOldRight, exprOldLeft, BinOpKind.MUL);
                    stack.push(exprNew);
                    break;
                }
                case DIV: {
                    Expression exprOldLeft = stack.peek();
                    stack.pop();
                    Expression exprOldRight = stack.peek();
                    stack.pop();
                    Expression exprNew = new BinaryExpressionImpl(exprOldRight, exprOldLeft, BinOpKind.DIV);
                    stack.push(exprNew);
                    break;
                }
                case MOD: {
                    Expression exprOldLeft = stack.peek();
                    stack.pop();
                    Expression exprOldRight = stack.peek();
                    stack.pop();
                    Expression exprNew = new BinaryExpressionImpl(exprOldRight, exprOldLeft, BinOpKind.MOD);
                    stack.push(exprNew);
                    break;
                }
                case CLOSEBR: {
                    Expression exprOld = stack.peek();
                    stack.pop();
                    Expression exprNew = new ParenthesisExpressionImpl(exprOld);
                    stack.push(exprNew);
                    break;
                }
                case LITERAL: {
                    Expression exprNew = new LiteralImpl(postfixToken.value_, postfixToken.name_);
                    stack.push(exprNew);
                    break;
                }
                default: {
                    break;
                }
            }
        return stack.peek();
    }
    public Expression parseExpression(String input) throws ExpressionParseException {
        infixTokens.clear();
        postfixTokens.clear();
        parseFirstStage(input);
        parseSecondStage();
        return getExpression();
    }
}
