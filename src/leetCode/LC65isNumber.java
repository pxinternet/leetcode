package leetCode;

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

        for (char ch : s.toCharArray()) {
            CharType type = toCharType(ch);
            if (transfer[state.ordinal()][type.ordinal()] == -1) {
                return false;
            } else {
                state = State.values()[transfer[state.ordinal()][type.ordinal()]];
            }
        }
        //这个解法是个状态机
        return state == State.INTEGER || state == State.POINT || state == State.FRACTION || state == State.EXP_NUMBER || state == State.END;
    }
}
