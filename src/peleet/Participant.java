package peleet;

/**
 * 参与者类，表示参与车牌摇号的人
 */
public class Participant {
    private final String name;
    private final int score;

    public Participant(String name, int score) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }
        if (score < 0) {
            throw new IllegalArgumentException("积分不能为负数");
        }
        this.name = name.trim();
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Participant{name='" + name + "', score=" + score + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return score == that.score && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 31 + score;
    }
}
