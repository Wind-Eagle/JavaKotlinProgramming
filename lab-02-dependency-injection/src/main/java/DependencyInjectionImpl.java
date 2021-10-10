import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DependencyInjectionImpl implements DependencyInjection {
    Boolean isRegistrationCompleted = false;
    Map<Class<?>, Constructor<?>> classes = new HashMap<>();
    Map<Class<?>, Integer> class_numbers = new HashMap<>();
    Map<Class<?>, Class<?>> interfaces = new HashMap<>();
    List<Class<?>> decrypted_classes = new ArrayList<>();
    List<List<Integer>> dependencies;
    Map<Class<?>, Object> singletons = new HashMap<>();
    public void register(Class<?> c) {
        if (isRegistrationCompleted) {
            throw new RuntimeException("Error! Registration is already completed!");
        }
        if (!classes.containsKey(c)) {
            classes.put(c, GetConstructor(c));
        }
    }
    public void register(Class<?> interf, Class<?> c) {
        if (isRegistrationCompleted) {
            throw new RuntimeException("Error! Registration is already completed!");
        }
        if (!interf.isAssignableFrom(c)) {
            throw new RuntimeException("Classes are not interface and implementation!");
        }
        if (!interfaces.containsKey(interf)) {
            interfaces.put(interf, c);
            classes.put(c, GetConstructor(c));
        } else {
            if (interfaces.get(interf) != c) {
                throw new RuntimeException("Error! Two implementations registered for one interface!");
            }
        }
    }
    public void completeRegistration() {
        isRegistrationCompleted = true;
        int num = 0;
        for (var cl : classes.entrySet()) {
            class_numbers.put(cl.getKey(), num++);
            decrypted_classes.add(cl.getKey());
        }
        dependencies = new ArrayList<List<Integer>>();
        for (int i = 0; i < classes.size(); i++) {
            dependencies.add(new ArrayList<Integer>());
        }

        for (var cl : classes.entrySet()) {
            int cur = class_numbers.get(cl.getKey());
            Class<?>[] parameters = cl.getValue().getParameterTypes();
            for (var dpnd: parameters) {
                int index = class_numbers.get(dpnd);
                dependencies.get(cur).add(index);
            }
        }
    }
    private Object GetClass(Class<?> c) {
        Object result = null;
        if (singletons.containsKey(c)) {
            return singletons.get(c);
        }
        Constructor<?> constructor = classes.get(c);
        ArrayList<Object> list = new ArrayList<Object>();
        for (var i : dependencies.get(class_numbers.get(c))) {
            list.add(GetClass(decrypted_classes.get(i)));
        }
        try {
            result = constructor.newInstance(list.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exp) {
            exp.printStackTrace();
        }
        if (c.isAnnotationPresent(Singleton.class)) {
            singletons.put(c, result);
        }
        return result;
    }
    public Object resolve(Class<?> c) {
        if (!isRegistrationCompleted) {
            throw new RuntimeException("Registration is not completed!");
        }
        if (c.isInterface()) {
            if (!interfaces.containsKey(c)) {
                throw new RuntimeException("Interface was not registered!");
            }
            return GetClass(interfaces.get(c));
        }
        if (!classes.containsKey(c)) {
            throw new RuntimeException("Class was not registered!");
        }
        return GetClass(c);
    }
    private Constructor<?> GetConstructor(Class<?> cl) {
        Constructor[] constructors = cl.getConstructors();
        Constructor<?> constructor = null;
        for (var cnst : constructors) {
            if (cnst.isAnnotationPresent(Inject.class)) {
                if (constructor != null) {
                    throw new RuntimeException("Error! Two or more @Inject constructors found!");
                }
                constructor = cnst;
            }
        }
        if (constructor == null) {
            throw new RuntimeException("Error! No @Inject constructors found!");
        }
        return constructor;
    }
}
