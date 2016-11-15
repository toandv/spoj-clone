package toandv.oj.codex.shell;

/**
 * @author toandv
 * @github https://github.com/toandv
 * @since 11/15/2016.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ShellCommand {

    protected final String command;

    protected final List<String> args;

    protected String output;

    protected Process process;

    protected final long timeout;

    protected boolean waitForFinish;

    protected File workingDir;

    public ShellCommand(String command, List<String> args) {
        this(command, args, -1, true);
    }

    public ShellCommand(String command, List<String> args, long timeout) {
        this(command, args, timeout, false);
    }

    public ShellCommand(String command) {
        this(command, Collections.emptyList(), -1, true);
    }

    public ShellCommand(String command, long timeout) {
        this(command, Collections.emptyList(), timeout, false);
    }

    public ShellCommand(String command, List<String> args, long timeout, boolean waitForFinish) {
        this.command = command;
        this.args = args;
        this.timeout = timeout;
        this.waitForFinish = waitForFinish;
    }

    public void exec() {
        try {
            process = Runtime.getRuntime().exec(command, args.toArray(new String[args.size()]));
            if (timeout > 0) {
                waitFor(timeout);
            } else if (waitForFinish) {
                waitFor();
            }

        } catch (IOException e) {
            throw new CommandRuntimeException(e);
        }
    }

    public String getOutput() {
        if (output == null) {
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                output = buffer.toString();
            } catch (IOException e) {
                throw new CommandRuntimeException(e);
            }
        }
        return output;
    }

    protected void waitFor() {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new CommandRuntimeException(e);
        }
    }

    protected void waitFor(long millis) {
        try {
            process.waitFor(millis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new CommandRuntimeException(e);
        }
    }

    public void changeWorkingDir(String path) {
        this.workingDir = new File(path);
    }

    public void changeWorkingDir(File dir) {
        this.workingDir = dir;
    }
}
