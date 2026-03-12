package main.terminal.widgets;

import org.jline.keymap.KeyMap;
import org.jline.reader.Binding;
import org.jline.reader.Buffer;
import org.jline.reader.LineReader;
import org.jline.reader.Reference;
import main.util.Pair;

import java.util.Set;

public class AutoDeleteWidget extends AbstractWidget {

    private final LineReader reader;

    public AutoDeleteWidget(LineReader reader, Set<Pair<Character>> pairs) {
        super("auto-delete-" + pairs.toString(), () -> {
            Buffer buffer = reader.getBuffer();
            char prev = (char) buffer.prevChar();
            for (Pair<Character> pair : pairs) {
                if (prev == pair.first()) {
                    buffer.backspace(1);
                    buffer.delete(1);
                    return true;
                }
            }
            reader.callWidget(LineReader.BACKWARD_DELETE_CHAR);
            return true;
        });
        this.reader = reader;
    }

    @Override
    public void enable() {
        reader.getWidgets().put(super.getName(), super.getWidget());
        KeyMap<Binding> main = reader.getKeyMaps().get(LineReader.MAIN);
        main.bind(new Reference(super.getName()), "\177", "\010"); // fallback delete lol
    }

    @Override
    public void disable() {
        reader.getWidgets().remove(super.getName());
        KeyMap<Binding> main = reader.getKeyMaps().get(LineReader.MAIN);
        main.unbind(super.getName());
    }
}
