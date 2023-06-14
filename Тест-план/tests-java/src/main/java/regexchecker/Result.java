package regexchecker;

public class Result {
  // error code, returned by the process. Equals to 0 on success
  public final int retcode; 

  // stdout of the process as a string
  public final String output; 

  // stderr of the process as a string
  public final String errors;
  
  public Result(int retcode, String output, String errors) {
    this.retcode = retcode;
    this.output = output;
    this.errors = errors;
  }

  public String toString() {
    return " -- Exit code: " + retcode + "\n" +
           " -- stdout: \n"  + output + "\n" +
           " -- stderr: \n" + errors + "\n";
  }

  public boolean isOK() {
    return retcode == 0;
  }
}
