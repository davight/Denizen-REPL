package main.terminal;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import main.terminal.completions.TabCompletion;
import main.terminal.highlighter.Highlighter;
import main.util.Color;
import main.util.Pair;
import main.terminal.widgets.AutoCloseWidget;
import main.terminal.widgets.AutoDeleteWidget;

import java.io.IOException;
import java.util.Set;

public class TerminalWindow {

    private static final String INPUT_PROMPT = Color.parseDenizenFormat("<LG>> <W>");
    private static final Set<Pair<Character, Character>> pairs = Set.of(Pair.of('<', '>'), Pair.of('[', ']'), Pair.of('"', '"'), Pair.of('{', '}'));

    private final LineReader reader;

    public TerminalWindow() throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        this.reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new TabCompletion())
                .highlighter(new Highlighter())
                .build();

        for (Pair<Character, Character> pair : pairs) {
            new AutoCloseWidget(reader, pair.first(), pair.second()).enable();
        }
        new AutoDeleteWidget(reader, pairs).enable();
    }

    public String readLine() {
        return reader.readLine(INPUT_PROMPT);
    }

    public void print(String message) {
        System.out.println(message);
    }
}
