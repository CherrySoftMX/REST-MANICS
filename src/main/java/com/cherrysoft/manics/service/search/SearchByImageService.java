package com.cherrysoft.manics.service.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchTemplateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import com.cherrysoft.manics.exception.ApplicationException;
import com.cherrysoft.manics.model.CartoonPage;
import com.cherrysoft.manics.model.search.MatchingPage;
import com.cherrysoft.manics.model.search.SearchingPage;
import com.cherrysoft.manics.service.PageService;
import com.cherrysoft.manics.service.search.utils.ImageAnalysisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
public class SearchByImageService {
  private final ElasticsearchClient client;
  private final PageService pageService;

  public Page<MatchingPage> searchPagesByImage(String imgUrl, Pageable pageable) {
    try {
      tryPrepareSearchClient();
      SearchTemplateResponse<SearchingPage> searchResponse = trySearchPagesByImage(imgUrl, pageable);
      return parseSearchResult(searchResponse, pageable);
    } catch (IOException e) {
      throw new ApplicationException(e);
    }
  }

  private void tryPrepareSearchClient() throws IOException {
    String queryTemplate = getQueryTemplate();
    client.putScript(request -> request
        .id("query-script")
        .script(script -> script
            .lang("mustache")
            .source(queryTemplate)
        ));
  }

  private SearchTemplateResponse<SearchingPage> trySearchPagesByImage(String imgUrl, Pageable pageable) throws IOException {
    Double[] imageVector = ImageAnalysisUtils.convertImgUrlToVector(imgUrl);
    return client.searchTemplate(r -> r
            .index("pages")
            .id("query-script")
            .params("queryVector", JsonData.of(Arrays.toString(imageVector)))
            .params("from", JsonData.of(pageable.getOffset()))
            .params("size", JsonData.of(pageable.getPageSize())),
        SearchingPage.class
    );
  }

  private Page<MatchingPage> parseSearchResult(SearchTemplateResponse<SearchingPage> searchResponse, Pageable pageable) {
    List<MatchingPage> matchingPages = new ArrayList<>();
    HitsMetadata<SearchingPage> hitMetadata = searchResponse.hits();
    for (Hit<SearchingPage> hit : hitMetadata.hits()) {
      double score = requireNonNullElse(hit.score(), 0.0);
      CartoonPage page = pageService.getPageById(hit.source().getId());
      matchingPages.add(new MatchingPage(page, score));
    }
    long totalMatches = hitMetadata.total().value();
    return new PageImpl<>(matchingPages, pageable, totalMatches);
  }

  private String getQueryTemplate() {
    return """
        {
          "from": {{from}},
          "size": {{size}},
          "query": {
            "function_score": {
              "query": {
                "match_all": {}
              },
              "script_score": {
                "script": {
                  "source": "1 / (1 + l2norm(params.query_vector, 'vector_values'))",
                  "params": {
                    "query_vector": {{queryVector}}
                  }
                }
              },
              "score_mode": "max"
            }
          }
        }
        """;
  }

}
