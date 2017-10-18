package com.vginert.brastlewark.android.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vginert.brastlewark.android.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter that manages a collection of {@link String}.
 *
 * @author Vicente Giner Tendero
 */

public class HeroesDetailListAdapter extends RecyclerView.Adapter<HeroesDetailListAdapter.HeroesDetailListViewHolder> {

    private List<String> titleItems;
    private final LayoutInflater layoutInflater;

    @Inject
    HeroesDetailListAdapter(Context context) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.titleItems = Collections.emptyList();
    }

    @Override
    public HeroesDetailListAdapter.HeroesDetailListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_hero_detail_title, parent, false);
        return new HeroesDetailListAdapter.HeroesDetailListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HeroesDetailListViewHolder holder, int position) {
        String title = this.titleItems.get(position);
        holder.tv_title.setText(title);
    }

    @Override
    public int getItemCount() {
        return (this.titleItems != null) ? this.titleItems.size() : 0;
    }

    public void setTitleItems(List<String> titleItems) {
        this.validateHeroesCollection(titleItems);
        this.titleItems = titleItems;
        this.notifyDataSetChanged();
    }

    private void validateHeroesCollection(Collection<String> titleCollection) {
        if (titleCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class HeroesDetailListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;

        public HeroesDetailListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
