package henrikstrath.example;

import static rx.Observable.OnSubscribe;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.Set;

import com.google.common.collect.Sets;

public class Source<T> {

    protected final Set<Subscriber<? super T>> subscribers = Sets.newConcurrentHashSet();
    private final Observable<T> observable;

    public Source() {
        observable = Observable.create((OnSubscribe<T>) (subscriber) -> {
            subscribers.add(subscriber);
            subscriber.add(new Subscription() {
                @Override
                public void unsubscribe() {
                    subscribers.remove(subscriber);
                }

                @Override
                public boolean isUnsubscribed() {
                    return !subscribers.contains(subscriber);
                }
            });
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
