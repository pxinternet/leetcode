package CircuitBreaker;

public enum CircuitBreakerState {

    //关闭状态
    COLSED,

    //打开状态
    OPEN,

    //半开状态
    HALF_OPEN;

    @Override
    public String toString() {
        return "Breaker state : " + name();
    }



}
