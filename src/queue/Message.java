package queue;

import javax.sound.midi.SysexMessage;

public class Message {

    private String content;
    private long createTime;
    private long ttl;

    public Message(String content, long ttl) {
        this.content = content;
        this.createTime = System.currentTimeMillis();
        this.ttl = ttl;
    }

    public String getContent() {
        return content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getTTL() {
        return ttl;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > createTime + ttl;
    }

}
