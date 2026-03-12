package main.terminal.parser;

import org.jline.reader.*;

import java.util.ArrayList;
import java.util.List;

public class LineParser {

    public static final int INDENT = 4;

    public LineParser(LineReader reader) {
        reader.getWidgets().put("multi_liner", () -> {
            if (!reader.getBuffer().toString().isBlank()) {
                reader.getBuffer().write('\n');
                return true;
            }
            return false;
        });
        reader.getKeyMaps().get(LineReader.MAIN).bind(new Reference("multi_liner"), "\033\n", "\033[13;2u", "\033\r"); // ESC enter
    }

    // Null if invalid
    public List<Object> parse(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        final String[] linesArr = text.split("\n");
        List<Object> lines = new ArrayList<>(linesArr.length);
        for (String line : linesArr) {
            if (line.isBlank()) { // can ignore empty lines
                continue;
            }
            if (line.startsWith("<")) { // redirect to debug
                line = "debug " + line;
            }
            int indexOfCol =  line.lastIndexOf(':');
            if (indexOfCol != -1) {
                // TODO add proper support for if, while... commands
            }
            lines.add(line);
        }
        return lines.isEmpty() ? null : lines;
    }

}
