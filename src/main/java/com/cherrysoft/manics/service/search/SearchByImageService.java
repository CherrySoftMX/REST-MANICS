package com.cherrysoft.manics.service.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchTemplateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.cherrysoft.manics.exception.ApplicationException;
import com.cherrysoft.manics.model.Page;
import com.cherrysoft.manics.model.search.SearchByImageResult;
import com.cherrysoft.manics.model.search.SearchingPage;
import com.cherrysoft.manics.service.PageService;
import com.cherrysoft.manics.service.search.utils.ImageAnalysisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
public class SearchByImageService {
  private final ElasticsearchClient client;
  private final PageService pageService;

  public SearchByImageResult searchPagesByImage(String imgUrl, Pageable pageable) {
    try {
      tryPrepareSearchClient();
      SearchTemplateResponse<SearchingPage> searchResponse = trySearchPagesByImage(imgUrl, pageable);
      return parseSearchResult(searchResponse);
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

  private SearchByImageResult parseSearchResult(SearchTemplateResponse<SearchingPage> searchResponse) {
    SearchByImageResult result = new SearchByImageResult();
    for (Hit<SearchingPage> hit : searchResponse.hits().hits()) {
      double score = requireNonNullElse(hit.score(), 0.0);
      Page page = pageService.getPageById(hit.source().getId());
      result.addMatchingPage(new SearchByImageResult.MatchingPage(page, score));
    }
    return result;
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