package io.github.yangziwen.reactivedemo.sample;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

public class OperatorTest {

    public static void main(String[] args) throws InterruptedException {
        String[] words = {
                "the", "quick", "brown",
                "fox", "jumped", "over",
                "the", "lazy", "dog"
        };
        // 每秒输出一个单词，统计每个字母出现的次数
        Flux.interval(Duration.ofSeconds(1L))
                .zipWith(Flux.fromArray(words))
                .take(words.length)
                .map(t -> t.getT2())
                .doOnNext(System.out::println)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .groupBy(c -> c)
                .map(flux -> flux.reduceWith(
                        () -> Tuples.of("", 0),
                        (t, str) -> Tuples.of(str, t.getT2() + 1))
                )
                .flatMap(mono -> mono.as(Flux::from))
                .map(t -> String.format("%s: %d次", t.getT1(), t.getT2()))
                .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(100L);

    }

}
