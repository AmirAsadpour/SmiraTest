package simra.androidtest.asadpour.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import kotlin.jvm.functions.Function3;
import simra.androidtest.asadpour.R;
import simra.androidtest.asadpour.data.model.Movie;
import simra.androidtest.asadpour.databinding.FragmentMovieDetailBinding;
import simra.androidtest.asadpour.ui.base.BaseFragment;
import simra.androidtest.asadpour.util.ViewUtil;

@AndroidEntryPoint
public class MovieDetailFragment extends BaseFragment<FragmentMovieDetailBinding> {
    private MovieDetailViewModel mViewModel;

    @Override
    protected Function3<LayoutInflater, ViewGroup, Boolean, FragmentMovieDetailBinding> getBindingInflater() {
        return FragmentMovieDetailBinding::inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        mViewModel.setMovieId(MovieDetailFragmentArgs.fromBundle(getArguments()).getImdbID());

        mViewModel.getShowProgress().observe(getViewLifecycleOwner(), this::showProgress);
        mViewModel.getShowError().observe(getViewLifecycleOwner(), this::showError);
        mViewModel.getMovie().observe(getViewLifecycleOwner(), this::showMovieDetail);
    }

    private void showMovieDetail(Movie movie) {
        getBinding().contentStateView.setVisibility(View.GONE);
        getBinding().contentGroup.setVisibility(View.VISIBLE);
        ViewUtil.loadUrl(getBinding().movieImage, movie.getPosterImageUrl());
        getBinding().titleText.setText(movie.getTitle());
        getBinding().typeText.setText(movie.getType());
        getBinding().yearText.setText(getString(R.string.label_year, movie.getYear()));
        getBinding().releaseText.setText(getString(R.string.label_released, movie.getReleased()));
        getBinding().directorText.setText(getString(R.string.label_director, movie.getDirector()));
        getBinding().genreText.setText(movie.getGenre());
        getBinding().actorsText.setText(movie.getActors());
        getBinding().plotText.setText(movie.getPlot());

        RatingsAdapter adapter = new RatingsAdapter();
        getBinding().ratingsRecyclerView.setAdapter(adapter);
        adapter.submitList(movie.getRatings());
    }

    private void showError(Throwable throwable) {
        getBinding().contentGroup.setVisibility(View.GONE);
        getBinding().contentStateView.setVisibility(View.VISIBLE);
        getBinding().contentStateView.showError(getString(R.string.msg_error_general), () -> mViewModel.loadMovieDetail());
    }

    private void showProgress(Boolean show) {
        if (show) {
            getBinding().contentGroup.setVisibility(View.GONE);
            getBinding().contentStateView.setVisibility(View.VISIBLE);
            getBinding().contentStateView.showProgress();
        } else {
            getBinding().contentStateView.setVisibility(View.GONE);
            getBinding().contentGroup.setVisibility(View.VISIBLE);
        }
    }
}
