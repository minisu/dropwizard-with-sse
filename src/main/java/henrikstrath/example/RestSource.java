package henrikstrath.example;

import henrikstrath.example.rx.Source;

public class RestSource extends Source<String> {

    public static RestSource INSTANCE = new RestSource();
}
