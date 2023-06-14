package regexchecker;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class AppTest 
{
    
    @Test
    public void test()
    {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("quit");

        Result res = App.runCLI(commands);
        System.out.println(res);
        
        assertTrue( res.isOK() );
    }
}
