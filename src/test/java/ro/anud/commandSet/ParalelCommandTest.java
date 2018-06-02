package ro.anud.commandSet;

import org.junit.Test;
import ro.anud.commandSet.Command;
import ro.anud.commandSet.CommandPipe;
import ro.anud.commandSet.OptionalFuture;
import ro.anud.commandSet.ParalelCommandBuilder;

import java.util.concurrent.ForkJoinPool;

public class ParalelCommandTest {

    @Test
    public void test() {
        ParalelCommandBuilder paralelCommandBuilder = new ParalelCommandBuilder(ForkJoinPool.commonPool());

        Command<Integer, Integer> sleepCommand = integer -> {
            System.out.println("Sleep command for " + integer + " \t\tthread: " + Thread.currentThread());
            try {
                Thread.sleep(Math.abs(integer) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Sleep command for " + integer + " finished" + " \t\tthread: " + Thread.currentThread());
            return integer;
        };

        Command<Integer, Integer> duplicateCommand = integer -> {
            System.out.println("Duplicate for " + integer + " \t\tthread: " + Thread.currentThread());
            return integer * 2;
        };

        Command<Integer, Integer> divideCommand = integer -> {
            System.out.println("Dividing for " + integer + " \t\tthread: " + Thread.currentThread());
            return integer / 2;
        };


        CommandPipe<Integer, Integer> commandPipe = CommandPipe.create(Integer.class)
                .then(sleepCommand)
                .then(duplicateCommand)
                .then(sleepCommand)
                .then(integer -> {
                    System.out.println("Chain finished for " + integer + " \t\tthread: " + Thread.currentThread());
                    return integer;
                });

        Command<Integer, OptionalFuture<Integer>> paralelCommandPipe = paralelCommandBuilder.queue(commandPipe);


        CommandPipe<Integer, Integer> dividePipe = CommandPipe.create(Integer.class)
                .then(integer -> {
                    System.out.println("Splitting for " + integer);
                    OptionalFuture<Integer> first = paralelCommandPipe.execute(integer);
                    OptionalFuture<Integer> second = paralelCommandPipe.execute(integer - integer * 2);
                    return first.shouldGet().orElse(1000) + second.shouldGet().orElse(1000);
                })
                .then(divideCommand);
        System.out.println("Divide pipe " + dividePipe.execute(5));
//
//        System.out.println("Executing parallel");
//        OptionalFuture<Integer> first = paralelCommandPipe.execute(2);
//        OptionalFuture<Integer> second = paralelCommandPipe.execute(6);
//        OptionalFuture<Integer> third = paralelCommandPipe.execute(9);
//
//        System.out.println("Addition");
//        System.out.println(first.shouldGet().orElse(0)
//                                   + second.shouldGet().orElse(0)
//                                   + third.shouldGet().orElse(0));
//
//        System.out.println("Executing sequentially");
//        int firstSequential = commandPipe.execute(2);
//        int secondSequential = commandPipe.execute(6);
//        int thirdSequential = commandPipe.execute(9);
//
//        System.out.println("Addition");
//        System.out.println(firstSequential + secondSequential + thirdSequential);
//

    }
}
