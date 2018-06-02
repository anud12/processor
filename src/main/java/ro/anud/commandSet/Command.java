package ro.anud.commandSet;

public interface Command<T, R> {

    public R execute(T t);

}
