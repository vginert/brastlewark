package com.vginert.brastlewark.domain.interactor;

import com.fernandocejas.arrow.checks.Preconditions;
import com.vginert.brastlewark.domain.Hero;
import com.vginert.brastlewark.domain.executor.IPostExecutionThread;
import com.vginert.brastlewark.domain.executor.IThreadExecutor;
import com.vginert.brastlewark.domain.repository.IHeroRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving data related to an specific {@link Hero}.
 *
 * @author Vicente Giner Tendero
 */
public class GetHeroDetails extends UseCase<Hero, GetHeroDetails.Params> {

    private final IHeroRepository heroRepository;

    @Inject
    GetHeroDetails(IHeroRepository heroRepository, IThreadExecutor threadExecutor,
                   IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.heroRepository = heroRepository;
    }

    @Override
    Observable<Hero> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.heroRepository.hero(params.heroId);
    }

    public static final class Params {

        private final int heroId;

        private Params(int heroId) {
            this.heroId = heroId;
        }

        public static Params forHero(int heroId) {
            return new Params(heroId);
        }
    }
}
