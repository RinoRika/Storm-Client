/*
 * Decompiled with CFR 0_132.
 */
package et.event.events;

import et.event.Event;
import net.minecraft.client.Minecraft;

public class EventPreUpdate
        extends Event {
    private float yaw;
    private float pitch;
    public double y;
    private boolean ground;
    private static final Minecraft mc = Minecraft.getMinecraft();

    public EventPreUpdate(float yaw, float pitch, double y, boolean ground) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.ground = ground;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        mc.thePlayer.renderYawOffset = yaw;
        mc.thePlayer.rotationYawHead = yaw;
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        mc.thePlayer.rotationPitch = pitch;
        this.pitch = pitch;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isOnground() {
        return this.ground;
    }

    public void setOnground(boolean ground) {
        this.ground = ground;
    }

}

