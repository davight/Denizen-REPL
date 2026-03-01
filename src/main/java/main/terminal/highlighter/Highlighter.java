package main.terminal.highlighter;

import com.denizenscript.denizencore.DenizenCore;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Pattern;

public class Highlighter implements org.jline.reader.Highlighter {

    private static final AttributedStyle COMMAND = AttributedStyle.DEFAULT.foreground(221, 153, 255);
    private static final AttributedStyle STRING = AttributedStyle.DEFAULT.foreground(152, 192, 121);

    private static final AttributedStyle TAG_BRACKET = AttributedStyle.DEFAULT.foreground(229, 192, 123).bold();
    private static final AttributedStyle TAG = AttributedStyle.DEFAULT.foreground(229, 192, 123);

    private static final AttributedStyle INPUT = AttributedStyle.DEFAULT.foreground(152, 195, 121);
    private static final AttributedStyle INPUT_BRACKET = AttributedStyle.DEFAULT.foreground(209, 154, 102).bold();

    private static final AttributedStyle DEFINITION = AttributedStyle.DEFAULT.foreground(224, 108, 117).bold();


    private static final AttributedStyle ERROR = AttributedStyle.DEFAULT.foreground(255, 85, 85);

    @Override
    public AttributedString highlight(LineReader reader, String buffer) {
        AttributedStringBuilder sb = new AttributedStringBuilder();
        int i = 0;
        if (!buffer.startsWith("<")) { // it surely is a command
            int firstSpace = buffer.indexOf(' ');
            if (firstSpace == -1) {
                sb.append(buffer, COMMAND);
                return sb.toAttributedString();
            }
            i = firstSpace;
            String cmd = buffer.substring(0, firstSpace);
            sb.append(cmd, DenizenCore.commandRegistry.list().containsKey(cmd) ? COMMAND : ERROR);
        }

        Deque<AttributedStyle> stack = new ArrayDeque<>();
        boolean lastWasTagOpen = false;

        while (i < buffer.length()) {
            char c = buffer.charAt(i);
            AttributedStyle ctx = stack.isEmpty() ? STRING : stack.peek();

            switch (c) {
                case '<' -> {
                    sb.append("<", TAG_BRACKET);
                    stack.push(TAG);
                    lastWasTagOpen = true;
                }
                case '[' -> {
                    sb.append("[", INPUT_BRACKET);
                    if (ctx == TAG && lastWasTagOpen) { // <[ definition
                        stack.push(DEFINITION);
                    }
                    else if (ctx == TAG) { // tag input
                        stack.push(INPUT);
                    }
                    lastWasTagOpen = false;
                }
                case ']' -> {
                    sb.append("]", INPUT_BRACKET);
                    if (ctx == INPUT || ctx == STRING || ctx == DEFINITION) {
                        stack.pop();
                    }
                    lastWasTagOpen = false;
                }
                case '>' -> {
                    sb.append(">", TAG_BRACKET);
                    if (ctx == TAG) {
                        stack.pop();
                    }
                    lastWasTagOpen = false;
                }
                default -> {
                    sb.append(String.valueOf(c), ctx);
                    lastWasTagOpen = false;
                }
            }
            i++;
        }

        return sb.toAttributedString();
    }

    @Override
    public void setErrorPattern(Pattern pattern) {

    }

    @Override
    public void setErrorIndex(int i) {

    }
}
