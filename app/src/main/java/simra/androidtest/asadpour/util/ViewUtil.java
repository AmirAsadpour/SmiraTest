package simra.androidtest.asadpour.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewUtil {
    public static void hide(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.GONE)
                view.setVisibility(View.GONE);
        }
    }

    public static void show(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.VISIBLE)
                view.setVisibility(View.VISIBLE);
        }
    }

    public static void loadUrl(ImageView imageView, String url) {
        if (url != null && !url.isEmpty())
            Picasso.get().load(url).into(imageView);
    }

    public static void hideKeyboard(Activity activity, View... views) {
        for (View v : views) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
    }
}
