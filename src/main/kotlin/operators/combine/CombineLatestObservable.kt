package operators.combine

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

class CombineLatestObservable : CombineObservable() {
    override fun implementCombine(scheduler: Scheduler): Observable<Pair<Int, String>> {
        return Observable.combineLatest(emitNumbers1(scheduler), emitNames(scheduler)) { number, name ->
            number to name
        }.doOnNext { (number, name) ->
            println("number $number, name $name")
        }
    }
}