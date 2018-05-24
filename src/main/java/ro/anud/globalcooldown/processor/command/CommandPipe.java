package ro.anud.globalcooldown.processor.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CommandPipe<OriginalInput, LastReturn> implements Command<OriginalInput, LastReturn> {
    private List<Command> commandList;

    public static <Input> CommandPipe<Input, Input> create(Class<Input> inputClass) {
        return new CommandPipe<>();
    }

    public CommandPipe() {
        commandList = new ArrayList<>();
        commandList.add((arg -> arg));
    }

    public CommandPipe(CommandPipe commandPipe, Command function) {
        if (commandPipe.commandList == null) {
            this.commandList = new ArrayList<>();
            this.commandList.add(function);
        } else {
            commandList = commandPipe.commandList;
            commandList.add(function);
        }
    }

    public <T> CommandPipe<OriginalInput, T> then(Command<LastReturn, T> function) {
        return new CommandPipe<>(this, function);
    }

    public <T> Conditional<OriginalInput, LastReturn> when(Predicate<OriginalInput> predicate) {
        return new Conditional<>(this, predicate);
    }

    @Override
    public LastReturn execute(OriginalInput arg) {
        Object argument = arg;
        for (Command command : commandList) {
            argument = command.execute(argument);
        }
        return (LastReturn) argument;
    }

    @Override
    public String toString() {
        return "CommandPipe {commandList: " + commandList.toString() + "}";
    }
}
