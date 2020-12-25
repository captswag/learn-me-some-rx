package operators.combine

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

abstract class CombineObservable {
    fun emitNumbers1(scheduler: Scheduler): Observable<Int> =
        Observable.just(1, 2, 3)
            .flatMap {
                Observable.just(it).delay(getNumbersDelay(it), TimeUnit.SECONDS, scheduler)
            }

    fun emitNumbers2(scheduler: Scheduler): Observable<Int> =
        Observable.just(4, 5, 6)
            .flatMap {
                Observable.just(it).delay(getNumbersDelay(it), TimeUnit.SECONDS, scheduler)
            }

    fun emitNames(scheduler: Scheduler): Observable<String> =
        Observable.just("Anjith", "Swetha", "Biergarten", "Bhaskar")
            .flatMap {
                Observable.just(it).delay(getNamesDelay(it), TimeUnit.SECONDS, scheduler)
            }

    private fun getNumbersDelay(number: Int): Long = when (number) {
        1 -> 1
        2 -> 2
        3 -> 3
        4 -> 0
        5 -> 1
        6 -> 5
        else -> 1
    }

    private fun getNamesDelay(name: String): Long = when (name) {
        "Anjith" -> 2
        "Swetha" -> 4
        "Biergarten" -> 6
        "Bhaskar" -> 8
        else -> 10
    }

    abstract fun implementCombine(scheduler: Scheduler): Observable<Pair<Int, String>>
}