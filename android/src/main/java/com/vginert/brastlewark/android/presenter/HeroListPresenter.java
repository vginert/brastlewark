package com.vginert.brastlewark.android.presenter;

import android.support.annotation.NonNull;

import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.android.exception.ErrorImageFactory;
import com.vginert.brastlewark.android.exception.ErrorMessageFactory;
import com.vginert.brastlewark.android.model.HeroModel;
import com.vginert.brastlewark.android.model.mapper.HeroModelDataMapper;
import com.vginert.brastlewark.android.view.HeroListView;
import com.vginert.brastlewark.domain.Hero;
import com.vginert.brastlewark.domain.exception.DefaultErrorBundle;
import com.vginert.brastlewark.domain.exception.IErrorBundle;
import com.vginert.brastlewark.domain.interactor.GetHeroList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
public class HeroListPresenter implements Presenter {

    private HeroListView viewListView;

    private final GetHeroList getHeroListUseCase;
    private final HeroModelDataMapper heroModelDataMapper;
    private final ErrorMessageFactory errorMessageFactory;
    private final ErrorImageFactory errorImageFactory;

    private String nameFilter = null;
    private Integer minAgeFilter = null;
    private Integer maxAgeFilter = null;
    private Integer minWeightFilter = null;
    private Integer maxWeightFilter = null;
    private Integer minHeightFilter = null;
    private Integer maxHeightFilter = null;

    private Collection<HeroModel> loadedItems = null;
    private IErrorBundle lastError = null;

    @Inject
    public HeroListPresenter(GetHeroList getHeroListHeroCase,
                             HeroModelDataMapper heroModelDataMapper,
                             ErrorMessageFactory errorMessageFactory,
                             ErrorImageFactory errorImageFactory) {
        this.getHeroListUseCase = getHeroListHeroCase;
        this.heroModelDataMapper = heroModelDataMapper;
        this.errorMessageFactory = errorMessageFactory;
        this.errorImageFactory = errorImageFactory;
    }

    public void setView(@NonNull HeroListView view) {
        this.viewListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getHeroListUseCase.dispose();
        this.viewListView = null;
    }

    /**
     * Initializes the presenter by start retrieving the hero list.
     */
    public void initialize() {
        this.updateFilterViews();
        if (this.loadedItems != null) {
            this.showHeroesCollectionInView(this.loadedItems);
        } else if (this.lastError != null) {
            this.showViewRetry(this.lastError);
        } else {
            this.loadHeroList();
        }
    }

    /**
     * Loads all heroes.
     */
    public void loadHeroList() {
        this.getHeroList();
    }

    public void onHeroClicked(HeroModel heroModel) {
        this.viewListView.viewHero(heroModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry(IErrorBundle errorBundle) {
        if (errorBundle != null) {
            this.showHeroesCollectionInView(Collections.EMPTY_LIST);
            int drawableId = this.errorImageFactory.create(errorBundle.getException());
            this.viewListView.showErrorImage(drawableId);
            String errorMessage = this.errorMessageFactory.create(errorBundle.getException());
            this.viewListView.showRetry(errorMessage);
        }
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showHeroesCollectionInView(Collection<HeroModel> heroModelsCollection) {
        if (heroModelsCollection != null) {
            this.loadedItems = heroModelsCollection;
            if (heroModelsCollection.isEmpty()) {
                this.viewListView.showNoResultsImage();
            } else {
                this.viewListView.hideErrorImage();
            }
            this.viewListView.renderHeroList(heroModelsCollection);
        }
    }

    public void removeFilters() {
        this.nameFilter = null;
        this.minAgeFilter = null;
        this.maxAgeFilter = null;
        this.minWeightFilter = null;
        this.maxWeightFilter = null;
        this.minHeightFilter = null;
        this.maxHeightFilter = null;
        this.updateFilterViews();
        this.loadHeroList();
    }

    public void removeNameFilter() {
        this.nameFilter = null;
        this.updateFilterViews();
        this.loadHeroList();
    }

    public void removeAgeFilter() {
        this.minAgeFilter = null;
        this.maxAgeFilter = null;
        this.updateFilterViews();
        this.loadHeroList();
    }

    public void removeWeightFilter() {
        this.minWeightFilter = null;
        this.maxWeightFilter = null;
        this.updateFilterViews();
        this.loadHeroList();
    }

    public void removeHeightFilter() {
        this.minHeightFilter = null;
        this.maxHeightFilter = null;
        this.updateFilterViews();
        this.loadHeroList();
    }

    public void setFilters(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                           Integer minHeight, Integer maxHeight) {
        this.nameFilter = name;
        this.minAgeFilter = minAge;
        this.maxAgeFilter = maxAge;
        this.minWeightFilter = minWeight;
        this.maxWeightFilter = maxWeight;
        this.minHeightFilter = minHeight;
        this.maxHeightFilter = maxHeight;
        this.updateFilterViews();
        this.loadHeroList();
    }

    private void updateFilterViews() {
        int filterNumber = 0;
        if (this.nameFilter != null) {
            this.viewListView.showNameChip(this.nameFilter);
            filterNumber++;
        } else {
            this.viewListView.hideNameChip();
        }

        if (this.minAgeFilter != null && this.maxAgeFilter != null) {
            this.viewListView.showAgeChip(this.minAgeFilter, this.maxAgeFilter);
            filterNumber++;
        } else if (this.minAgeFilter != null) {
            this.viewListView.showFromAgeChip(this.minAgeFilter);
            filterNumber++;
        } else if (this.maxAgeFilter != null) {
            this.viewListView.showToAgeChip(this.maxAgeFilter);
            filterNumber++;
        } else {
            this.viewListView.hideAgeChip();
        }

        if (this.minWeightFilter != null && this.maxWeightFilter != null) {
            this.viewListView.showWeightChip(this.minWeightFilter, this.maxWeightFilter);
            filterNumber++;
        } else if (this.minWeightFilter != null) {
            this.viewListView.showFromWeightChip(this.minWeightFilter);
            filterNumber++;
        } else if (this.maxWeightFilter != null) {
            this.viewListView.showToWeightChip(this.maxWeightFilter);
            filterNumber++;
        } else {
            this.viewListView.hideWeightChip();
        }

        if (this.minHeightFilter != null && this.maxHeightFilter != null) {
            this.viewListView.showHeightChip(this.minHeightFilter, this.maxHeightFilter);
            filterNumber++;
        } else if (this.minHeightFilter != null) {
            this.viewListView.showFromHeightChip(this.minHeightFilter);
            filterNumber++;
        } else if (this.maxHeightFilter != null) {
            this.viewListView.showToHeightChip(this.maxHeightFilter);
            filterNumber++;
        } else {
            this.viewListView.hideHeightChip();
        }

        if (filterNumber > 0) {
            this.viewListView.showFilterToolbar();
            if (filterNumber > 1) {
                this.viewListView.showDeleteFilterButton();
            } else {
                this.viewListView.hideDeleteFilterButton();
            }
        } else {
            this.viewListView.hideFilterToolbar();
        }
    }

    private void getHeroList() {
        this.getHeroListUseCase.execute(new HeroListObserver(),
                GetHeroList.Params.forQuery(
                        this.nameFilter,
                        this.minAgeFilter,
                        this.maxAgeFilter,
                        this.minWeightFilter,
                        this.maxWeightFilter,
                        this.minHeightFilter,
                        this.maxHeightFilter
                ));
    }

    private final class HeroListObserver extends DisposableObserver<List<Hero>> {

        @Override
        protected void onStart() {
            HeroListPresenter.this.hideViewRetry();
            HeroListPresenter.this.showViewLoading();
        }

        @Override
        public void onComplete() {
            HeroListPresenter.this.hideViewLoading();
            HeroListPresenter.this.lastError = null;
        }

        @Override
        public void onError(Throwable e) {
            HeroListPresenter.this.loadedItems = null;
            HeroListPresenter.this.lastError = new DefaultErrorBundle((Exception) e);
            HeroListPresenter.this.hideViewLoading();
            HeroListPresenter.this.showViewRetry(HeroListPresenter.this.lastError);
        }

        @Override
        public void onNext(List<Hero> heroes) {
            HeroListPresenter.this.loadedItems = HeroListPresenter.this.heroModelDataMapper.transform(heroes);
            HeroListPresenter.this.showHeroesCollectionInView(HeroListPresenter.this.loadedItems);
        }
    }
}
