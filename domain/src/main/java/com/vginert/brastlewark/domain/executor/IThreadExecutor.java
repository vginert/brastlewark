package com.vginert.brastlewark.domain.executor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link com.vginert.brastlewark.domain.interactor.UseCase} out of the UI thread.
 *
 * @author Vicente Giner Tendero
 */
public interface IThreadExecutor extends Executor {
}
