package main;

import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.objects.ObjectFetcher;
import com.denizenscript.denizencore.tags.TagManager;
import com.denizenscript.denizencore.utilities.CoreConfiguration;
import com.denizenscript.denizencore.utilities.CoreUtilities;
import main.commands.ExitCommand;
import main.commands.HelpCommand;
import main.objects.VectorTag;
import main.util.ConsoleTagContext;
import main.terminal.TerminalWindow;
import main.util.PropertyReader;
import main.util.Ticker;

import java.util.List;

public class Main {

    public static final PropertyReader properties = new PropertyReader();
    public static final String NAME = "Denizen REPL";

    public static String version = properties.getProperty("app.version");

    public static ConsoleQueue queue;
    public static TerminalWindow terminal;
    public static Ticker ticker;

    private static boolean shouldRun = true;

    public static void main(String[] args) throws Exception {
        terminal = new TerminalWindow();
        DenizenCore.init(new REPLImpl());
        ticker = new Ticker(50);
        ticker.start();

        CoreUtilities.noDebugContext = new ConsoleTagContext(null, false, null);
        CoreUtilities.basicContext = new ConsoleTagContext(null, true, null);
        CoreUtilities.errorButNoDebugContext = new ConsoleTagContext(null, false, null);

        CoreConfiguration.queueIdPrefix = false;
        CoreConfiguration.queueIdNumeric = false;
        CoreConfiguration.queueIdWords = false;

        // Tag bases
        new TerminalWindow.TerminalTagBase(terminal);

        // Command registers
        DenizenCore.commandRegistry.registerCommand(ExitCommand.class);
        DenizenCore.commandRegistry.registerCommand(HelpCommand.class);

        // Object registers
        ObjectFetcher.registerWithObjectFetcher(VectorTag.class, VectorTag.tagProcessor);
        TagManager.registerStaticTagBaseHandler(VectorTag.class, VectorTag.class, "vector",  (attribute, object) -> object);
        VectorTag.register();

        queue = new ConsoleQueue(terminal);

        while (shouldRun) {
            List<Object> lines = terminal.readLines();
            if (lines != null) {
                queue.execute(lines);
            }
        }
    }

    public static void exit() {
        shouldRun = false;
    }

}
