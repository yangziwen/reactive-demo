package io.github.yangziwen.reactivedemo.sample;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class NumberPublisher implements Publisher<Integer> {

    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(1);

    private AtomicInteger number = new AtomicInteger(0);

    private Subscription subscription;

    private Subscriber<? super Integer> subscriber;

    private final int maxNumber;

    private final long delaySeconds;

    public NumberPublisher(int maxNumber, long delaySeconds) {
        this.delaySeconds = delaySeconds;
        this.maxNumber = maxNumber;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        this.subscription = new NumberSubscription(this, subscriber);
        this.subscriber = subscriber;
        subscriber.onSubscribe(subscription);
    }

    void doRequest(long n) {
        EXECUTOR.schedule(() -> {
            for (int i = 0; i < n; i++) {
                int num = this.number.getAndIncrement();
                if (num == this.maxNumber) {
                    subscriber.onComplete();
                    return;
                }
                subscriber.onNext(num);
            }
        }, delaySeconds, TimeUnit.SECONDS);
    }

}
