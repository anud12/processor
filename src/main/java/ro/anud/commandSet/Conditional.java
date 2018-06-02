package ro.anud.commandSet;

import java.util.Objects;
import java.util.function.Predicate;

public class Conditional<LastInput, ReturnType> implements Command<LastInput, ReturnType> {

    private Predicate<LastInput> predicate;
    private CommandPipe<LastInput, ReturnType> commandType;
    private Command<LastInput, ReturnType> onTrueCommand;
    private Command<LastInput, ReturnType> onFalseCommand;


    public Conditional(Predicate<LastInput> predicate) {
        this.onTrueCommand = new CommandPipe<>();
        this.onFalseCommand = new CommandPipe<>();
        commandType = new CommandPipe<>();
        this.predicate = Objects.requireNonNull(predicate, "predicate must not be null");
    }

    public Conditional(CommandPipe<LastInput, ReturnType> commandType, Predicate<LastInput> predicate) {
        this.onTrueCommand = new CommandPipe<>();
        this.onFalseCommand = new CommandPipe<>();
        this.commandType = Objects.requireNonNull(commandType, "commandType must not be null");
        this.predicate = Objects.requireNonNull(predicate, "predicate must not be null");
    }

    public Conditional<LastInput, ReturnType> onTrue(Command<LastInput, ReturnType> command) {
        this.onTrueCommand = command;
        return this;
    }

    public Conditional<LastInput, ReturnType> onFalse(Command<LastInput, ReturnType> command) {
        this.onFalseCommand = command;
        return this;
    }

    public CommandPipe<LastInput, ReturnType> end() {
        return new CommandPipe<>(commandType, getFunction());
    }

    @Override
    public ReturnType execute(LastInput lastInput) {
        return this.getFunction().execute(lastInput);
    }

    private Command<LastInput, ReturnType> getFunction() {
        return lastInput -> {
            if (predicate.test(lastInput)) {
                return onTrueCommand.execute(lastInput);
            } else {
                return onFalseCommand.execute(lastInput);
            }
        };
    }
}
