class Reader4 {
    private String fileContent;
    private int filePointer = 0;

    public Reader4(String content) {
        this.fileContent = content;
    }

    public int read4(char[] buf4) {
        int i = 0;
        while (i < 4 && filePointer < fileContent.length()) {
            buf4[i++] = fileContent.charAt(filePointer++);
        }
        return i;
    }
}

public class Reader4Impl extends Reader4 {

    public Reader4Impl(String content) {
        super(content);
    }

    public int read(char[] buf, int n) {
        int totalCharsRead = 0;
        char[] buf4 = new char[4];

        while (totalCharsRead < n) {
            int charsReadThisTIme = read4(buf4);

            for (int i = 0; i < charsReadThisTIme && totalCharsRead < n; i++) {
                buf[totalCharsRead++] = buf4[i];
            }

            if (charsReadThisTIme < 4) {
                break;
            }
        }

        return totalCharsRead;
    }

    public static void main(String[] args) {
        Reader4Impl reader = new Reader4Impl("1234567890");
        char[] buffer = new char[5];
        int charsRead = reader.read(buffer, 5);
    }

}
