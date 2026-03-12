package main.terminal;

import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.tags.PseudoObjectTagBase;
import com.denizenscript.denizencore.tags.TagManager;
import main.terminal.parser.LineParser;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import main.terminal.completions.TabCompletion;
import main.terminal.highlighter.Highlighter;
import main.util.Pair;
import main.terminal.widgets.AutoCloseWidget;
import main.terminal.widgets.AutoDeleteWidget;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class TerminalWindow {

    private static final String INPUT_PROMPT = "> ";
    private static final Set<Pair<Character>> pairs = Set.of(Pair.of('<', '>'), Pair.of('[', ']'), Pair.of('"', '"'), Pair.of('{', '}'));

    private final LineReader reader;
    private final Terminal terminal;
    private final LineParser parser;

    public TerminalWindow() throws IOException {
        this.terminal = TerminalBuilder.builder()
                .system(true)
                .build();
        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new TabCompletion())
                .variable(LineReader.SECONDARY_PROMPT_PATTERN, "%P   ")
                .variable(LineReader.INDENTATION, 2)
                .highlighter(new Highlighter())
                .build();
        this.parser = new LineParser(reader);

        for (Pair<Character> pair : pairs) {
            new AutoCloseWidget(reader, pair.first(), pair.second()).enable();
        }
        new AutoDeleteWidget(reader, pairs).enable();
    }

    public String readLine() {
        return reader.readLine(INPUT_PROMPT);
    }

    public List<Object> readLines() {
        return parser.parse(readLine());
    }

    public void print(String message) {
        System.out.println(message);
    }

    public static class TerminalTagBase extends PseudoObjectTagBase<TerminalTagBase> {

        public static TerminalTagBase instance;

        private final TerminalWindow window;

        public TerminalTagBase(TerminalWindow window) {
            this.window = window;
            instance = this;
            TagManager.registerStaticTagBaseHandler(TerminalTagBase.class, "terminal", (t) -> instance);
        }

        @Override
        public void register() {
            tagProcessor.registerStaticTag(ElementTag.class, "is_dummy", (attribute, object) -> new ElementTag(false));

            tagProcessor.registerStaticTag(ElementTag.class, "width", (attribute, object) -> new ElementTag(object.window.terminal.getWidth()));

            tagProcessor.registerStaticTag(ElementTag.class, "height", (attribute, object) -> new ElementTag(object.window.terminal.getHeight()));
        }
    }
}
