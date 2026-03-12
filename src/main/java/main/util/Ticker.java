package main.util;

import com.denizenscript.denizencore.DenizenCore;

public class Ticker {

    private final Object object = new Object();
    private final Thread thread;

    public Ticker(int ms) {
        this.thread = new Thread(() -> {
            while (true) {
                synchronized (object) {
                    DenizenCore.tick(ms);
                }
                try {
                    Thread.sleep(ms);
                } catch (Exception ignored) {
                }
            }
        });
    }

    public void start() {
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
    }
}
