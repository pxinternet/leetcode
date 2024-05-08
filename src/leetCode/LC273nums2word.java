package leetCode;

public class LC273nums2word {

    private final String[] LESS_THAN_20 = new String[]{
        "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
        "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
        "Eighteen", "Nineteen"
    };

    private final String[] TENS = new String[] {
        "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    private final String[] THOUSANDS = new String[] {
        "", "Thousand", "Million", "Billion"
    };
    public String numberToWords(int num) {
        //表映射？
        if (num == 0) return "Zero";
        int i = 0;

        StringBuilder res = new StringBuilder();

    }

}
