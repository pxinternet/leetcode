package memoryfile;

public class Content {

    private final StringBuilder content;

    public Content(String content) {
        this.content = new StringBuilder(content);
    }

    public void append(String additionalContent) {
        this.content.append(additionalContent);
    }

    public String getContent() {
        return content.toString();
    }

    public void clear() {
        content.setLength(0);
    }


    //重写equal 和 hash
}
