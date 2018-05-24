package ro.anud.globalcooldown.processor.command;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class ParalelCommandBuilder {

    private final ExecutorService executorService;

    public ParalelCommandBuilder(ExecutorService executorService) {
        this.executorService = Objects.requireNonNull(executorService, "executorService must not be null");
    }

    public <T, R> ParalelCommand<T, R> queue(Command<T, R> command) {
        return new ParalelCommand<>(executorService, command);
    }
}
