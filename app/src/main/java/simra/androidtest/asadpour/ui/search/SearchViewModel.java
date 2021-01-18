package simra.androidtest.asadpour.ui.search;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import simra.androidtest.asadpour.data.model.MiniMovie;
import simra.androidtest.asadpour.data.repository.MovieRepository;
import simra.androidtest.asadpour.ui.base.BaseViewModel;

public class SearchViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    private final MutableLiveData<List<MiniMovie>> mMovies = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> mShowProgress = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mShowError = new MutableLiveData<>();

    private String mCurrentQuery = "";

    private final MutableLiveData<Boolean> mLoadMoreProgress = new MutableLiveData<>();
    private final MutableLiveData<Throwable> mLoadMoreError = new MutableLiveData<>();

    private Disposable mInitialSearchDisposable;

    int lastLoadedPage = 0;

    @ViewModelInject
    public SearchViewModel(MovieRepository repository) {
        movieRepository = repository;
    }

    public LiveData<List<MiniMovie>> getMovies() {
        return mMovies;
    }

    public LiveData<Boolean> getShowProgress() {
        return mShowProgress;
    }

    public LiveData<Throwable> getShowError() {
        return mShowError;
    }

    public LiveData<Boolean> getLoadMoreProgress() {
        return mLoadMoreProgress;
    }

    public LiveData<Throwable> getLoadMoreError() {
        return mLoadMoreError;
    }

    public void performSearch(String query) {
        mShowProgress.setValue(true);
        if (mInitialSearchDisposable != null) mInitialSearchDisposable.dispose();
        mInitialSearchDisposable = movieRepository.searchMovies(query, 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((miniMovies, throwable) -> {
                    mShowProgress.setValue(false);
                    if (miniMovies != null) {
                        mCurrentQuery = query;
                        lastLoadedPage = 1;
                        mMovies.setValue(miniMovies);
                    } else {
                        mShowError.setValue(throwable);
                    }
                });
        disposables.add(mInitialSearchDisposable);
    }

    public void onLoadMore(int page) {
        mLoadMoreProgress.setValue(true);
        disposables.add(movieRepository.searchMovies(mCurrentQuery, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((miniMovies, throwable) -> {
                    mLoadMoreProgress.setValue(false);
                    if (miniMovies != null) {
                        lastLoadedPage = page;
                        List<MiniMovie> newList = mMovies.getValue();
                        newList.addAll(miniMovies);
                        mMovies.setValue(newList);
                    } else {
                        mLoadMoreError.setValue(throwable);
                    }
                }));
    }
}
