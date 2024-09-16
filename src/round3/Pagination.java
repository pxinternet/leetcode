package round3;

import java.util.ArrayList;
import java.util.List;


public class Pagination {

    public static List<String> pagination(String text, int count) {
        String[] words = text.split(" ");

        List<String> pages = new ArrayList<>();

        StringBuilder currentPage = new StringBuilder();

        for (String word : words) {
            if (currentPage.length() == 0 || currentPage.length() + word.length() + 1 <= count) {
                if (currentPage.length() > 0) {
                    currentPage.append(" ");
                }
                currentPage.append(word);
            } else {
                pages.add(currentPage.toString());
                currentPage = new StringBuilder();
            }
        }

        if (currentPage.length() > 0) {
            pages.add(currentPage.toString());
        }
        return pages;
    }

}
