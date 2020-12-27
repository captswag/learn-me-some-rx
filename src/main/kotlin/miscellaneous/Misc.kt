package miscellaneous

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class Misc(private val scheduler: Scheduler) {
    fun maybeWithoutEmission(): Maybe<Int> = Maybe.create { emitter ->
        emitter.onComplete()
    }

    fun observableTake(): Observable<Int> = Observable.interval(2, TimeUnit.SECONDS, scheduler)
        .flatMap {
            println("flatMap $it")
            Observable.just(1)
        }
        .take(2)
        .map {
            println("map $it")
            it + 1
        }

    fun singleError(): Single<Int> = Single.error(ArithmeticException("Arithmetic Exception"))

    fun singleSuccess(): Single<Int> = Single.just(1)

    fun singleToCompletable(): Completable = singleSuccess().ignoreElement()
}