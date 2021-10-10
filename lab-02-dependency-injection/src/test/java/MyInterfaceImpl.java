import javax.inject.Inject;

public class MyInterfaceImpl implements MyInterface {
    int field = 200;
    @Inject
    public MyInterfaceImpl() {
        field = 250;
    }
    public int calculate() {
        return field;
    }
}
