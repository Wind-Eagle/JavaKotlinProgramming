import javax.inject.Inject;

public class SecondClass {
    int field = 0;
    @Inject
    public SecondClass() {
        field = 500;
    }
    public int calculate() {
        return field;
    }
}
