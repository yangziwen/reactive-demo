package io.github.yangziwen.reactivedemo.sample;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulerTest {

    public static void main(String[] args) throws Exception {

        Flux.interval(Duration.ofSeconds(1L))
            .doOnNext(SchedulerTest::printValue)

            .publishOn(Schedulers.elastic())
            .doOnNext(SchedulerTest::printValue)

            .publishOn(Schedulers.single())
            .doOnNext(SchedulerTest::printValue)

            .doOnNext(v -> printDivider())

            .subscribeOn(Schedulers.parallel())
            .subscribe();

        TimeUnit.SECONDS.sleep(100L);

    }

    private static void printValue(Long value) {
        String message = value + " on "
                + Thread.currentThread();
        System.out.println(message);
    }

    private static void printDivider() {
        System.out.println(StringUtils.repeat("-", 30));
    }

}
