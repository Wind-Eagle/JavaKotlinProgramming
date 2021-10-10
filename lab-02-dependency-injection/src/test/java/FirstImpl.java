import javax.inject.Inject;

public class FirstImpl implements SecondInterface {
    int field = 200;
    @Inject
    public FirstImpl() {
        field = 250;
    }
}
