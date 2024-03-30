package de.f4bii.api.animation;

import de.f4bii.api.Step;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Animation {
    private final List<Step> steps = new ArrayList<>();
    private BukkitRunnable runnable;
    private int delay;
    private final Plugin plugin;
    private int pos = 0;
    private boolean paused = false;
    private boolean async = false;

    public Animation(Plugin plugin, int delay) {
        this.plugin = plugin;
        this.delay = delay;
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (paused) {
                    return;
                }
                if (pos >= steps.size()) {
                    pos = 0;
                }
                steps.get(pos).run();
                pos++;
            }
        };
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void addEmtpyStep() {
        addStep(() -> {});
    }

    public void addEmtpySteps(int amount) {
        for (int i = 0; i < amount; i++) {
            addEmtpyStep();
        }
    }

    public void start() {
        if (paused) {
            paused = false;
            return;
        }
        if (async) {
            runnable.runTaskTimerAsynchronously(plugin, 1, delay);
        } else {
            runnable.runTaskTimer(plugin, 1, delay);
        }
    }

    public void renew() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (paused) {
                    return;
                }
                if (pos >= steps.size()) {
                    pos = 0;
                }
                steps.get(pos).run();
                pos++;
            }
        };
    }

    public void pause() {
        paused = true;
    }

    public void stop() {
        pos = 0;
        runnable.cancel();
    }
}
