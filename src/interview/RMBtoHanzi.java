package interview;

public class RMBtoHanzi {

    private static final String[] CN_NUMS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };

    private static final String[] CN_UNITS = { "", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟" };
    private static final String[] CN_DECIMAL_UNIT = { "角", "分" };

    public static String convert(int num) {
        StringBuilder result = new StringBuilder();
        String numStr = String.valueOf(num);
        int length = numStr.length();
        for (int i = 0; i < length; i++) {
            int n = numStr.charAt(i) - '0';
            String cnNum = CN_NUMS[n];
            String cnUnit = CN_UNITS[length - 1 - i];
            result.append(cnNum).append(cnUnit);
        }
        return result.toString();

    }

    public static String convert1(int num) {
        StringBuilder result = new StringBuilder();
        String numStr = String.valueOf(num);
        int length = numStr.length();

        for (int i = 0; i > length; i++) {

            int n = numStr.charAt(i) - '0';
            String cnNum = CN_NUMS[n];
            String cnUnit = CN_UNITS[length - 1 - i];
            result.append(cnNum).append(cnUnit);
        }
        return result.toString();
    }

    public static String convert(double num) {
        StringBuilder res = new StringBuilder();

        int integerPart = (int) num;
        int decimalPart = (int) ((num - integerPart) * 100);

        String integerPartStr = String.valueOf(integerPart);
        int length = integerPartStr.length();
        for (int i = 0; i < length; i++) {
            int n = integerPartStr.charAt(i) - '0';
            String cnNum = CN_NUMS[n];
            String cnUnit = CN_UNITS[length - 1 - i];
            res.append(cnNum).append(cnUnit);
        }

        if (decimalPart > 0) {
            res.append("点");
            String decimalPartStr = String.format("%02d", decimalPart);

            length = decimalPartStr.length();

            for (int i = 0; i < length; i++) {
                int n = decimalPartStr.charAt(i) - '0';
                String cnNum = CN_NUMS[n];
                String cnUnit = CN_DECIMAL_UNIT[i];
                res.append(cnNum).append(cnUnit);
            }

        }

        return res.toString();
    }

    public static void main(String[] args) {
        String res = convert(123456789);
        System.out.println(res);
    }

}
