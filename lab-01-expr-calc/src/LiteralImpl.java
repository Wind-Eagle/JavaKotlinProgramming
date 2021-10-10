public class LiteralImpl implements Literal {
    LiteralImpl(double value, String name) {
        value_ = value;
        name_ = name;
    }
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
    public Object accept(DebugRepresentationExpressionVisitor visitor) {
        return visitor.visitLiteral(this);
    }
    public Object accept(ToStringVisitor visitor) {
        return visitor.visitLiteral(this);
    }
    public double getValue() {
        return value_;
    }
    public String getLiteralName() {
        return name_;
    }
    double value_ = 0;
    String name_ = "";
}
