package ro.anud.globalcooldown.processor;

import ro.anud.globalcooldown.processor.command.AgingCommand;
import ro.anud.globalcooldown.processor.command.CommandPipe;

public class Main {

    public static void main(String[] args) {

        CommandPipe<Integer, Character> commandPipe = CommandPipe
                .create(Integer.class)
                .then(integer -> integer + " hello")
                .then(s -> s + " Lorem Ipsum")
                .then(s -> s.split("Ipsum")[0])
                .then(String::toUpperCase)
                .then(s -> s.split("R")[0])
                .then(s -> s.charAt(0));

        AgingCommand<Integer, Character> agingCommand = new AgingCommand<>(commandPipe);
        System.out.println(agingCommand.execute(5));
        System.out.println(agingCommand.execute(1));
        System.out.println(agingCommand.execute(2));
        System.out.println(agingCommand.execute(3));
        System.out.println(agingCommand);
    }
}
