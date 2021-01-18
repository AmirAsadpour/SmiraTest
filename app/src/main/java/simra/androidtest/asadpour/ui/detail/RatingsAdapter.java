package simra.androidtest.asadpour.ui.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import simra.androidtest.asadpour.data.model.MovieRating;
import simra.androidtest.asadpour.databinding.ItemRatingBinding;

public class RatingsAdapter extends ListAdapter<MovieRating, RatingsAdapter.ViewHolder> {

    protected RatingsAdapter() {
        super(new DiffUtil.ItemCallback<MovieRating>() {
            @Override
            public boolean areItemsTheSame(@NonNull MovieRating oldItem, @NonNull MovieRating newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull MovieRating oldItem, @NonNull MovieRating newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                ItemRatingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieRating item = getItem(position);
        holder.binding.titleText.setText(item.getSource());
        holder.binding.valueText.setText(item.getValue());
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) holder.binding.valueView.getLayoutParams();
        params.matchConstraintPercentWidth = getRatingValuePercent(item.getValue()) / 100f;
        holder.binding.valueView.setLayoutParams(params);
    }

    private int getRatingValuePercent(String str) {
        try {
            Matcher m = Pattern.compile("(?!=\\d\\.\\d\\.)([\\d.]+)").matcher(str);
            m.find();
            double firstNumber = Double.parseDouble(m.group());
            if (firstNumber >= 10) return (int) firstNumber;
            else {
                boolean found = m.find();
                if (found) {
                    double secondNumber = Double.parseDouble(m.group());
                    if (secondNumber == 100) return (int) firstNumber;
                    else if (secondNumber == 10) return (int) (firstNumber * 10);
                    else return 0;
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemRatingBinding binding;

        public ViewHolder(@NonNull ItemRatingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
