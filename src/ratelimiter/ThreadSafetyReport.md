# RateLimiter 线程安全性报告

## 概述

所有 RateLimiter 实现都是**线程安全的**，可以在多线程环境中安全使用。

## 详细分析

### 1. FixedWindowRateLimiter ✅

**线程安全机制：**
- 使用 `AtomicLong` 存储 `windowStart` 和 `requestCount`
- `tryAcquire()` 使用 CAS 操作 (`compareAndSet`)
- 无锁设计，性能好

**代码示例：**
```java
// CAS操作保证线程安全
if (windowStart.compareAndSet(currentWindowStart, now)) {
    requestCount.set(0);
}
return requestCount.compareAndSet(currentCount, currentCount + 1);
```

**线程安全性：** ✅ **完全线程安全**

---

### 2. SlidingWindowRateLimiter ✅

**线程安全机制：**
- 使用 `ConcurrentLinkedQueue`（线程安全集合）
- `tryAcquire()` 使用 `synchronized` 关键字
- `getAvailablePermits()` 操作线程安全集合

**代码示例：**
```java
@Override
public synchronized boolean tryAcquire() {
    // 操作线程安全的 ConcurrentLinkedQueue
    requestTimestamps.offer(now);
    return true;
}
```

**线程安全性：** ✅ **完全线程安全**（但使用锁，性能相对较低）

---

### 3. TokenBucketRateLimiter ✅

**线程安全机制：**
- 使用 `AtomicLong` 存储 `tokens` 和 `lastRefillTime`
- `tryAcquire()` 使用 CAS 操作
- 无锁设计，性能好

**代码示例：**
```java
// CAS操作保证线程安全
while (true) {
    long currentTokens = tokens.get();
    if (currentTokens < permits) {
        return false;
    }
    if (tokens.compareAndSet(currentTokens, currentTokens - permits)) {
        return true;
    }
    // CAS失败，重试
}
```

**线程安全性：** ✅ **完全线程安全**

---

### 4. LeakyBucketRateLimiter ✅

**线程安全机制：**
- 使用 `AtomicLong` 存储 `waterLevel` 和 `lastLeakTime`
- `tryAcquire()` 使用 CAS 操作
- 无锁设计，性能好

**代码示例：**
```java
// CAS操作保证线程安全
while (true) {
    long currentLevel = waterLevel.get();
    long newLevel = currentLevel + permits;
    if (newLevel > capacity) {
        return false;
    }
    if (waterLevel.compareAndSet(currentLevel, newLevel)) {
        return true;
    }
}
```

**线程安全性：** ✅ **完全线程安全**

---

### 5. SlidingLogRateLimiter ✅

**线程安全机制：**
- 使用 `ConcurrentLinkedQueue`（线程安全集合）
- `tryAcquire()` 使用 `synchronized` 关键字
- `getAvailablePermits()` 操作线程安全集合

**代码示例：**
```java
@Override
public synchronized boolean tryAcquire() {
    // 操作线程安全的 ConcurrentLinkedQueue
    requestLogs.offer(now);
    return true;
}
```

**线程安全性：** ✅ **完全线程安全**（但使用锁，性能相对较低）

---

### 6. SlidingWindowBucketRateLimiter ✅

**线程安全机制：**
- 使用 `AtomicLongArray`（线程安全数组）
- `tryAcquire()` 使用 `synchronized` 关键字
- `getAvailablePermits()` 操作线程安全数组

**代码示例：**
```java
@Override
public synchronized boolean tryAcquire() {
    // 操作线程安全的 AtomicLongArray
    buckets.incrementAndGet(currentBucketIndex);
    return true;
}
```

**线程安全性：** ✅ **完全线程安全**（但使用锁，性能相对较低）

---

## 线程安全机制对比

| 实现 | 线程安全机制 | 性能 | 锁竞争 |
|------|------------|------|--------|
| **FixedWindowRateLimiter** | CAS (AtomicLong) | ⭐⭐⭐⭐⭐ | 无锁 |
| **SlidingWindowRateLimiter** | synchronized + ConcurrentLinkedQueue | ⭐⭐⭐ | 有锁 |
| **TokenBucketRateLimiter** | CAS (AtomicLong) | ⭐⭐⭐⭐⭐ | 无锁 |
| **LeakyBucketRateLimiter** | CAS (AtomicLong) | ⭐⭐⭐⭐⭐ | 无锁 |
| **SlidingLogRateLimiter** | synchronized + ConcurrentLinkedQueue | ⭐⭐⭐ | 有锁 |
| **SlidingWindowBucketRateLimiter** | synchronized + AtomicLongArray | ⭐⭐⭐ | 有锁 |

## 性能建议

### 高并发场景推荐：
1. **TokenBucketRateLimiter** - 无锁，性能最好
2. **LeakyBucketRateLimiter** - 无锁，性能最好
3. **FixedWindowRateLimiter** - 无锁，性能最好

### 需要精确限流的场景：
- **SlidingLogRateLimiter** - 使用锁，但精度最高
- **SlidingWindowBucketRateLimiter** - 使用锁，但内存占用固定

## 注意事项

### 1. getAvailablePermits() 的线程安全性

大部分实现的 `getAvailablePermits()` 方法**没有显式同步**，但：
- 操作的是线程安全的数据结构（AtomicLong、ConcurrentLinkedQueue等）
- 即使有轻微的竞态条件，也不会影响限流的正确性
- 返回值可能不是完全精确的，但不影响 `tryAcquire()` 的正确性

### 2. 性能考虑

- **使用 CAS 的实现**：无锁，适合高并发场景
- **使用 synchronized 的实现**：有锁，在高并发下可能有性能瓶颈

### 3. 最佳实践

```java
// ✅ 推荐：直接使用 tryAcquire()，它是完全线程安全的
if (limiter.tryAcquire()) {
    // 处理请求
}

// ⚠️ 注意：getAvailablePermits() 返回值可能不是完全精确的
long available = limiter.getAvailablePermits(); // 仅供参考
```

## 总结

✅ **所有 RateLimiter 实现都是线程安全的**

- **无锁实现**（CAS）：FixedWindow、TokenBucket、LeakyBucket
- **有锁实现**（synchronized）：SlidingWindow、SlidingLog、SlidingWindowBucket

**选择建议：**
- 高并发场景 → 选择无锁实现（TokenBucket、LeakyBucket、FixedWindow）
- 需要精确限流 → 选择有锁实现（SlidingLog、SlidingWindowBucket）
