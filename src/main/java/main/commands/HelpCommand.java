package main.commands;

import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.commands.AbstractCommand;
import com.denizenscript.denizencore.scripts.commands.generator.ArgName;
import com.denizenscript.denizencore.tags.TagManager;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import main.Main;
import main.util.Color;

import java.util.HashMap;
import java.util.Map;

public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        setName("help");
        setSyntax("help <type>");
        setRequiredArguments(1, 1);
        isProcedural = false;
        autoCompile();
    }

    public enum Type { COMMANDS, TAG_BASES }

    private static final HashMap<Type, String> cached = new HashMap<>();

    public static void autoExecute(ScriptEntry scriptEntry,
                                   @ArgName("type") Type type) {
        if (cached.containsKey(type)) {
            Main.terminal.print(cached.get(type));
            return;
        }
        final StringBuilder output = new StringBuilder();
        switch (type) {
            case COMMANDS -> {
                Map<String, AbstractCommand> commands = DenizenCore.commandRegistry.list();
                output.append(Color.LIGHT_PURPLE.getAnsiFormat()).append("Commands: (").append(commands.size()).append(")").append(Color.RESET.getAnsiFormat()).append("\n");
                commands.forEach((name, command) -> {
                    output.append(Color.LIGHT_PURPLE.getAnsiFormat()).append(name);
                    int index = command.syntax.indexOf(" ");
                    String toAppend = index == -1 ? command.syntax : command.syntax.substring(index);
                    output.append(Color.DARK_GRAY.getAnsiFormat()).append(toAppend);
                    output.append(Color.RESET.getAnsiFormat()).append("\n");
                });
            }
            case TAG_BASES -> {
                Map<String, TagManager.TagBaseData> tagBases = TagManager.baseTags;
                output.append(Color.YELLOW.getAnsiFormat()).append("Tag Bases: (").append(tagBases.size()).append(")").append(Color.RESET.getAnsiFormat()).append("\n");
                tagBases.forEach((name, tagBase) -> {
                    output.append(Color.YELLOW.getAnsiFormat()).append('<').append(name).append('>');
                    output.append(Color.RESET.getAnsiFormat()).append(" - ");
                    output.append(Color.GRAY.getAnsiFormat()).append(tagBase.returnType == null ? "Null" : tagBase.returnType.getSimpleName());
                    output.append(Color.RESET.getAnsiFormat()).append("\n");
                });
            }
        }
        cached.put(type, output.toString());
        Main.terminal.print(output.toString());
    }



}
