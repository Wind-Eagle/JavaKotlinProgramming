import javax.inject.Inject;

public class ClassWithPrivateInjection {
    int field = 500;
    public ClassWithPrivateInjection() {
        field = 150;
    }
}
