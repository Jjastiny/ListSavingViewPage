package isy.listsavingviewpage;

import android.widget.AbsListView;

/**
 * Created by justinyang on 5/4/15.
 */
public interface ScrollTabHolder {
    void adjustScroll(int scrollHeight, int headerTranslationY, boolean isOthersTop);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
