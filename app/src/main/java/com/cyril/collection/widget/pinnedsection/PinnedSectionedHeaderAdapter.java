package com.cyril.collection.widget.pinnedsection;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cyril on 2017/8/9.
 */

public interface PinnedSectionedHeaderAdapter {
    boolean isSectionHeader(int position);

    int getSectionForPosition(int position);

    View getSectionHeaderView(int section, View convertView, ViewGroup parent);

    int getSectionHeaderViewType(int section);

    int getCount();
}
