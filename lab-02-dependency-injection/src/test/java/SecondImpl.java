import javax.inject.Inject;

public class SecondImpl implements SecondInterface {
    int field = 200;
    @Inject
    public SecondImpl() {
        field = 150;
    }
}
