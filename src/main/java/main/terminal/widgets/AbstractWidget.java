package main.terminal.widgets;

import org.jline.reader.Widget;

import java.util.*;

public abstract class AbstractWidget {

    private final Widget widget;
    private final String name;

    public AbstractWidget(String name, Widget widget) {
        this.name = name;
        this.widget = widget;
    }

    public String getName() {
        return name;
    }

    public Widget getWidget() {
        return widget;
    }

    public abstract void enable();

    public abstract void disable();

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
