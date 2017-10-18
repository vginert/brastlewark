package com.vginert.brastlewark.domain.interactor;

import com.vginert.brastlewark.domain.Hero;
import com.vginert.brastlewark.domain.executor.IPostExecutionThread;
import com.vginert.brastlewark.domain.executor.IThreadExecutor;
import com.vginert.brastlewark.domain.repository.IHeroRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Hero}.
 *
 * @author Vicente Giner Tendero
 */
public class GetHeroList extends UseCase<List<Hero>, GetHeroList.Params> {

    private final IHeroRepository heroRepository;

    @Inject
    GetHeroList(IHeroRepository heroRepository, IThreadExecutor threadExecutor,
                IPostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.heroRepository = heroRepository;
    }

    @Override
    Observable<List<Hero>> buildUseCaseObservable(Params params) {
        return this.heroRepository.heroes(params.name, params.minAge, params.maxAge,
                params.minWeight, params.maxWeight, params.minHeight, params.maxHeight);
    }

    public static final class Params {

        private final String name;
        private final Integer minAge;
        private final Integer maxAge;
        private final Integer minWeight;
        private final Integer maxWeight;
        private final Integer minHeight;
        private final Integer maxHeight;

        private Params(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                       Integer minHeight, Integer maxHeight) {
            this.name = name;
            this.minAge = minAge;
            this.maxAge = maxAge;
            this.minWeight = minWeight;
            this.maxWeight = maxWeight;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        public static GetHeroList.Params forQuery(String name, Integer minAge, Integer maxAge, Integer minWeight, Integer maxWeight,
                                                  Integer minHeight, Integer maxHeight) {
            return new GetHeroList.Params(name, minAge, maxAge, minWeight, maxWeight, minHeight, maxHeight);
        }
    }
}
