package ro.anud.globalcooldown.processor.command;

import java.util.Objects;

public class AgingCommand<T, R> implements Command<T, R> {

    private Long age;
    private Command<T, R> command;

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

    @Override
    public String toString() {
        return "AgingCommand {age: " + age + ", command: " + command.toString() + "}";
    }
}
