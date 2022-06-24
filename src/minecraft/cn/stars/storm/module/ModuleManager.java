package cn.stars.storm.module;

import cn.stars.storm.module.modules.movement.Sprint;
import et.event.EventBus;
import et.event.EventHandler;
import et.event.events.EventTick;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules;

    public ModuleManager(){
        modules = new ArrayList<>();
    }

    public void load() {
        modules.add(new Sprint());
        EventBus.getInstance().register(this);
    }

    public void onKey(int key) {
        for (Module module : modules) {
            if (module.getKey() == key) {
                module.Enable();
            }
        }
    }

}
