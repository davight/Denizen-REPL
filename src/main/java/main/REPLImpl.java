package main;

import com.denizenscript.denizencore.DenizenImplementation;
import com.denizenscript.denizencore.flags.FlaggableObject;
import com.denizenscript.denizencore.objects.Argument;
import com.denizenscript.denizencore.objects.ObjectFetcher;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.VectorObject;
import com.denizenscript.denizencore.scripts.ScriptEntry;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;
import com.denizenscript.denizencore.scripts.queues.ScriptQueue;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.DefinitionProvider;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import main.objects.VectorTag;
import main.util.Color;
import main.util.ConsoleScriptEntryData;
import main.util.ConsoleTagContext;

import java.io.File;

public class REPLImpl implements DenizenImplementation {

    @Override
    public File getScriptFolder() {
        return null;
    }

    @Override
    public String getImplementationVersion() {
        return Main.version;
    }

    @Override
    public String getImplementationName() {
        return Main.NAME;
    }

    @Override
    public void preScriptReload() {

    }

    @Override
    public void onScriptReload() {

    }

    @Override
    public ScriptEntryData getEmptyScriptEntryData() {
        return new ConsoleScriptEntryData();
    }

    @Override
    public boolean handleCustomArgs(ScriptEntry scriptEntry, Argument argument) {
        return false;
    }

    @Override
    public void refreshScriptContainers() {

    }

    @Override
    public TagContext getTagContext(ScriptContainer scriptContainer) {
        return new ConsoleTagContext(scriptContainer);
    }

    @Override
    public TagContext getTagContext(ScriptEntry scriptEntry) {
        return new ConsoleTagContext(scriptEntry, true, null);
    }

    @Override
    public String cleanseLogString(String s) {
        return "";
    }

    @Override
    public void preTagExecute() {

    }

    @Override
    public void postTagExecute() {

    }

    @Override
    public boolean needsHandleArgPrefix(String s) {
        return false;
    }

    @Override
    public boolean canWriteToFile(File file) {
        return false;
    }

    @Override
    public String getRandomColor() {
        return Color.getRandomColor().getAnsiFormat();
    }

    @Override
    public boolean canReadFile(File file) {
        return false;
    }

    @Override
    public File getDataFolder() {
        Debug.log("getDataFolder");
        return null;
    }

    @Override
    public String queueHeaderInfo(ScriptEntry scriptEntry) {
        return "";
    }

    @Override
    public FlaggableObject simpleWordToFlaggable(String word, ScriptEntry scriptEntry) {
        ObjectTag obj = ObjectFetcher.pickObjectFor(word, scriptEntry.context);
        if (obj instanceof FlaggableObject flaggableObject) {
            return flaggableObject;
        }
        return null;
    }

    @Override
    public ObjectTag getSpecialDef(String s, ScriptQueue scriptQueue) {
        return null;
    }

    @Override
    public boolean setSpecialDef(String s, ScriptQueue scriptQueue, ObjectTag objectTag) {
        return false;
    }

    @Override
    public void addExtraErrorHeaders(StringBuilder stringBuilder, ScriptEntry scriptEntry) {

    }

    @Override
    public String applyDebugColors(String s) {
        return Color.parseDenizenFormat(s);
    }

    @Override
    public void doFinalDebugOutput(String s) {
        if (!s.contains("line 1")) {
            Main.terminal.print(s);
        }
    }

    @Override
    public void addFormatScriptDefinitions(DefinitionProvider definitionProvider, TagContext tagContext) {

    }

    @Override
    public String stripColor(String s) {
        return Color.resetString(s);
    }

    @Override
    public void reloadConfig() {

    }

    @Override
    public void reloadSaves() {

    }

    @Override
    public VectorObject getVector(double v, double v1, double v2) {
        return new VectorTag(v, v1, v2);
    }

    @Override
    public VectorObject vectorize(ObjectTag objectTag, TagContext tagContext) {
        return objectTag instanceof VectorTag vector ? vector : null;
    }
}
