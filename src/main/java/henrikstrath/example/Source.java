package henrikstrath.example;

import rx.Observable;
import rx.Subscriber;

import java.util.Set;

import com.google.common.collect.Sets;

public class Source<T> {

    protected final Set<Subscriber<? super T>> subscribers = Sets.newConcurrentHashSet();
    private final Observable<T> observable;

    public Source() {
        observable = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                subscribers.add(subscriber);
            }
        });
    }

    public Observable<T> asObservable() {
        return observable;
    }

    public void push(T next) {
        subscribers
                .parallelStream()
                .forEach(s -> s.onNext(next));
    }
}
