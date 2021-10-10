import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirstSingleton {
    SecondSingleton inner;
    ThirdSingleton secondInner;
    @Inject
    public FirstSingleton(SecondSingleton s, ThirdSingleton t) {
        inner = s;
        secondInner = t;
    }
    int calculate() {
        return inner.calculate() * secondInner.calculate();
    }
}
