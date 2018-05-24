package ro.anud.globalcooldown.processor.command;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class ParalelCommand<T, R> implements Command<T, OptionalFuture<R>> {

    private final ExecutorService executorService;
    private final Command<T, R> command;

    public ParalelCommand(ExecutorService executorService, Command<T, R> command) {
        this.executorService = Objects.requireNonNull(executorService, "executorService must not be null");
        this.command = Objects.requireNonNull(command, "command must not be null");
    }

    @Override
    public OptionalFuture<R> execute(T arg) {
        return new OptionalFuture<>(executorService.submit(() -> command.execute(arg)));
    }
}
