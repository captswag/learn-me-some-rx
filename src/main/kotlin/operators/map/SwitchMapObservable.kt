package operators.map

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class SwitchMapObservable : MapObservable() {
    override fun implementMap(): Observable<String> {
        return emitNumbers()
            .switchMap {
                println("switchMap $it")
                joinToString(it).delay(getRandomDelay(it), TimeUnit.SECONDS)
            }
            .doOnNext {
                println("doOnNext $it")
            }
    }
}