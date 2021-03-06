package cc.funkemunky.api.listeners;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.event.system.EventManager;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.api.utils.MiscUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.concurrent.Executors;

@Init
public class PluginShutdownListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(PluginDisableEvent event) {
        if(event.getPlugin().getDescription().getDepend().contains("Atlas")) {
            MiscUtils.printToConsole("&c" + event.getPlugin().getName() + " &7is being shutdown. Removing its hooks and listeners...");
            EventManager.unregisterAll(event.getPlugin());
            Atlas.getInstance().getThreadPool().shutdownNow();
            Atlas.getInstance().getSchedular().shutdownNow();
            Atlas.getInstance().setThreadPool(Executors.newFixedThreadPool(4));
            Atlas.getInstance().setSchedular(Executors.newScheduledThreadPool(2));
            MiscUtils.printToConsole("&aCompleted!");
        }
    }
}
