package simra.androidtest.asadpour.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kotlin.jvm.functions.Function3;
import simra.androidtest.asadpour.databinding.FragmentSearchBinding;
import simra.androidtest.asadpour.ui.base.BaseFragment;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    @Override
    protected Function3<LayoutInflater, ViewGroup, Boolean, FragmentSearchBinding> getBindingInflater() {
        return FragmentSearchBinding::inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
