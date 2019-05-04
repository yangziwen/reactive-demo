package io.github.yangziwen.reactivedemo.sample;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class NumberTest {

    public static void main(String[] args) {
        // 创建publisher，发布10个数字，并制定发布每批数据的时间间隔为2s
        Publisher<Integer> publisher = new NumberPublisher(10, 2);
        // 创建subscriber，并制定每批请求的数据量为3
        Subscriber<Integer> subscriber = new NumberSubscriber(3);
        // 建立订阅关系
        publisher.subscribe(subscriber);
    }

}
