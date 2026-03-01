package main;

import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.objects.ObjectFetcher;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import main.commands.ExitCommand;
import main.commands.HelpCommand;
import main.objects.VectorTag;
import main.util.ConsoleTagContext;
import main.terminal.TerminalWindow;

public class Main {

    public static final String VERSION = "1.0.0";
    public static final String NAME = "Denizen REPL";

    public static ConsoleQueue queue;
    public static TerminalWindow terminal;

    private static boolean shouldRun = true;

    public static void main(String[] args) throws Exception {
        terminal = new TerminalWindow();
        DenizenCore.init(new REPLImpl());

        CoreUtilities.noDebugContext = new ConsoleTagContext(null, false, null);
        CoreUtilities.basicContext = new ConsoleTagContext(null, true, null);
        CoreUtilities.errorButNoDebugContext = new ConsoleTagContext(null, false, null);

        // Command registers
        DenizenCore.commandRegistry.registerCommand(ExitCommand.class);
        DenizenCore.commandRegistry.registerCommand(HelpCommand.class);

        // Object registers
        ObjectFetcher.registerWithObjectFetcher(VectorTag.class, VectorTag.tagProcessor);

        queue = new ConsoleQueue(terminal);

        while (shouldRun) {
            String input = terminal.readLine();

            if (input.startsWith("<")) { // auto redirect to debug
                input = "debug " + input;
            }

            queue.execute(input);
        }
    }

    public static void exit() {
        shouldRun = false;
    }

}
