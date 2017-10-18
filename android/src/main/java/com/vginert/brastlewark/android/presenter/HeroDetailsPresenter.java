package com.vginert.brastlewark.android.presenter;

import android.support.annotation.NonNull;
import android.webkit.URLUtil;

import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.android.exception.ErrorMessageFactory;
import com.vginert.brastlewark.android.model.HeroModel;
import com.vginert.brastlewark.android.model.mapper.HeroModelDataMapper;
import com.vginert.brastlewark.android.view.HeroDetailsView;
import com.vginert.brastlewark.domain.Hero;
import com.vginert.brastlewark.domain.exception.DefaultErrorBundle;
import com.vginert.brastlewark.domain.exception.IErrorBundle;
import com.vginert.brastlewark.domain.interactor.GetHeroDetails;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
public class HeroDetailsPresenter implements Presenter {

    private HeroDetailsView viewDetailsView;

    private final GetHeroDetails getHeroDetailsUseCase;
    private final HeroModelDataMapper heroModelDataMapper;
    private final ErrorMessageFactory errorMessageFactory;

    @Inject
    public HeroDetailsPresenter(GetHeroDetails getHeroDetailsUseCase,
                                HeroModelDataMapper heroModelDataMapper,
                                ErrorMessageFactory errorMessageFactory) {
        this.getHeroDetailsUseCase = getHeroDetailsUseCase;
        this.heroModelDataMapper = heroModelDataMapper;
        this.errorMessageFactory = errorMessageFactory;
    }

    public void setView(@NonNull HeroDetailsView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        this.getHeroDetailsUseCase.dispose();
        this.viewDetailsView = null;
    }

    /**
     * Initializes the presenter by showing/hiding proper views
     * and retrieving hero details.
     */
    public void initialize(int heroId) {
        this.hideViewRetry();
        this.showViewLoading();
        this.getHeroDetails(heroId);
    }

    private void getHeroDetails(int heroId) {
        this.getHeroDetailsUseCase.execute(new HeroDetailsObserver(), GetHeroDetails.Params.forHero(heroId));
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry(IErrorBundle errorBundle) {
        String errorMessage = this.errorMessageFactory.create(errorBundle.getException());
        this.viewDetailsView.showRetry(errorMessage);
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

    private void showHeroDetailsInView(Hero hero) {
        final HeroModel heroModel = this.heroModelDataMapper.transform(hero);
        if (heroModel != null) {
            this.viewDetailsView.renderHeroName(heroModel.getName());
            String thumbnailUrl = heroModel.getThumbnail();
            if (URLUtil.isValidUrl(thumbnailUrl)) {
                this.viewDetailsView.renderHeroThumbnail(thumbnailUrl);
            }
            this.viewDetailsView.renderHeroAge(heroModel.getAge());
            this.viewDetailsView.renderHeroWeight(heroModel.getWeight());
            this.viewDetailsView.renderHeroHeight(heroModel.getHeight());
            this.viewDetailsView.renderHeroHairColor(heroModel.getHairColor());
            if (heroModel.getProfessions() != null && !heroModel.getProfessions().isEmpty()) {
                this.viewDetailsView.showProfessions();
                this.viewDetailsView.renderHeroProfessions(heroModel.getProfessions());
            } else {
                this.viewDetailsView.hideProfessions();
            }
            if (heroModel.getFriends() != null && !heroModel.getFriends().isEmpty()) {
                this.viewDetailsView.showFriends();
                this.viewDetailsView.renderHeroFriends(heroModel.getFriends());
            } else {
                this.viewDetailsView.hideFriends();
            }
        }
    }

    private final class HeroDetailsObserver extends DisposableObserver<Hero> {

        @Override
        public void onComplete() {
            HeroDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            HeroDetailsPresenter.this.hideViewLoading();
            HeroDetailsPresenter.this.showViewRetry(new DefaultErrorBundle((Exception) e));
        }

        @Override
        public void onNext(Hero hero) {
            HeroDetailsPresenter.this.showHeroDetailsInView(hero);
        }
    }
}
