public interface DependencyInjection {
    public void register(Class<?> c);
    public void register(Class<?> interf, Class<?> c);
    public void completeRegistration();
    public Object resolve(Class<?> c);
}
