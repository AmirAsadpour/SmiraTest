package simra.androidtest.asadpour.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import simra.androidtest.asadpour.databinding.ViewContentStateBinding;

public class ContentStateView extends FrameLayout {
    private ViewContentStateBinding binding;

    public ContentStateView(@NonNull Context context) {
        super(context);
        init();
    }

    public ContentStateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentStateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = ViewContentStateBinding.inflate(LayoutInflater.from(getContext()), this, true);
    }

    public void showProgress() {
        binding.errorFrame.setVisibility(GONE);
        binding.progressBar.setVisibility(VISIBLE);
    }

    public void showError(String message, Listener listener) {
        binding.progressBar.setVisibility(GONE);
        binding.errorFrame.setVisibility(VISIBLE);
        binding.errorMessageText.setText(message);
        binding.actionButton.setOnClickListener(v -> {
            listener.onRetry();
        });
    }

    public void showEmptyState(String message) {
        binding.progressBar.setVisibility(GONE);
        binding.errorFrame.setVisibility(VISIBLE);
        binding.errorMessageText.setText(message);
        binding.actionButton.setVisibility(GONE);
    }

    public interface Listener {
        void onRetry();
    }
}
