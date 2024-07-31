package memoryfile;

public class File {

    private final String name;
    private Content content;

    public File(String name) {
        this.name = name;
        this.content = new Content("");
    }

    public String getName() {
        return name;
    }

    public String readContent() {
        return content.getContent();
    }

    public void writeContent(String content) {
        this.content.append(content);
    }

    public void clearContent() {
        this.content.clear();
    }

    //重写 equal 和hasc:\Users\lvhh8\AppData\Local\Temp\SGPicFaceTpBq\14592\02D7BB31.png


}
