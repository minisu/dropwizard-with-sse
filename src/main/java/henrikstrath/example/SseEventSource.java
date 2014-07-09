package henrikstrath.example;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.jetty.servlets.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SseEventSource implements EventSource {

    private static final Logger log = LoggerFactory.getLogger(SseEventSource.class);
    
    private Emitter emitter;
    private String id;

    public SseEventSource() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
    public void onOpen(Emitter emitter) throws IOException {
        log.info("onOpen");
        this.emitter = emitter;
    }

    @Override
    public void onClose() {
        log.info("onClose");
        //EventPublisher.removeListener(this);
    }

    public void pushEvent(String dataToSend)  {
        log.info("pushEvent");
        try {
            this.emitter.data(dataToSend);
        }
        catch(IOException e) {
            log.warn("Failed to push to client ", e);
        }
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SseEventSource) {
            SseEventSource that = (SseEventSource)obj;
            return Objects.equals(this.id, that.id);
        }
        return false;
    }
}
