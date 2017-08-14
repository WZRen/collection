package com.cyril.collection.widget.pinnedsection;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by cyril on 2017/8/9.
 */

public abstract class SectionedBaseAdapter extends BaseAdapter implements PinnedSectionedHeaderAdapter {
    private final SparseArray<Integer> mSectionPositionCache;

    private final SparseArray<Integer> mSectionCache;
    private final SparseArray<Integer> mSectionCountCache;

    private int mCount;
    private int mSectionCount;

    public SectionedBaseAdapter() {
        super();
        mSectionCache = new SparseArray<>();
        mSectionPositionCache = new SparseArray<>();
        mSectionCountCache = new SparseArray<>();
        mCount = -1;
        mSectionCount = -1;
    }

    @Override
    public void notifyDataSetChanged() {
        mSectionCache.clear();
        mSectionPositionCache.clear();
        mSectionCountCache.clear();
        mCount = -1;
        mSectionCount = -1;
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        mSectionCache.clear();
        mSectionPositionCache.clear();
        mSectionCountCache.clear();
        mCount = -1;
        mSectionCount = -1;
        super.notifyDataSetInvalidated();
    }

    @Override
    public int getCount() {
        if (mCount >= 0) {
            return mCount;
        }
        int count = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            count += internalGetCountForSection(i);
            count++;
        }
        mCount = count;
        return count;
    }

    @Override
    public Object getItem(int i) {
        return getItem(getSectionForPosition(i), getPositionInSectionForPosition(i));
    }

    @Override
    public long getItemId(int i) {
        return getItemId(getSectionForPosition(i), getPositionInSectionForPosition(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (isSectionHeader(i)) {
            return getSectionHeaderView(getSectionForPosition(i), view, viewGroup);
        }
        return getItemView(getSectionForPosition(i), getPositionInSectionForPosition(i), view, viewGroup);
    }

    @Override
    public int getViewTypeCount() {
        return getItemViewTypeCount() + getSectionHeaderViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSectionHeader(position)) {
            return getItemViewTypeCount() + getSectionHeaderViewType(getSectionForPosition(position));
        }
        return getItemViewType(getSectionForPosition(position), getPositionInSectionForPosition(position));
    }

    public final int getSectionForPosition(int position) {
        Integer cachedSection = mSectionCache.get(position);
        if (cachedSection != null) {
            return cachedSection;
        }
        int sectionStart = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            int sectionCount = internalGetCountForSection(i);
            int sectionEnd = sectionStart + sectionCount + 1;
            if (position >= sectionStart && position < sectionEnd) {
                mSectionCache.put(position, i);
                return i;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

    public int getPositionInSectionForPosition(int position) {
        Integer cachedPosition = mSectionPositionCache.get(position);
        if (cachedPosition != null) {
            return cachedPosition;
        }
        int sectionStart = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            int sectionCount = internalGetCountForSection(i);
            int sectionEnd = sectionStart + sectionCount + 1;
            if (position >= sectionStart && position < sectionEnd) {
                int positionInSection = position - sectionStart - 1;
                mSectionPositionCache.put(position, positionInSection);
                return positionInSection;
            }
            sectionStart = sectionEnd;
        }
        return 0;
    }

    public final boolean isSectionHeader(int position) {
        int sectionStart = 0;
        for (int i = 0; i < internalGetSectionCount(); i++) {
            if (position == sectionStart) {
                return true;
            } else if (position < sectionStart) {
                return false;
            }
            sectionStart += internalGetCountForSection(i) + 1;
        }
        return false;
    }

    public int getItemViewType(int section, int position) {
        return 0;
    }

    public int getItemViewTypeCount() {
        return 1;
    }

    public int getSectionHeaderViewType(int section) {
        return 0;
    }

    public int getSectionHeaderViewTypeCount() {
        return 1;
    }

    public abstract Object getItem(int section, int position);

    public abstract long getItemId(int section, int position);

    public abstract int getSectionCount();

    public abstract int getCountForSection(int section);

    public abstract View getItemView(int section, int position, View convertView, ViewGroup parent);

    public abstract View getSectionHeaderView(int section, View convertView, ViewGroup parent);

    private int internalGetCountForSection(int section) {
        Integer cachedSectionCount = mSectionCountCache.get(section);
        if (cachedSectionCount != null) {
            return cachedSectionCount;
        }
        int sectionCount = getCountForSection(section);
        mSectionCountCache.put(section, sectionCount);
        return sectionCount;
    }

    private int internalGetSectionCount() {
        if (mSectionCount >= 0) {
            return mSectionCount;
        }
        mSectionCount = getSectionCount();
        return mSectionCount;
    }
}
