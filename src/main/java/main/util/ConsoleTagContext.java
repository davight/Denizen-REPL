package main.util;

import com.denizenscript.denizencore.objects.core.ScriptTag;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import com.denizenscript.denizencore.tags.TagContext;

public class ConsoleTagContext extends TagContext {

    public ConsoleTagContext(ConsoleTagContext copyFrom) {
        super(copyFrom.debug, copyFrom.entry, copyFrom.script);
    }

    public ConsoleTagContext(ScriptEntry entry, boolean debug, ScriptTag script) {
        super(debug, entry, script);
    }

    public ConsoleTagContext(ScriptEntry entry) {
        super(entry);
    }

    public ConsoleTagContext(ScriptContainer container) {
        super(container == null || container.shouldDebug(), null, container == null ? null : new ScriptTag(container));
    }

    @Override
    public ScriptEntryData getScriptEntryData() {
        ConsoleScriptEntryData data = new ConsoleScriptEntryData();
        data.scriptEntry = entry;
        return data;
    }
}
