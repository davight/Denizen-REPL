package main.util;

import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.YamlConfiguration;

public class ConsoleScriptEntryData extends ScriptEntryData {

    @Override
    public void transferDataFrom(ScriptEntryData scriptEntryData) {
        if (scriptEntryData == null) {
            return;
        }
        super.scriptEntry = scriptEntryData.scriptEntry;
    }

    @Override
    public TagContext getTagContext() {
        return new ConsoleTagContext(super.scriptEntry, scriptEntry == null || scriptEntry.shouldDebug(), scriptEntry == null ? null : scriptEntry.getScript());
    }

    @Override
    public YamlConfiguration save() {
        return new YamlConfiguration();
    }

    @Override
    public void load(YamlConfiguration yamlConfiguration) {
    }
}
