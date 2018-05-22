package ro.anud.globalcooldown.processor.command;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CommandPipe<OriginalInput, LastReturn> implements Command<OriginalInput, LastReturn> {
    private List<Command> commandList;

    public static <Input> CommandPipe<Input, Input> create(Class<Input> inputClass) {
        return new CommandPipe<Input, Input>();
    }

    private CommandPipe() {

    }

    private CommandPipe(CommandPipe commandPipe, Command function) {
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
