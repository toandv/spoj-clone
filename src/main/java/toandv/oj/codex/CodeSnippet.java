package toandv.oj.codex;

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
            Process p = Runtime.getRuntime().exec("javac Test.java");
            p.waitFor();
            Process p1 = Runtime.getRuntime().exec("java Test");
            p1.waitFor();
            InputStream is = p1.getInputStream();
            int b;
            while ((b = is.read()) != -1) {
                System.out.print((char) b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
