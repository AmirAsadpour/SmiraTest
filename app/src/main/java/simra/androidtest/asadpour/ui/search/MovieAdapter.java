package simra.androidtest.asadpour.ui.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import simra.androidtest.asadpour.R;
import simra.androidtest.asadpour.data.model.MiniMovie;
import simra.androidtest.asadpour.databinding.ItemMovieBinding;
import simra.androidtest.asadpour.util.ViewUtil;

public class MovieAdapter extends ListAdapter<MiniMovie, MovieAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    public MovieAdapter(OnItemClickListener onItemClickListener) {
        super(new DiffUtil.ItemCallback<MiniMovie>() {
            @Override
            public boolean areItemsTheSame(@NonNull MiniMovie oldItem, @NonNull MiniMovie newItem) {
                return oldItem.getImdbID().equals(newItem.getImdbID());
            }

            @Override
            public boolean areContentsTheSame(@NonNull MiniMovie oldItem, @NonNull MiniMovie newItem) {
                return oldItem.equals(newItem);
            }
        });
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        MiniMovie item = getItem(position);
        holder.binding.titleText.setText(item.getTitle());
        holder.binding.typeText.setText(item.getType());
        holder.binding.yearText
                .setText(holder.binding.getRoot().getContext().getString(R.string.label_year, item.getYear()));
        ViewUtil.loadUrl(holder.binding.movieImage, item.getPosterImageUrl());
        holder.binding.getRoot().setOnClickListener(v -> mOnItemClickListener.onItemClicked(item.getImdbID()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        public ViewHolder(@NonNull ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(String imdbID);
    }
}
