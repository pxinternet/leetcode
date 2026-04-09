package leetCode;

/**
 * LC65 - 有效数字
 *
 * 题目概要：判断字符串是否可以解释为十进制数字（支持科学计数法、正负号、小数点）。
 *
 * 解法说明：使用有限状态机（DFA）。状态转移表根据当前状态和字符类型决定下一状态，
 * 非法转移立即返回 false；最终只有落在「有效数字」状态才返回 true。
 *
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * 有效终点：INTEGER、POINT、FRACTION、EXP_NUMBER（以及由 POINT 到达的隐含 END）。
 */
public class LC65isNumber {

    private enum State {
        START,
        INT_SIGN,
        INTEGER,
        POINT,
        POINT_WITHOUT_INT,
        FRACTION,
        EXP,
        EXP_SIGN,
        EXP_NUMBER,
        END
    }

    private enum CharType {
        NUMBER,
        EXP,
        POINT,
        SIGN,
        ILLEGAL
    }

    private CharType toCharType(char ch) {
        if (ch >= '0' && ch <= '9') {
            return CharType.NUMBER;
        } else if (ch == 'e' || ch == 'E') {
            return CharType.EXP;
        } else if (ch == '.') {
            return CharType.POINT;
        } else if (ch == '+' || ch == '-') {
            return CharType.SIGN;
        } else {
            return CharType.ILLEGAL;
        }

    }

    public boolean isNumber(String s) {
        State state = State.START;

        int[][] transfer = new int[][] {
                {State.INT_SIGN.ordinal(), State.INTEGER.ordinal(), State.POINT_WITHOUT_INT.ordinal(), -1},
                {-1, State.INTEGER.ordinal(), State.POINT_WITHOUT_INT.ordinal(), -1},
                {-1, State.INTEGER.ordinal(), State.POINT.ordinal(), State.EXP.ordinal()},
                {-1, State.FRACTION.ordinal(), -1, State.EXP.ordinal()},
                {-1, State.FRACTION.ordinal(), -1, -1},
                {-1, State.FRACTION.ordinal(), -1, State.EXP.ordinal()},
                {State.EXP_SIGN.ordinal(), State.EXP_NUMBER.ordinal(), -1, -1},
                {-1, State.EXP_NUMBER.ordinal(), -1, -1},
                {-1, State.EXP_NUMBER.ordinal(), -1, -1}
        };

        // 逐字符按转移表推进状态；-1 表示非法转移
        for (char ch : s.toCharArray()) {
            CharType type = toCharType(ch);
            if (transfer[state.ordinal()][type.ordinal()] == -1) {
                return false;
            }
            state = State.values()[transfer[state.ordinal()][type.ordinal()]];
        }

        // 只有落在「已形成有效数字」的状态才为 true
        // POINT 表示以 "." 结尾（如 "3."），题目规定为有效
        return state == State.INTEGER || state == State.POINT || state == State.FRACTION
                || state == State.EXP_NUMBER || state == State.END;
    }
}
