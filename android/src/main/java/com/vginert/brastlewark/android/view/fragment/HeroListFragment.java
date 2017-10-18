package com.vginert.brastlewark.android.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pchmn.materialchips.ChipView;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.TouchScrollBar;
import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.components.HeroComponent;
import com.vginert.brastlewark.android.model.HeroModel;
import com.vginert.brastlewark.android.presenter.HeroListPresenter;
import com.vginert.brastlewark.android.view.HeroListView;
import com.vginert.brastlewark.android.view.activity.HeroFilterActivity;
import com.vginert.brastlewark.android.view.activity.ToolbarActivity;
import com.vginert.brastlewark.android.view.adapter.HeroesAdapter;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment that shows a list of Heroes.
 *
 * @author Vicente Giner Tendero
 */
public class HeroListFragment extends BaseFragment implements HeroListView {

    private static int FILTER_REQUEST = 1;

    @Inject
    HeroListPresenter heroListPresenter;
    @Inject
    HeroesAdapter heroesAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.filter_toolbar)
    Toolbar filter_toolbar;
    @BindView(R.id.rv_heroes)
    RecyclerView rv_heroes;
    @BindView(R.id.dsb_rv_heroes)
    TouchScrollBar dsb_rv_heroes;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;
    @BindView(R.id.btn_remove_filters)
    ImageButton btn_remove_filters;
    @BindView(R.id.cp_name_filter)
    ChipView cp_name_filter;
    @BindView(R.id.cp_age_filter)
    ChipView cp_age_filter;
    @BindView(R.id.cp_weight_filter)
    ChipView cp_weight_filter;
    @BindView(R.id.cp_height_filter)
    ChipView cp_height_filter;
    @BindView(R.id.iv_error_image)
    ImageView iv_error_image;
    @BindView(R.id.tv_error_text)
    TextView tv_error_text;

    @BindString(R.string.retry_button_text)
    String retry_button_text;
    @BindString(R.string.range_text_separator)
    String range_text_separator;
    @BindString(R.string.weight_range_text)
    String weight_range_text;
    @BindString(R.string.height_range_text)
    String height_range_text;
    @BindString(R.string.hero_detail_name_chip_hint)
    String hero_detail_name_chip_hint;
    @BindString(R.string.hero_detail_age_chip_hint)
    String hero_detail_age_chip_hint;
    @BindString(R.string.hero_detail_weight_chip_hint)
    String hero_detail_weight_chip_hint;
    @BindString(R.string.hero_detail_height_chip_hint)
    String hero_detail_height_chip_hint;
    @BindString(R.string.hero_detail_from_chip_hint)
    String hero_detail_from_chip_hint;
    @BindString(R.string.hero_detail_to_chip_hint)
    String hero_detail_to_chip_hint;
    @BindDrawable(R.drawable.list_divider)
    Drawable list_divider;
    private Unbinder unbinder;

    private HeroListListener heroListListener;
    private Snackbar retrySnackbar;

    public HeroListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof HeroListListener) {
            this.heroListListener = (HeroListListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(HeroComponent.class).inject(this);
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_hero_list, container, false);
        this.unbinder = ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        setupChipViews();
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.heroListPresenter.setView(this);
        this.heroListPresenter.initialize();
        Activity activity = getActivity();
        if (activity instanceof ToolbarActivity) {
            ((ToolbarActivity) activity).setToolbar(this.toolbar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.heroListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.heroListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.rv_heroes.setAdapter(null);
        this.unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.heroListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.heroListListener = null;
    }

    @Override
    public void showLoading() {
        if (!this.swipe_refresh.isRefreshing()) {
            this.swipe_refresh.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        this.swipe_refresh.setRefreshing(false);
    }

    @Override
    public void showRetry(String message) {
        View view = getView();
        if (view != null) {
            this.retrySnackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(this.retry_button_text, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HeroListFragment.this.heroListPresenter.loadHeroList();
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

    @Override
    public void renderHeroList(Collection<HeroModel> heroModelCollection) {
        if (heroModelCollection != null) {
            this.heroesAdapter.setHeroesCollection(heroModelCollection);
        }
    }

    @Override
    public void viewHero(HeroModel heroModel) {
        if (this.heroListListener != null) {
            this.heroListListener.onHeroClicked(heroModel);
        }
    }

    @Override
    public void showFilterToolbar() {
        this.filter_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFilterToolbar() {
        this.filter_toolbar.setVisibility(View.GONE);
    }

    @Override
    public void showDeleteFilterButton() {
        this.btn_remove_filters.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeleteFilterButton() {
        this.btn_remove_filters.setVisibility(View.GONE);
    }

    @Override
    public void showNameChip(String name) {
        this.cp_name_filter.setLabel(hero_detail_name_chip_hint + name);
        this.cp_name_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNameChip() {
        this.cp_name_filter.setVisibility(View.GONE);
    }

    @Override
    public void showAgeChip(int from, int to) {
        String pluralFrom = getResources().getQuantityString(R.plurals.age_range_text, from);
        String pluralTo = getResources().getQuantityString(R.plurals.age_range_text, to);
        String textFrom = String.format(pluralFrom, from);
        String textTo = String.format(pluralTo, to);
        this.cp_age_filter.setLabel(hero_detail_age_chip_hint + textFrom + range_text_separator + textTo);
        this.cp_age_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFromAgeChip(int from) {
        String plural = getResources().getQuantityString(R.plurals.age_range_text, from);
        String text = String.format(plural, from);
        this.cp_age_filter.setLabel(hero_detail_age_chip_hint + hero_detail_from_chip_hint + text);
        this.cp_age_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToAgeChip(int to) {
        String plural = getResources().getQuantityString(R.plurals.age_range_text, to);
        String text = String.format(plural, to);
        this.cp_age_filter.setLabel(hero_detail_age_chip_hint + hero_detail_to_chip_hint + text);
        this.cp_age_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAgeChip() {
        this.cp_age_filter.setVisibility(View.GONE);
    }

    @Override
    public void showWeightChip(int from, int to) {
        String textFrom = String.format(this.weight_range_text, from);
        String textTo = String.format(this.weight_range_text, to);
        this.cp_weight_filter.setLabel(hero_detail_weight_chip_hint + textFrom + range_text_separator + textTo);
        this.cp_weight_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFromWeightChip(int from) {
        String text = String.format(this.weight_range_text, from);
        this.cp_weight_filter.setLabel(hero_detail_weight_chip_hint + hero_detail_from_chip_hint + text);
        this.cp_weight_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToWeightChip(int to) {
        String text = String.format(this.weight_range_text, to);
        this.cp_weight_filter.setLabel(hero_detail_weight_chip_hint + hero_detail_to_chip_hint + text);
        this.cp_weight_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWeightChip() {
        this.cp_weight_filter.setVisibility(View.GONE);
    }

    @Override
    public void showHeightChip(int from, int to) {
        String textFrom = String.format(this.height_range_text, from);
        String textTo = String.format(this.height_range_text, to);
        this.cp_height_filter.setLabel(hero_detail_height_chip_hint + textFrom + range_text_separator + textTo);
        this.cp_height_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFromHeightChip(int from) {
        String text = String.format(this.height_range_text, from);
        this.cp_height_filter.setLabel(hero_detail_height_chip_hint + hero_detail_from_chip_hint + text);
        this.cp_height_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToHeightChip(int to) {
        String text = String.format(this.height_range_text, to);
        this.cp_height_filter.setLabel(hero_detail_height_chip_hint + hero_detail_to_chip_hint + text);
        this.cp_height_filter.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHeightChip() {
        this.cp_height_filter.setVisibility(View.GONE);
    }

    @Override
    public void showNoResultsImage() {
        this.showErrorImage(R.drawable.sorry);
        this.tv_error_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorImage(int drawableId) {
        this.iv_error_image.setImageResource(drawableId);
        this.iv_error_image.setVisibility(View.VISIBLE);
        this.tv_error_text.setVisibility(View.GONE);
    }

    @Override
    public void hideErrorImage() {
        this.iv_error_image.setVisibility(View.GONE);
        this.tv_error_text.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                if (this.heroListListener != null) {
                    this.heroListListener.onFilterClicked(this, FILTER_REQUEST);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        // Set scrollbars
        this.dsb_rv_heroes.setIndicator(new AlphabetIndicator(context()), false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.rv_heroes.getContext(),
                linearLayoutManager.getOrientation());
        // Set dividers
        dividerItemDecoration.setDrawable(this.list_divider);
        this.rv_heroes.addItemDecoration(dividerItemDecoration);
        // Configure recycler adapter
        this.heroesAdapter.setOnItemClickListener(onItemClickListener);
        this.rv_heroes.setLayoutManager(linearLayoutManager);
        this.rv_heroes.setAdapter(heroesAdapter);
        // Configure swipe refresh layout
        this.swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HeroListFragment.this.heroListPresenter.loadHeroList();
            }
        });
        this.swipe_refresh.setColorSchemeResources(
                R.color.colorPrimary
        );
    }

    private void setupChipViews() {
        this.cp_name_filter.setOnDeleteClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeroListFragment.this.heroListPresenter.removeNameFilter();
            }
        });
        this.cp_age_filter.setOnDeleteClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeroListFragment.this.heroListPresenter.removeAgeFilter();
            }
        });
        this.cp_weight_filter.setOnDeleteClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeroListFragment.this.heroListPresenter.removeWeightFilter();
            }
        });
        this.cp_height_filter.setOnDeleteClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeroListFragment.this.heroListPresenter.removeHeightFilter();
            }
        });
    }

    @OnClick(R.id.btn_remove_filters)
    public void onRemoveFiltersClick() {
        this.heroListPresenter.removeFilters();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILTER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String name = data.getStringExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_NAME);
                Integer minAge = null;
                if (data.hasExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MIN_AGE)) {
                    minAge = data.getIntExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MIN_AGE, 0);
                }
                Integer maxAge = null;
                if (data.hasExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MAX_AGE)) {
                    maxAge = data.getIntExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MAX_AGE, 0);
                }
                Integer minWeight = null;
                if (data.hasExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MIN_WEIGHT)) {
                    minWeight = data.getIntExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MIN_WEIGHT, 0);
                }
                Integer maxWeight = null;
                if (data.hasExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MAX_WEIGHT)) {
                    maxWeight = data.getIntExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MAX_WEIGHT, 0);
                }
                Integer minHeight = null;
                if (data.hasExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MIN_HEIGHT)) {
                    minHeight = data.getIntExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MIN_HEIGHT, 0);
                }
                Integer maxHeight = null;
                if (data.hasExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MAX_HEIGHT)) {
                    maxHeight = data.getIntExtra(HeroFilterActivity.INTENT_EXTRA_PARAM_HERO_MAX_HEIGHT, 0);
                }
                this.heroListPresenter.setFilters(name, minAge, maxAge, minWeight, maxWeight,
                        minHeight, maxHeight);
            }
        }
    }

    /**
     * Interface for listening hero list events.
     */
    public interface HeroListListener {
        void onHeroClicked(final HeroModel heroModel);
        void onFilterClicked(final Fragment fragment, int requestCode);
    }

    private HeroesAdapter.OnItemClickListener onItemClickListener =
            new HeroesAdapter.OnItemClickListener() {
                @Override
                public void onHeroItemClicked(HeroModel heroModel) {
                    if (HeroListFragment.this.heroListPresenter != null && heroModel != null) {
                        HeroListFragment.this.heroListPresenter.onHeroClicked(heroModel);
                    }
                }
            };
}
