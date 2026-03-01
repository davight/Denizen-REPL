package main;

import com.denizenscript.denizencore.scripts.ScriptBuilder;
import com.denizenscript.denizencore.scripts.queues.core.TimedQueue;
import main.util.ConsoleScriptEntryData;
import main.terminal.TerminalWindow;

import java.util.List;

public class ConsoleQueue {

        final TimedQueue queue;
        final TerminalWindow ter;

        public ConsoleQueue(TerminalWindow ter) {
            this.ter = ter;
            this.queue = new TimedQueue("CONSOLE", 0);
            queue.waitWhenEmpty = true;
            queue.debugOutput = s -> {};
        }

        public void execute(String command) {
            if (command.isBlank() || command.split(" ").length < 1) {
                return;
            }
            if (queue.isPaused() || queue.isDelayed()) {
                ter.print("Sustained queue is currently paused or waiting, adding command to queue for later execution.");
            }
            queue.addEntries(ScriptBuilder.buildScriptEntries(List.of(command), null, new ConsoleScriptEntryData()));
            if (!queue.is_started) {
                queue.start();
            }
            else {
                queue.onStart();
            }
        }

}
