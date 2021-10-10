import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SecondSingleton {
    String inner;
    @Inject
    public SecondSingleton() {
        inner = "123456789";
    }
    int calculate() {
        return inner.length();
    }
}
