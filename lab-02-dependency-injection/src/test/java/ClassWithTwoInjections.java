import javax.inject.Inject;

public class ClassWithTwoInjections {
    int field = 500;
    @Inject
    public ClassWithTwoInjections() {
        field = 200;
    }
    @Inject
    public ClassWithTwoInjections(SecondClass cl) {
        field = 250;
    }
}
