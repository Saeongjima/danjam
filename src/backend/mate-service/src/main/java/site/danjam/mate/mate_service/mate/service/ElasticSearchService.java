package site.danjam.mate.mate_service.mate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public void checkConnection() {
        try {
            System.out.println("✅ Elasticsearch 연결 성공: " + elasticsearchOperations.toString());
        } catch (Exception e) {
            System.err.println("❌ Elasticsearch 연결 실패: " + e.getMessage());
        }
    }
}
