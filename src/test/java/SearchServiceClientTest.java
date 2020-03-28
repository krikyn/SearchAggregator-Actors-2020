import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.kirill.vakhrushev.searchaggregator.actor.MasterSearchActor;
import com.kirill.vakhrushev.searchaggregator.service.BingClient;
import com.kirill.vakhrushev.searchaggregator.service.GoogleClient;
import com.kirill.vakhrushev.searchaggregator.service.SearchServiceClient;
import com.kirill.vakhrushev.searchaggregator.service.YandexClient;
import org.junit.jupiter.api.Test;
import scala.concurrent.duration.FiniteDuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchServiceClientTest {

    private static ActorSystem system = ActorSystem.create();
    private static List<SearchServiceClient> clients = new ArrayList<>();

    @Test
    void test() {

        clients.add(new SearchServiceClient() {
            @Override
            public String getServiceName() {
                return "Test service 1";
            }
        });
        clients.add(new GoogleClient());
        clients.add(new YandexClient());
        clients.add(new BingClient());

        SearchServiceClientUtil.getKorona(system, clients);
    }
}
