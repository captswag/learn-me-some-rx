package operators.map

import io.reactivex.rxjava3.observers.TestObserver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FlatMapObservableTest {

    private lateinit var flatMapObservable: MapObservable

    @BeforeEach
    fun setup() {
        flatMapObservable = FlatMapObservable()
    }

    @Test
    fun implementMap() {
        val testObserver: TestObserver<String> = TestObserver()
        flatMapObservable.implementMap().blockingSubscribe(testObserver)
    }
}