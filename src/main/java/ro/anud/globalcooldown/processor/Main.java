package ro.anud.globalcooldown.processor;

import ro.anud.globalcooldown.processor.command.AgingCommand;
import ro.anud.globalcooldown.processor.command.Command;
import ro.anud.globalcooldown.processor.command.CommandPipe;

public class Main {

    public static void main(String[] args) {



        CommandPipe<Integer, String> integerToCharacter = CommandPipe.create(Integer.class)
                .then(arg -> {
                    System.out.println(arg);
                    return arg + " integerToCharacte first";
                })
                .then(arg -> {
                    System.out.println(arg);
                    return arg + " integerToCharacte second";

                });

        CommandPipe<Integer, String> stringToInt = CommandPipe
                .create(Integer.class)
                .then(integerToCharacter)
                .then(integer -> integer + " hello")
                .then(s -> s + " Lorem Ipsum")
                .then(s -> s.split("Ipsum")[0])
                .then(String::toLowerCase)
                .then(s -> s.split("R")[0]);

        AgingCommand<Integer, String> agingCommand = new AgingCommand<>(stringToInt);
        System.out.println(agingCommand.execute(5));
        System.out.println(agingCommand.execute(1));
        System.out.println(agingCommand.execute(2));
        System.out.println(agingCommand.execute(3));
        System.out.println(agingCommand);
    }
}
