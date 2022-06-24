package cn.stars.storm.module.modules.movement;

import cn.stars.storm.module.Module;
import cn.stars.storm.module.Category;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
    public static Minecraft mc;
    public Sprint() {
        super("Sprint", Category.MOVEMENT, Keyboard.KEY_Z);
    }

    @Override
    public void onEnable() {
        System.out.println("Sprint Enabled!");
    }

    @Override
    public void onDisable() {
        System.out.println("Sprint Disabled!");
    }
}
