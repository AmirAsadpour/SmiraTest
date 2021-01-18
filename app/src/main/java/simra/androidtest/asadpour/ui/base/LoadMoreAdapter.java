package simra.androidtest.asadpour.ui.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import simra.androidtest.asadpour.util.view.ContentStateView;

public class LoadMoreAdapter extends RecyclerView.Adapter<LoadMoreAdapter.ViewHolder> {

    private State mState = State.DONE;
    private Listener mListener;

    public LoadMoreAdapter(Listener listener) {
        mListener = listener;
    }

    public void setState(State state) {
        if (mState == State.LOADING && state == State.DONE) {
            mState = state;
            notifyItemRemoved(0);
        } else if (mState == State.DONE && (state == State.LOADING || state == State.ERROR)) {
            mState = state;
            notifyItemInserted(0);
        } else {
            notifyItemChanged(0);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ContentStateView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentStateView contentStateView = (ContentStateView) holder.itemView;
        switch (mState) {
            case LOADING:
                contentStateView.showProgress();
                break;
            case ERROR:
                contentStateView.showError("", () -> mListener.onRetry());
                break;
            case DONE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mState == State.DONE) return 0;
        else return 1;
    }

    public enum State {
        LOADING, ERROR, DONE
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface Listener {
        void onRetry();
    }
}
