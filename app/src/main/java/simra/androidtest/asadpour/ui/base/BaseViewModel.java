package simra.androidtest.asadpour.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

abstract public class BaseViewModel extends ViewModel {
    protected CompositeDisposable disposables;

    public BaseViewModel() {
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
