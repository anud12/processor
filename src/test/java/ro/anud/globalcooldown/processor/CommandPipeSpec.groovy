package ro.anud.globalcooldown.processor

import ro.anud.globalcooldown.processor.command.CommandPipe
import spock.lang.Specification

import java.util.function.Function

class CommandPipeSpec extends Specification {

    def commandPipe = CommandPipe.create(Integer.class)

    def "then"() {
        given:
        def firstFunction = Mock(Function)
        def secondFunction = Mock(Function)
        def initialValue = 0
        when:
        def result = commandPipe
                .then(firstFunction)
                .then(secondFunction)
                .execute(initialValue)

        then:
        result == 1
    }
}
