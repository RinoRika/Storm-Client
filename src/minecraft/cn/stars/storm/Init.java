package cn.stars.storm;

import cn.stars.storm.module.ModuleManager;
import org.lwjgl.opengl.Display;

public enum Init {
    INSTANCE;
    public static final String NAME = "Storm";
    public static final String VERSION = "220624";
    public static final String SAYING = "呵呵";

    public ModuleManager module = new ModuleManager();

    public void start() {
        module = new ModuleManager();
        Display.setTitle(NAME + " " + VERSION + " | " + SAYING);
        module.load();
    }
}