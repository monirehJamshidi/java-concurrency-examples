# Java Concurrency in Practice

## 📌 Overview

This project demonstrates fundamental Java concurrency concepts through simple, practical examples.

The goal is to understand how multiple threads interact with shared state and how Java provides different mechanisms to make concurrent code safer and more predictable.

The project focuses on:

* Race Conditions
* Thread Safety
* `synchronized`
* `AtomicInteger`
* `volatile`
* `CountDownLatch`
* JUnit 5 concurrency testing
* Java Memory Model (JMM)
* `happens-before`

---

## 🧠 Concepts Covered

### 1. Race Condition

A Race Condition can occur when multiple threads access shared mutable state concurrently and the result depends on the timing or ordering of their operations.

Example:

```java
private int count = 0;

public void increment() {
    count++;
}
```

Multiple threads updating the same `count` can lead to lost updates.

---

### 2. synchronized

`synchronized` provides mutual exclusion for code protected by the same monitor and also provides memory visibility guarantees.

Example:

```java
public synchronized void increment() {
    count++;
}
```

Only one thread at a time can execute the synchronized method for the same object.

---

### 3. AtomicInteger

`AtomicInteger` provides atomic operations on an `int` value without requiring explicit locking in the application code.

Example:

```java
private final AtomicInteger count =
        new AtomicInteger(0);

public void increment() {
    count.incrementAndGet();
}
```

Useful for simple atomic operations such as counters.

---

### 4. volatile

`volatile` is mainly used to provide visibility of changes to a shared variable between threads.

Example:

```java
private volatile boolean running = true;
```

It does **not** make compound operations such as:

```java
count++;
```

atomic.

---

### 5. CountDownLatch

`CountDownLatch` is used for thread coordination.

It allows one or more threads to wait until a counter reaches zero.

This project uses it in tests to coordinate the start and completion of worker threads.

```java
CountDownLatch startLatch =
        new CountDownLatch(1);

CountDownLatch doneLatch =
        new CountDownLatch(numberOfThreads);
```

---

## 🔬 Comparing the Approaches

| Mechanism        | Main Purpose                  | Explicit Lock | Example                 |
| ---------------- | ----------------------------- | ------------: | ----------------------- |
| Plain `int`      | Mutable state                 |             ❌ | Race Condition possible |
| `synchronized`   | Mutual exclusion + visibility |             ✅ | Counter                 |
| `AtomicInteger`  | Atomic operations             |             ❌ | Counter                 |
| `volatile`       | Visibility                    |             ❌ | Stop flag               |
| `CountDownLatch` | Thread coordination           |             — | Tests                   |

> **Important:** `volatile`, `synchronized`, and `AtomicInteger` solve different concurrency problems. They are not interchangeable.

---

## 🧪 Testing

The project uses **JUnit 5** to verify concurrent behavior.

The tests use multiple threads and `CountDownLatch` to coordinate their execution.

For example:

```text
10 threads
    ×
100,000 increments
    =
1,000,000 expected operations
```

Both `SynchronizedCounter` and `AtomicIntegerCounter` should produce:

```text
Expected: 1000000
Actual:   1000000
```

The `RaceConditionExample` is intentionally unsafe and demonstrates nondeterministic behavior. Its result may be less than the expected value, although a particular run can also produce the expected value.

---

## 📂 Project Structure

```text
src
├── main
│   └── java
│       └── com.monireh.concurrency
│           ├── racecondition
│           │   └── RaceConditionExample.java
│           │
│           ├── synchronizedexample
│           │   ├── SynchronizedCounter.java
│           │   └── SynchronizedExample.java
│           │
│           ├── atomic
│           │   ├── AtomicIntegerCounter.java
│           │   └── AtomicIntegerExample.java
│           │
│           └── volatileexample
│               └── VolatileExample.java
│
└── test
    └── java
        └── com.monireh.concurrency
            ├── racecondition
            │   └── RaceConditionExampleTest.java
            │
            ├── synchronizedexample
            │   └── SynchronizedCounterTest.java
            │
            ├── atomic
            │   └── AtomicIntegerCounterTest.java
            │
            ├── volatileexample
            │   └── VolatileExampleTest.java
            │
            └── support
                └── ConcurrencyTestHelper.java
```

---

## ▶️ How to Run

### Run the tests with Maven

```bash
mvn test
```

### Run a specific test

```bash
mvn -Dtest=SynchronizedCounterTest test
```

or:

```bash
mvn -Dtest=AtomicIntegerCounterTest test
```

You can also run individual examples directly from IntelliJ IDEA.

---

## 📚 Learning Path

This project was built step by step to connect the concepts:

```text
Stateful vs Stateless
        ↓
Thread Safety
        ↓
Race Condition
        ↓
synchronized
        ↓
AtomicInteger
        ↓
volatile
        ↓
Java Memory Model
        ↓
happens-before
```

---

## 🎯 Key Takeaways

* Avoid unnecessary shared mutable state.
* `synchronized` provides mutual exclusion and visibility.
* `AtomicInteger` is useful for atomic operations on a single integer value.
* `volatile` provides visibility but does not make compound operations atomic.
* `CountDownLatch` is useful for coordinating threads in concurrent tests.
* Concurrency bugs can be nondeterministic and difficult to reproduce.

---

## 🚀 What's Next?

This repository focuses on the fundamentals of Java concurrency.

Next topics in my Java learning journey:

```text
Modern Java
→ var
→ Text Blocks
→ Records
→ Sealed Classes
→ Pattern Matching
→ equals() & hashCode()
→ Collections
→ HashMap
→ ORM / JPA / Hibernate
```

---

## 👩‍💻 Author

**Monireh Jamshidi**

Java | Spring Boot | REST APIs | Microservices

This repository is part of my journey to deepen my knowledge of Java concurrency and prepare for professional backend development.