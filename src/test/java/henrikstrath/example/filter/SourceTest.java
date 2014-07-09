package henrikstrath.example.filter;

import static org.fest.assertions.api.Assertions.assertThat;

import henrikstrath.example.Source;
import rx.Observable;

import org.junit.Test;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

public class SourceTest {

    @Test
    public void testPipelineRx2() {
        Source<Integer> source = new Source<>();
        Observable<Integer> observable = source.asObservable();

        Multiset<Integer> matchedIntegers = ConcurrentHashMultiset.create();

        source.push(2);

        observable
                .filter(x -> x < 10)
                .forEach(matchedIntegers::add);

        source.push(1);
        source.push(7);

        observable
                .filter(x -> x < 5)
                .forEach(matchedIntegers::add);

        source.push(12);
        source.push(4);

        assertThat(matchedIntegers).isEqualTo(ImmutableMultiset.of(1, 7, 4, 4));
    }
}
