package main.terminal.completions;

import com.denizenscript.denizencore.DenizenCore;
import com.denizenscript.denizencore.tags.TagManager;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

public class TabCompletion implements Completer {

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> list) {
        // Command suggestor
        if (line.wordIndex() == 0) {
            String probablyCommand = line.word();
            if (!probablyCommand.startsWith("<")) {
                for (String cmd : DenizenCore.commandRegistry.list().keySet()) {
                    if (cmd.startsWith(probablyCommand)) {
                        list.add(new Candidate(cmd, cmd, "Commands", null, null, null, true));
                    }
                }
            }
        }

        // Tagbase suggestor
        if (line.word().startsWith("<")) {
            String t = line.word().substring(1, line.word().length() - 1);
            for (String tagBase : TagManager.baseTags.keySet()) {
                if (tagBase.startsWith(t)) {
                    list.add(new Candidate("<" + tagBase + "[]>", tagBase, "TagBase", null, null, null, true));
                }
            }
        }
    }
}
