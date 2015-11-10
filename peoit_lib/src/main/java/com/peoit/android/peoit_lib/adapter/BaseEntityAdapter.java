package com.peoit.android.peoit_lib.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 实体适配器
 *
 * @create libo 2015/1/22
 * @finish ...
 */
public abstract class BaseEntityAdapter<T> extends BaseAdapter {
    private LayoutInflater inflater;
    protected List<T> datas;
    protected int layoutId;
    protected Activity mAc;
    protected Fragment mFragment;
    private Comparator<T> mDataComparator;
    private List<T> mDatasBefroeComparator;

    public BaseEntityAdapter(Activity mAc, int layoutId) {
        this(mAc, layoutId, null);
    }

    public BaseEntityAdapter(Activity mAc, int layoutId, List<T> datas) {
        this.mAc = mAc;
        this.layoutId = layoutId;
        this.datas = datas;
        inflater = LayoutInflater.from(mAc);
        upDateList(datas);
    }

    public BaseEntityAdapter(Fragment fragment, int layoutId) {
        this(fragment, layoutId, null);
    }

    public BaseEntityAdapter(Fragment fragment, int layoutId, List<T> datas) {
        this.mAc = fragment.getActivity();
        this.mFragment = fragment;
        this.layoutId = layoutId;
        this.datas = datas;
        inflater = LayoutInflater.from(mAc);
        upDateList(datas);
    }

    protected View inflater() {
        if (inflater == null)
            throw new NullPointerException("@libo inflater is null");
        return inflater.inflate(layoutId, null);
    }

    public LayoutInflater getLayoutInflater() {
        return inflater;
    }

    /**
     * 更新数据
     *
     * @param datas
     */
    public BaseEntityAdapter<T> upDateList(List<T> datas) {
        if (datas == null) {
            datas = new ArrayList<T>();
        }
        this.datas = datas;
        if (mDataComparator != null) {
            Collections.sort(this.datas, mDataComparator);
        }
        notifyDataSetChanged();
        return this;
    }

    /**
     * 向集合头部追加数据
     *
     * @param datas
     */
    public boolean addHeadDataList(List<T> datas) {
        boolean isChange = false;
        if (datas == null || datas.size() == 0) {
            datas = new ArrayList<>();
        }
        if (this.datas == null || this.datas.size() == 0){
            this.datas = datas;
            isChange = true;
        } else {
            for (int i = datas.size() - 1; i >= 0; i--) {
                if (!this.datas.contains(datas.get(i))) {
                    this.datas.add(0, datas.get(i));
                    isChange = true;
                }
            }
        }
        if (isChange) {
            if (mDataComparator != null) {
                Collections.sort(datas, mDataComparator);
            }
            notifyDataSetChanged();
        }
        return isChange;
    }

    /**
     * 向集合尾部追加数据
     *
     * @param datas
     */
    public boolean addFootDataList(List<T> datas) {
        boolean isChange = false;
        if (datas == null || datas.size() == 0) {
            datas = new ArrayList<>();
        }
        if (this.datas == null || this.datas.size() ==0){
            this.datas = datas;
            isChange = true;
        } else {
            for (int i = 0; i < datas.size(); i++) {
                if (!this.datas.contains(datas.get(i))) {
                    this.datas.add(datas.get(i));
                    isChange = true;
                }
            }
        }
        if (isChange) {
            if (mDataComparator != null) {
                Collections.sort(datas, mDataComparator);
            }
            notifyDataSetChanged();
        }
        return isChange;
    }

    /**
     * 移除datas里的单个数据 by data
     * <p/>
     * 注：需重写实体类equals方法
     *
     * @param data
     */
    public void removeDataItem(T data) {
        if (datas.contains(data)) {
            datas.remove(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除datas里的所有数据
     */
    public void removeAllData() {
        if (datas != null) {
            datas.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 移除datas里的单个数据，根据data下标
     *
     * @param position
     */
    public void removeDataItem(int position) {
        if (position >= 0 && datas.size() > 0 && datas.size() > position) {
            datas.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 改变某一个位置的数据
     *
     * @param postition
     * @param t
     */
    protected void changData(int postition, T t) {
        datas.remove(postition);
        datas.add(postition, t);
        notifyDataSetChanged();
    }

    /**
     * 设置比较器
     *
     * @param temp
     */
    public void setDataComparator(Comparator<T> temp) {
        setDataComparator(temp, false);
    }

    /**
     * 设置
     *
     * @param temp
     * @param isSave
     */
    public void setDataComparator(Comparator<T> temp, boolean isSave) {
        if (isSave) {
            mDataComparator = temp;
        }
        if (datas != null && datas.size() > 0) {
            Collections.sort(datas, temp);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<T> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderBase holderBase = null;
        if (convertView == null) {
            holderBase = getViewHolder();
            convertView = inflater();
            if (holderBase == null)
                throw new NullPointerException(" @libo ViewHolder is null");
            holderBase.inflaer(convertView);
            convertView.setTag(holderBase);
        } else {
            holderBase = (ViewHolderBase) convertView.getTag();
        }
        initView(position, getItem(position), holderBase, convertView);
        return convertView;
    }

    /**
     * 获取自定义ViewHolder
     *
     * @return
     */
    protected abstract ViewHolderBase getViewHolder();

    /**
     * 加载数据
     *
     * @param position
     * @param data
     * @param holderBase
     * @param convertView
     */
    protected abstract void initView(int position, T data, ViewHolderBase holderBase, View convertView);

    public interface ViewHolderBase {
        void inflaer(View convertView);
    }
}
