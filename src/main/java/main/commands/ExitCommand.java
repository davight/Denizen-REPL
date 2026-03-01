package main.commands;

import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import main.Main;

public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        setName("exit");
        setRequiredArguments(0, 0);
        isProcedural = false;
        autoCompile();
    }

    public static void autoExecute(ScriptEntry scriptEntry) {
        Main.exit();
    }

}
