package ro.anud.globalcooldown.processor.command;

import java.util.Objects;

public class AgingCommand<T, R> implements Command<T, R> {

    private Long age;
    private Command<T, R> command;

    static public <T, R> AgingCommand<T, R> wrap(Command<T, R> command) {
        return new AgingCommand<>(command);
    }

    public AgingCommand(Command<T, R> command) {
        this.age = 0L;
        this.command = Objects.requireNonNull(command, "command must not be null");
    }

    @Override
    public R execute(T arg) {
        this.age++;
        return command.execute(arg);
    }


    public Long getAge() {
        return age;
    }
}
