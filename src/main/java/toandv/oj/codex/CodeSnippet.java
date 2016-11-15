package toandv.oj.codex;

import toandv.oj.codex.shell.ShellCommand;
import toandv.oj.codex.shell.ShellCommandChain;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author toandv
 * @github https://github.com/toandv
 * @since 11/15/2016.
 */
public class CodeSnippet {
    public static void main(String[] args) {
        try {
            File file = new File("Test.java");
            FileOutputStream fout = new FileOutputStream(file);

            String hello = "public class Test {\n" +
                    "\n" +
                    "    public static void main(String[] args) {\n" +
                    "        // Prints \"Hello, World\" to the terminal window.\n" +
                    "        System.out.println(\"Hello, World\");\n" +
                    "    }\n" +
                    "\n" +
                    "}";

            fout.write(hello.getBytes());
            fout.flush();
            fout.close();
            ShellCommand compileHello = new ShellCommand("javac Test.java").exec();
            System.out.println(compileHello.getOutput() + "|");
            ShellCommand runHello = new ShellCommand("java Test").exec();
            System.out.println(runHello.getOutput() + "|");
            ShellCommandChain chain = new ShellCommandChain().and(compileHello).and(runHello).exec();
            System.out.println(chain.getOutputs());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
