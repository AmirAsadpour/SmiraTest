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
import simra.androidtest.asadpour.util.SingleLiveEvent;

public class SearchViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    private final MutableLiveData<List<MiniMovie>> mMovies = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> mShowProgress = new MutableLiveData<>();
    private final SingleLiveEvent<Throwable> mShowError = new SingleLiveEvent<>();

    private String mCurrentQuery = "";

    private final MutableLiveData<Boolean> mLoadMoreProgress = new MutableLiveData<>();
    private final SingleLiveEvent<Throwable> mLoadMoreError = new SingleLiveEvent<>();
    private final MutableLiveData<Boolean> mShowEmptyState = new MutableLiveData<>();

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

    public LiveData<Boolean> getShowEmptyState() {
        return mShowEmptyState;
    }

    public void performSearch(String query) {
        // resetting empty state
        mShowEmptyState.setValue(false);
        mShowProgress.setValue(true);
        if (mInitialSearchDisposable != null) mInitialSearchDisposable.dispose();
        mInitialSearchDisposable = movieRepository.searchMovies(query, 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((miniMovies, throwable) -> {
                    lastLoadedPage = 1;
                    mShowProgress.setValue(false);
                    if (miniMovies != null) {
                        mCurrentQuery = query;
                        mMovies.setValue(miniMovies);
                    } else {
                        if (throwable instanceof NullPointerException) mShowEmptyState.setValue(true);
                        else mShowError.setValue(throwable);
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
                    lastLoadedPage = page;
                    mLoadMoreProgress.setValue(false);
                    if (miniMovies != null) {
                        List<MiniMovie> newList = mMovies.getValue();
                        newList.addAll(miniMovies);
                        mMovies.setValue(newList);
                    } else {
                        if (!(throwable instanceof NullPointerException))
                            mLoadMoreError.setValue(throwable);
                    }
                }));
    }

    public void retryLoadMore() {
        onLoadMore(lastLoadedPage);
    }
}
