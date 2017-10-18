package com.vginert.brastlewark.android.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.turingtechnologies.materialscrollbar.INameableAdapter;
import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.model.HeroModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter that manages a collection of {@link HeroModel}.
 *
 * @author Vicente Giner Tendero
 */
public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.HeroViewHolder> implements INameableAdapter{

    public interface OnItemClickListener {
        void onHeroItemClicked(HeroModel heroModel);
    }

    private Context context;
    private List<HeroModel> heroesCollection;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    @Inject
    HeroesAdapter(Context context) {
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.heroesCollection = Collections.emptyList();
    }

    @Override
    public int getItemCount() {
        return (this.heroesCollection != null) ? this.heroesCollection.size() : 0;
    }

    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.row_hero, parent, false);
        return new HeroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HeroViewHolder holder, final int position) {
        final HeroModel heroModel = this.heroesCollection.get(position);
        Picasso.with(this.context)
                .load(heroModel.getThumbnail())
                .resize(150, 150)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(R.drawable.ic_profile_list)
                .error(R.drawable.ic_profile_list)
                .into(holder.iv_avatar);
        holder.tv_name.setText(heroModel.getName());
        holder.tv_hair_color.setText(heroModel.getHairColor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HeroesAdapter.this.onItemClickListener != null) {
                    HeroesAdapter.this.onItemClickListener.onHeroItemClicked(heroModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return this.heroesCollection.get(position).getId();
    }

    public void setHeroesCollection(Collection<HeroModel> heroesCollection) {
        this.validateHeroesCollection(heroesCollection);
        this.heroesCollection = (List<HeroModel>) heroesCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateHeroesCollection(Collection<HeroModel> heroesCollection) {
        if (heroesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    @Override
    public Character getCharacterForElement(int element) {
        if(element < 0) {
            return ' ';
        }
        return this.heroesCollection.get(element).getName().charAt(0);
    }


    static class HeroViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_avatar)
        CircleImageView iv_avatar;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_hair_color)
        TextView tv_hair_color;

        HeroViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
