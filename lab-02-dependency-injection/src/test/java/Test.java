import static org.junit.jupiter.api.Assertions.*;

public class Test {
    @org.junit.jupiter.api.Test
    public void TestSimple() {
        DependencyInjection DI = new DependencyInjectionImpl();
        DI.register(FirstClass.class);
        DI.register(SecondClass.class);
        DI.completeRegistration();
        FirstClass fcl = (FirstClass) DI.resolve(FirstClass.class);
        assertEquals(fcl.calculate(), 1500);
    }
    @org.junit.jupiter.api.Test
    public void TestHard() {
        DependencyInjection DI = new DependencyInjectionImpl();
        DI.register(FirstClass.class);
        DI.register(SecondClass.class);
        DI.register(FirstSingleton.class);
        DI.register(SecondSingleton.class);
        DI.register(ThirdSingleton.class);
        DI.completeRegistration();
        FirstSingleton fsgt = (FirstSingleton) DI.resolve(FirstSingleton.class);
        FirstSingleton fsgt2 = (FirstSingleton) DI.resolve(FirstSingleton.class);
        assertEquals(fsgt, fsgt2);
        SecondSingleton ssgt = (SecondSingleton) DI.resolve(SecondSingleton.class);
        SecondSingleton ssgt2 = (SecondSingleton) DI.resolve(SecondSingleton.class);
        assertEquals(ssgt, ssgt2);
        ThirdSingleton tsgt = (ThirdSingleton) DI.resolve(ThirdSingleton.class);
        ThirdSingleton tsgt2 = (ThirdSingleton) DI.resolve(ThirdSingleton.class);
        assertEquals(tsgt, tsgt2);
        assertEquals(fsgt.calculate(), 13500);
    }
    @org.junit.jupiter.api.Test
    public void TestInterfaces() {
        DependencyInjection DI = new DependencyInjectionImpl();
        DI.register(MyInterface.class, MyInterfaceImpl.class);
        DI.completeRegistration();
        MyInterfaceImpl fcl = (MyInterfaceImpl) DI.resolve(MyInterface.class);
        assertEquals(fcl.calculate(), 250);
    }
    @org.junit.jupiter.api.Test
    public void TestErrors() {
        DependencyInjection DI = new DependencyInjectionImpl();
        assertThrows(RuntimeException.class, () -> DI.register(ClassWithoutInjections.class));
        assertThrows(RuntimeException.class, () -> DI.register(ClassWithTwoInjections.class));
        assertThrows(RuntimeException.class, () -> DI.register(ClassWithPrivateInjection.class));
        assertThrows(RuntimeException.class, () -> DI.resolve(FirstClass.class));
        DI.completeRegistration();
        assertThrows(RuntimeException.class, () -> DI.register(FirstClass.class));
    }
    @org.junit.jupiter.api.Test
    public void TestInterfaceErrors() {
        DependencyInjection DI = new DependencyInjectionImpl();
        DI.register(SecondInterface.class, FirstImpl.class);
        assertThrows(RuntimeException.class, () -> DI.register(SecondInterface.class, SecondImpl.class));
        assertThrows(RuntimeException.class, () -> DI.register(FirstImpl.class, SecondImpl.class));
        assertThrows(RuntimeException.class, () -> DI.register(SecondInterface.class, SecondInterface.class));
        DI.completeRegistration();;
        assertThrows(RuntimeException.class, () -> DI.register(MyInterface.class, MyInterfaceImpl.class));
        assertThrows(RuntimeException.class, () -> DI.resolve(MyInterface.class));
    }
}
