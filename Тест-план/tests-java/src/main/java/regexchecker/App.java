package regexchecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static String pathCLI = findCLI();

    public static String findCLI() {
        String[] names = {"cli.exe", "cli"};
        Path currentPath = Paths.get("").toAbsolutePath();
        while (currentPath != null && currentPath.toFile().exists()) {
            for (String name : names) {
                Path cliPath = currentPath.resolve(name);
                System.out.println(cliPath);
                if (cliPath.toFile().exists()) {
                    return cliPath.toString();
                }
            }
            currentPath = currentPath.getParent();
        }

        System.err.println("Couldn't find 'cli' executable!");

        return null;
    }

    private static Result runCLIImplementation(String path, String[] options, String text) {
        int retcode = -1;
        StringBuilder output = new StringBuilder();
        StringBuilder errors = new StringBuilder();

        ArrayList<String> commands = new ArrayList<>();
        commands.add(pathCLI);
        for (String option : options) {
            commands.add(option);
        }

        Runtime rt = Runtime.getRuntime();
        try {
            Process proc = rt.exec(commands.toArray(String[]::new));

            BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(proc.getOutputStream()));
            BufferedReader stdout = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stderr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            stdin.write(text + "\n");
            stdin.flush();
            stdin.close();

            String s = null;
            while ((s = stdout.readLine()) != null) {
                output.append(s);
            }

            while ((s = stderr.readLine()) != null) {
                errors.append(s);
            }

            stdout.close();
            stderr.close();
            retcode = proc.waitFor();
        } catch (IOException ex) {
            errors.append(ex.getMessage());
        } catch (InterruptedException ex) {
            errors.append(ex.getMessage());
        }

        return new Result(retcode, output.toString(), errors.toString());
    }

    public static Result runCLI(String text, String[] options) {
        return runCLIImplementation(pathCLI, options, text);
    }

    public static Result runCLI(String text, List<String> options) {
        return runCLI(text, options.toArray(String[]::new));
    }

    public static Result runCLI(String text) {
        return runCLI(text, new String[0]);
    }

    public static Result runCLI(String[] lines, String[] options) {
        return runCLI(String.join("\n", lines), options);
    }

    public static Result runCLI(String[] lines, List<String> options) {
        return runCLI(String.join("\n", lines), options);
    }

    public static Result runCLI(String[] lines) {
        return runCLI(String.join("\n", lines));
    }

    public static Result runCLI(Iterable<String> lines, String[] options) {
        return runCLI(String.join("\n", lines), options);
    }

    public static Result runCLI(Iterable<String> lines, List<String> options) {
        return runCLI(String.join("\n", lines), options);
    }

    public static Result runCLI(Iterable<String> lines) {
        return runCLI(String.join("\n", lines));
    }

    public static void main( String[] args )
    {
        ArrayList<String> commands = new ArrayList<>();

        commands.add("quit");

        Result res = App.runCLI(commands);
        if (res.isOK()) {
            System.out.println("Everything seems to be OK");
        } else {
            System.out.println("Something is wrong");
        }
    }
}
