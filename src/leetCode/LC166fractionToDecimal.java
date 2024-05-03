package leetCode;

import java.util.HashMap;
import java.util.Map;

public class LC166fractionToDecimal {

    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) return "0";

        StringBuilder res = new StringBuilder();
        
        //判断正负
        if(numerator < 0 ^ denominator <0) {
            res.append("-");
        }
        
        Long dividend = Math.abs(Long.valueOf(numerator)); 
        Long divisor = Math.abs(Long.valueOf(denominator));

        //添加整数部分
        res.append(dividend / divisor);
        long remainder = dividend % divisor;

        if (remainder == 0) {
            //没有小数，返回0即可
            return res.toString();
        }

        res.append('.');
        Map<Long, Integer> remainderSet = new HashMap<>();

        while(remainder!= 0) {
            if (remainderSet.contains(remainder)) {
                res.insert(, b)
            }
            remainderSet.add(remainder);
            remainder *= 10;
            res.append(String.valueOf(remainder / divisor));
            remainder %= divisor;
        }
        return res.toString();

    }

    
}
