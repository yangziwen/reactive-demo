package io.github.yangziwen.reactivedemo.sample;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class NumberSubscription implements Subscription {

    private NumberPublisher publisher;

    private Subscriber<? super Integer> subscriber;

    public NumberSubscription(NumberPublisher publisher, Subscriber<? super Integer> subscriber) {
        this.publisher = publisher;
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        publisher.doRequest(n);
    }

    @Override
    public void cancel() {
        subscriber.onComplete();
    }

}
