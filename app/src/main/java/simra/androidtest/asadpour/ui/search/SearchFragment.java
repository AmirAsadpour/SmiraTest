package simra.androidtest.asadpour.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.jvm.functions.Function3;
import simra.androidtest.asadpour.data.model.MiniMovie;
import simra.androidtest.asadpour.databinding.FragmentSearchBinding;
import simra.androidtest.asadpour.ui.base.BaseFragment;
import simra.androidtest.asadpour.ui.base.LoadMoreAdapter;
import simra.androidtest.asadpour.util.EndlessRecyclerViewScrollListener;
import simra.androidtest.asadpour.util.ViewUtil;

@AndroidEntryPoint
public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    private SearchViewModel mViewModel;
    private MovieAdapter mAdapter;
    private LoadMoreAdapter mLoadMoreAdapter;
    private EndlessRecyclerViewScrollListener mOnScrollListener;

    @Override
    protected Function3<LayoutInflater, ViewGroup, Boolean, FragmentSearchBinding> getBindingInflater() {
        return FragmentSearchBinding::inflate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MovieAdapter(imdbID -> {
//            NavHostFragment.findNavController(this).navigate();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        getBinding().searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mOnScrollListener.resetState();
                performSearch();
                ViewUtil.hideKeyboard(getActivity(), getBinding().searchEditText);
                return true;
            }
            return false;
        });

        setupRecyclerView();

        mViewModel.getMovies().observe(getViewLifecycleOwner(), this::showMovies);
        mViewModel.getShowProgress().observe(getViewLifecycleOwner(), this::showProgress);
        mViewModel.getShowError().observe(getViewLifecycleOwner(), this::showError);
        mViewModel.getLoadMoreProgress().observe(getViewLifecycleOwner(), this::showLoadMoreProgress);
        mViewModel.getLoadMoreError().observe(getViewLifecycleOwner(), this::showLoadMoreError);
    }

    private void showLoadMoreError(Throwable throwable) {
//        mLoadMoreAdapter.setState(LoadMoreAdapter.State.ERROR);
    }

    private void showLoadMoreProgress(Boolean show) {
//        if (show)
//            mLoadMoreAdapter.setState(LoadMoreAdapter.State.LOADING);
//        else
//            mLoadMoreAdapter.setState(LoadMoreAdapter.State.DONE);
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mOnScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mViewModel.onLoadMore(page);
            }
        };
        if (mViewModel.lastLoadedPage != 0)
            mOnScrollListener.setInitialPageIndex(mViewModel.lastLoadedPage);
        getBinding().recyclerView.addOnScrollListener(mOnScrollListener);
        getBinding().recyclerView.setLayoutManager(layoutManager);
        getBinding().recyclerView.setAdapter(mAdapter);
    }

    private void performSearch() {
        mViewModel.performSearch(getBinding().searchEditText.getText().toString());
    }

    private void showError(Throwable throwable) {
        getBinding().recyclerView.setVisibility(View.GONE);
        getBinding().contentStateView.setVisibility(View.VISIBLE);
        getBinding().contentStateView.showError(throwable.getLocalizedMessage(), this::performSearch);
    }

    private void showProgress(Boolean show) {
        if (show) {
            getBinding().recyclerView.setVisibility(View.GONE);
            getBinding().contentStateView.setVisibility(View.VISIBLE);
            getBinding().contentStateView.showProgress();
        } else {
            getBinding().contentStateView.setVisibility(View.GONE);
            getBinding().recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void showMovies(List<MiniMovie> movies) {
        mAdapter.submitList(new ArrayList<>(movies));
    }
}
