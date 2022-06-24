package et.event;

public abstract class Event {
    private boolean cancelled;
    public byte type;

    public boolean isCancel() {
        return this.cancelled;
    }

    public void cancelEvent(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}

