package simra.androidtest.asadpour.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Function;
import simra.androidtest.asadpour.data.model.MiniMovie;
import simra.androidtest.asadpour.data.model.response.SearchResponse;
import simra.androidtest.asadpour.data.remote.WebService;

@Singleton
public class MovieRepository {

    private WebService webService;

    @Inject
    public MovieRepository(WebService webService) {
        this.webService = webService;
    }


    public Single<List<MiniMovie>> searchMovies(String query, int page) {
        return webService.searchMovies(query, page).map(SearchResponse::getMovies);
    }
}
