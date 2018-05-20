package ro.anud.globalcooldown.processor.command;

import java.util.function.Function;

public class CommandPipe<OriginalInput, LastReturn> implements Command<OriginalInput, LastReturn> {

    private Function<OriginalInput, LastReturn> start;

    public static <Input> CommandPipe<Input, Input> create(Class<Input> inputClass) {
        return new CommandPipe<Input, Input>();
    }

    private CommandPipe() {

    }

    private CommandPipe(CommandPipe commandPipe, Function function) {
        if (commandPipe.start == null) {
            this.start = function;
        } else {
            this.start = commandPipe.start.andThen(function);
        }
    }

    public <T> CommandPipe<OriginalInput, T> then(Function<LastReturn, T> function) {
        return new CommandPipe<>(this, function);
    }

    @Override
    public LastReturn execute(OriginalInput arg) {
        return start.apply(arg);
    }
}
