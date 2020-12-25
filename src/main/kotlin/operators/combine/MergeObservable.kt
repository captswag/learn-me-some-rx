package operators.combine

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

class MergeObservable : CombineObservable() {
    override fun implementCombine(scheduler: Scheduler): Observable<Pair<Int, String>> {
        return Observable.merge(emitNumbers1(scheduler), emitNumbers2(scheduler))
            .map { it to "random" }
            .doOnNext {
                println("number $it.first")
            }
    }
}