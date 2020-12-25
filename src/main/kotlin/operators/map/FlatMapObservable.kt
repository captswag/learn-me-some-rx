package operators.map

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import java.util.concurrent.TimeUnit

class FlatMapObservable : MapObservable() {
    override fun implementMap(scheduler: Scheduler): Observable<String> {
        return emitNumbers()
            .flatMap {
                val delay = getRandomDelay(it)
                println("flatMap $it, seconds $delay")
                joinToString(it).delay(delay, TimeUnit.SECONDS, scheduler)
            }
            .doOnNext {
                println("doOnNext $it")
            }
    }
}