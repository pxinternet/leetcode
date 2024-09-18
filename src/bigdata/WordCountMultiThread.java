package bigdata;

import com.sun.jdi.CharType;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import memoryfile.File;

public class WordCountMultiThread {

    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        String filePath = "path/file.txt";

        Map<String, Integer> wordCount = countWordsInFile(filePath);

        wordCount.forEach((word, count) -> System.out.println(word + " : " + count));
    }

    public static Map<String, Integer> countWordsInFile(String filePath) throws Exception {

        File file = new File(filePath);

        long fileLength = file.length();
        long chunkSize = fileLength / THREAD_COUNT;

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        List<Future<Map<String, Integer>>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            long start = i * chunkSize;
            long end = (i == THREAD_COUNT - 1) ? fileLength : (i + 1) * chunkSize;

            futures.add(executor.submit(new WordCountTask(filePath, start, end)));
        }

        Map<String, Integer> wordCount = new ConcurrentHashMap<>();
        for (Future<Map<String, Integer>> future : futures) {
            Map<String, Integer> partialCount = future.get();
            mergeWordCounts(wordCount, partialCount);
        }

        return wordCount;

    }

    private static void mergeWordCounts(Map<String, Integer> totalCount, Map<String, Integer> partialCount) {
        for (Map.Entry<String, Integer> entry : partialCount.entrySet()) {
            totalCount.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

}

class WordCountTask implements Callable<Map<String, Integer>> {

    private String filePath;
    long start;
    long end;

    public WordCountTask(String filePath, long start, long end) {
        this.filePath = filePath;
        this.start = start;
        this.end = end;
    }

    @Override
    public Map<String, Integer> call() throws Exception {
        Map<String, Integer> wordCount = new HashMap<>();

        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            FileChannel channel = raf.getChannel();

            if (start != 0) {
                start = adjustPosition(channel, start, true);
            }

            if (end != channel.size()) {
                end = adjustPosition(channel, end, false);
            }

            long size = end - start;
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, start, size);
            String text = StandardCharsets.UTF_8.decode(buffer).toString();

            countWordsInText(text, wordCount);
        }
        return wordCount;
    }


    private long adjustPosition(FileChannel channel, long position, boolean forward) throws Exception {
        if (position <= 0 || position >= channel.size()) {
            return position;
        }
        ByteBuffer bb = ByteBuffer.allocate(1);
        while (true) {
            channel.read(bb, position);
            bb.flip();
            char c = (char) bb.get();

            bb.clear();

            if (Character.isWhitespace(c)) {
                break;
            }

            position = forward ? position + 1: position - 1;
            if (position <= 0 || position >= channel.size()) {
                break;
            }
        }
        return position;
    }

    private void countWordsInText(String text, Map<String, Integer> wordCount) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            word = word.trim();
            if (!word.isEmpty()) {
                wordCount.merge(word, 1, Integer::sum);
            }
        }
    }

}
