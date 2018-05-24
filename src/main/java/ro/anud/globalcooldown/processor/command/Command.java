package ro.anud.globalcooldown.processor.command;

public interface Command<T, R> {

    public R execute(T t);

}
