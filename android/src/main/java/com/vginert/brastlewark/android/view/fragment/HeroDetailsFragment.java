package com.vginert.brastlewark.android.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fernandocejas.arrow.checks.Preconditions;
import com.squareup.picasso.Picasso;
import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.components.HeroComponent;
import com.vginert.brastlewark.android.presenter.HeroDetailsPresenter;
import com.vginert.brastlewark.android.view.HeroDetailsView;
import com.vginert.brastlewark.android.view.activity.ToolbarActivity;
import com.vginert.brastlewark.android.view.adapter.HeroesDetailListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment that shows details of a certain hero.
 *
 * @author Vicente Giner Tendero
 */

public class HeroDetailsFragment extends BaseFragment implements HeroDetailsView {

    private static final String PARAM_USER_ID = "param_hero_id";

    @Inject
    HeroDetailsPresenter heroDetailsPresenter;
    @Inject
    HeroesDetailListAdapter heroProfessionListAdapter;
    @Inject
    HeroesDetailListAdapter heroFriendsListAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_bar_title)
    TextView tv_bar_title;
    @BindView(R.id.tv_age)
    TextView tv_age;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_height)
    TextView tv_height;
    @BindView(R.id.tv_hair_color)
    TextView tv_hairColor;
    @BindView(R.id.rv_professions)
    RecyclerView rv_professions;
    @BindView(R.id.rv_friends)
    RecyclerView rv_friends;
    @BindView(R.id.ll_profession_layout)
    LinearLayout ll_profession_layout;
    @BindView(R.id.ll_friends_layout)
    LinearLayout ll_friends_layout;
    @BindView(R.id.ll_hero_data)
    LinearLayout ll_hero_data;
    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @BindString(R.string.retry_button_text)
    String retry_button_text;
    private Unbinder unbinder;

    private Snackbar retrySnackbar;

    public HeroDetailsFragment() {
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getComponent(HeroComponent.class).inject(this);
        final View fragmentView = inflater.inflate(R.layout.fragment_hero_detail, container, false);
        this.unbinder = ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.heroDetailsPresenter.setView(this);
        this.loadHeroDetails();
        Activity activity = getActivity();
        if (activity instanceof ToolbarActivity) {
            ToolbarActivity toolbarActivity = (ToolbarActivity) activity;
            toolbarActivity.setToolbar(this.toolbar);
            toolbarActivity.getToolbar().setDisplayHomeAsUpEnabled(true);
            toolbarActivity.getToolbar().setDisplayShowHomeEnabled(true);
            toolbarActivity.getToolbar().setTitle("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.heroDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.heroDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.rv_professions.setAdapter(null);
        this.rv_friends.setAdapter(null);
        this.unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.heroDetailsPresenter.destroy();
    }

    @Override
    public void renderHeroName(String name) {
        this.tv_name.setText(name);
        this.tv_bar_title.setText(name);
    }

    @Override
    public void renderHeroThumbnail(String thumbnailUrl) {
        Picasso.with(context())
                .load(thumbnailUrl)
                .resize(150, 150)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .into(this.iv_avatar);
    }

    @Override
    public void renderHeroAge(int age) {
        this.tv_age.setText(String.valueOf(age));
    }

    @Override
    public void renderHeroWeight(double weight) {
        this.tv_weight.setText(String.valueOf(weight));
    }

    @Override
    public void renderHeroHeight(double height) {
        this.tv_height.setText(String.valueOf(height));
    }

    @Override
    public void renderHeroHairColor(String hairColor) {
        this.tv_hairColor.setText(hairColor);
    }

    @Override
    public void renderHeroProfessions(List<String> professions) {
        if(professions != null) {
            this.heroProfessionListAdapter.setTitleItems(professions);
        }
    }

    @Override
    public void renderHeroFriends(List<String> friends) {
        if(friends != null) {
            this.heroFriendsListAdapter.setTitleItems(friends);
        }
    }

    @Override
    public void showProfessions() {
        this.ll_profession_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProfessions() {
        this.ll_profession_layout.setVisibility(View.GONE);
    }

    @Override
    public void showFriends() {
        this.ll_friends_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFriends() {
        this.ll_friends_layout.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        this.ll_hero_data.setVisibility(View.INVISIBLE);
        this.rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.ll_hero_data.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetry(String message) {
        View view = getView();
        if (view != null) {
            this.retrySnackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(this.retry_button_text, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HeroDetailsFragment.this.loadHeroDetails();
                        }
                    });
            this.retrySnackbar.show();
        }
    }

    @Override
    public void hideRetry() {
        if (this.retrySnackbar != null && this.retrySnackbar.isShown()) {
            this.retrySnackbar.dismiss();
        }
    }

    @NonNull
    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    /**
     * Load hero details.
     */
    private void loadHeroDetails() {
        if (this.heroDetailsPresenter != null) {
            this.heroDetailsPresenter.initialize(currentHeroId());
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager professionsLayoutManager = new LinearLayoutManager(context());
        this.rv_professions.setLayoutManager(professionsLayoutManager);
        this.rv_professions.setNestedScrollingEnabled(false);
        this.rv_professions.setAdapter(this.heroProfessionListAdapter);

        RecyclerView.LayoutManager friendsLayoutManager = new LinearLayoutManager(context());
        this.rv_friends.setLayoutManager(friendsLayoutManager);
        this.rv_friends.setNestedScrollingEnabled(false);
        this.rv_friends.setAdapter(this.heroFriendsListAdapter);
    }

    /**
     * Get current hero id from fragments arguments.
     */
    private int currentHeroId() {
        final Bundle arguments = getArguments();
        Preconditions.checkNotNull(arguments, "Fragment arguments cannot be null");
        return arguments.getInt(PARAM_USER_ID);
    }

    public static HeroDetailsFragment forHero(int heroId) {
        final HeroDetailsFragment heroDetailsFragment = new HeroDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_USER_ID, heroId);
        heroDetailsFragment.setArguments(arguments);
        return heroDetailsFragment;
    }
}
