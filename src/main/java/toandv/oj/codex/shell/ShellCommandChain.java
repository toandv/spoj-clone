package toandv.oj.codex.shell;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author toandv
 * @github https://github.com/toandv
 * @since 11/16/2016.
 */
public class ShellCommandChain {

    protected List<ShellCommand> chain = new LinkedList<>();

    protected List<String> outputs = Collections.emptyList();

    public ShellCommandChain and(ShellCommand c) {
        chain.add(c);
        return this;
    }

    public ShellCommandChain exec() {
        chain.forEach(ShellCommand::exec);
        outputs = chain.stream().map(ShellCommand::getOutput).collect(Collectors.toList());
        return this;
    }

    public List<String> getOutputs() {
        return Collections.unmodifiableList(outputs);
    }
}
