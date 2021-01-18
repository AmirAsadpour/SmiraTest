package simra.androidtest.asadpour.ui.detail;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import simra.androidtest.asadpour.data.model.Movie;
import simra.androidtest.asadpour.data.repository.MovieRepository;
import simra.androidtest.asadpour.ui.base.BaseViewModel;

public class MovieDetailViewModel extends BaseViewModel {
    private final MovieRepository mRepository;

    private String movieID;

    private MutableLiveData<Boolean> mShowProgress = new MutableLiveData<>(false);
    private MutableLiveData<Movie> mMovie = new MutableLiveData<>();
    private MutableLiveData<Throwable> mShowError = new MutableLiveData<>();


    public LiveData<Boolean> getShowProgress() {
        return mShowProgress;
    }

    public LiveData<Movie> getMovie() {
        return mMovie;
    }

    public LiveData<Throwable> getShowError() {
        return mShowError;
    }


    @ViewModelInject
    public MovieDetailViewModel(MovieRepository repository) {
        mRepository = repository;
    }

    public void setMovieId(String movieID) {
        this.movieID = movieID;
        loadMovieDetail();
    }

    public void loadMovieDetail() {
        if (movieID == null) return;
        mShowProgress.setValue(true);
        disposables.add(mRepository.getMovieDetail(movieID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((movie, throwable) -> {
                    mShowProgress.setValue(false);
                    if (movie != null) {
                        mMovie.setValue(movie);
                    } else {
                        mShowError.setValue(throwable);
                    }
                }));
    }
}
