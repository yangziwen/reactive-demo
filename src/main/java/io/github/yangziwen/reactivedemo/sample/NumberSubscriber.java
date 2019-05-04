package io.github.yangziwen.reactivedemo.sample;

import java.util.concurrent.atomic.AtomicInteger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class NumberSubscriber implements Subscriber<Integer> {

    private AtomicInteger counter = new AtomicInteger();

    private int n = 1;

    private Subscription subscription;

    public NumberSubscriber(int n) {
        this.n = n;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("number publisher is subscribed");
        this.subscription = subscription;
        subscription.request(n);
    }

    @Override
    public void onNext(Integer t) {
        System.out.println("receive number " + t);
        int count = counter.incrementAndGet();
        if (count % n == 0) {
            subscription.request(n);
        }
    }

    @Override
    public void onError(Throwable t) {
        System.err.println("error happened: " + t.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("number flow completed");
    }

}
