import javax.inject.Inject;

public class FirstClass {
    SecondClass inner;
    @Inject
    public FirstClass(SecondClass s) {
        inner = s;
    }
    public int calculate() {
        return inner.calculate() * 3;
    }
    public SecondClass getSecondClass() {
        return inner;
    }
}
