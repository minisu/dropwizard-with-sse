package henrikstrath.example;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ExampleApplication extends Application<Configuration> {

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(new Resource());
        environment.getApplicationContext().addServlet(SseEventSourceServlet.class, "/sse");
    }

    public static void main(String[] args) throws Exception {
        new ExampleApplication().run(args);
    }
}
