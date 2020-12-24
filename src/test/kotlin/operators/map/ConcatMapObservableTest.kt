package operators.map

import io.reactivex.rxjava3.observers.TestObserver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ConcatMapObservableTest {

    private lateinit var concatMapObservable: MapObservable

    @BeforeEach
    fun setup() {
        concatMapObservable = ConcatMapObservable()
    }

    @Test
    fun implementMap() {
        val testObserver: TestObserver<String> = TestObserver()
        concatMapObservable.implementMap().blockingSubscribe(testObserver)
    }
}