package main.terminal.widgets;

import org.jline.keymap.KeyMap;
import org.jline.reader.*;

public class AutoCloseWidget extends AbstractWidget {

    private final LineReader reader;
    private final char start;

    public AutoCloseWidget(LineReader reader, char start, char end) {
        super("auto-close-" + start + "-" + end, () -> {
            Buffer buffer = reader.getBuffer();
            buffer.write(start);
            buffer.write(end);
            buffer.move(-1);
            return true;
        });
        this.reader = reader;
        this.start = start;
    }

    public void enable() {
        reader.getWidgets().put(super.getName(), super.getWidget());
        KeyMap<Binding> main = reader.getKeyMaps().get(LineReader.MAIN);
        main.bind(new Reference(super.getName()), String.valueOf(start));
    }

    public void disable() {
        reader.getWidgets().remove(super.getName());
        KeyMap<Binding> main = reader.getKeyMaps().get(LineReader.MAIN);
        main.unbind(super.getName());
    }
}
