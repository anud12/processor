package ro.anud.globalcooldown.processor;

import org.junit.Test;
import ro.anud.globalcooldown.processor.command.Command;
import ro.anud.globalcooldown.processor.command.CommandPipe;

import java.util.stream.Stream;

public class ConditionalTest {

    @Test
    public void test() {
        Command<Integer, ?> command = CommandPipe.create(Integer.class)
                .then(arg -> {
                    System.out.println("then for " + arg);
                    return arg * 10;
                })
                .when(integer -> {
                    System.out.println("when for " + integer + " " + (integer / 10) % 2);
                    return (integer / 10) % 2 == 0;
                })
                .onFalse(arg -> {
                    System.out.println("case false for " + arg);
                    return arg - (arg * 2);
                })
                .onTrue(arg -> {
                    System.out.println("case true for " + arg);
                    return arg * 2;
                })
                .end()
                .then(arg -> arg + 5);

        Stream.of(1, 2, 3, 4).forEach(integer -> {
            System.out.println(command.execute(integer));
        });
    }
}
