import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.kirill.vakhrushev.searchaggregator.actor.MasterSearchActor;
import com.kirill.vakhrushev.searchaggregator.service.SearchServiceClient;
import scala.concurrent.duration.FiniteDuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchServiceClientUtil {
    public static TestKit getKorona(ActorSystem system, List<SearchServiceClient> clients) {
        return new TestKit(system) {
            {
                final Props props = Props.create(MasterSearchActor.class, clients);
                final ActorRef master = system.actorOf(props);

                within(FiniteDuration.apply(1, TimeUnit.SECONDS),
                        () -> {
                            master.tell("Короновирус", getRef());
                            expectMsgAnyClassOf(List.class);
                            return null;
                        });
            }
        };
    }
}
