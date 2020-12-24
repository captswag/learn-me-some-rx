package operators.map

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class ConcatMapObservable : MapObservable() {
    override fun implementMap(): Observable<String> {
        return emitNumbers()
            .concatMap {
                println("concatMap $it")
                joinToString(it).delay((startRange..endRange).random().toLong(), TimeUnit.SECONDS)
            }
            .doOnNext {
                println("doOnNext $it")
            }
    }
}