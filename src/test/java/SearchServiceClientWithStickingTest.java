import akka.actor.ActorSystem;
import com.kirill.vakhrushev.searchaggregator.model.SearchResult;
import com.kirill.vakhrushev.searchaggregator.service.BingClient;
import com.kirill.vakhrushev.searchaggregator.service.GoogleClient;
import com.kirill.vakhrushev.searchaggregator.service.SearchServiceClient;
import com.kirill.vakhrushev.searchaggregator.service.YandexClient;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchServiceClientWithStickingTest {

    private static ActorSystem system = ActorSystem.create();
    private static List<SearchServiceClient> clients = new ArrayList<>();

    @Test
    public void test() {

        clients.add(new SearchServiceClient() {
            @Override
            public String getServiceName() {
                return "Google with sticking 1";
            }

            @Override
            public List<SearchResult> search(String query) throws InterruptedException {
                List<SearchResult> result = new ArrayList<>();
                result.add(new SearchResult(getServiceName(), "title1", "reference1.com", 1));
                TimeUnit.SECONDS.sleep(100);
                result.add(new SearchResult(getServiceName(), "title2", "reference2.com", 2));
                return result;
            }
        });
        clients.add(new YandexClient());
        clients.add(new BingClient());

        SearchServiceClientUtil.getKorona(system, clients);
    }
}