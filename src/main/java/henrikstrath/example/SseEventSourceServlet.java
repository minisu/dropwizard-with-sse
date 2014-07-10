package henrikstrath.example;

import henrikstrath.example.rx.Source;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class SseEventSourceServlet extends EventSourceServlet {
    private static final Logger log = LoggerFactory.getLogger(SseEventSourceServlet.class);

    private final Source<String> source = RestSource.INSTANCE;

    /*
    public SseEventSourceServlet(Source<String> sourceFeed) {
        this.source = sourceFeed;
    }*/

    @Override
    protected EventSource newEventSource(HttpServletRequest request) {

        String topic = request.getParameter("topic");

        log.debug("Client subscribed to topic " + topic);

        SseEventSource eventSource = new SseEventSource();

        source.asObservable()
                .filter(topic::equals)
                .subscribe(eventSource::pushEvent);

        return eventSource;
    }
}
