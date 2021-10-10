import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ThirdSingleton {
    FirstClass inner;
    @Inject
    public ThirdSingleton(FirstClass cl) {
        inner = cl;
    }
    int calculate() {
        return inner.calculate();
    }
}
