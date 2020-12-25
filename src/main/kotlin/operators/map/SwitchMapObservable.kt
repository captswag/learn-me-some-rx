package operators.map

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

class SwitchMapObservable : MapObservable() {
    override fun implementMap(scheduler: Scheduler): Observable<String> {
        return emitNumbers()
            .switchMap {
                val delay = getRandomDelay(it)
                println("switchMap $it, seconds $delay")
                joinToString(it).delay(delay, TimeUnit.SECONDS, scheduler)
            }
            .doOnNext {
                println("doOnNext $it")
            }
    }
}