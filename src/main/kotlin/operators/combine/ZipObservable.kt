package operators.combine

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler

class ZipObservable : CombineObservable() {
    override fun implementCombine(scheduler: Scheduler): Observable<Pair<Int, String>> {
        return Observable.zip(emitNumbers(scheduler), emitNames(scheduler)) { number, names ->
            number to names
        }.doOnNext { (number, name) ->
            println("number $number, name $name")
        }
    }
}