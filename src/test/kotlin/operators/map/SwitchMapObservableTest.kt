package operators.map

import io.reactivex.rxjava3.observers.TestObserver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SwitchMapObservableTest {

    private lateinit var switchMapObservable: MapObservable

    @BeforeEach
    fun setup() {
        switchMapObservable = SwitchMapObservable()
    }

    @Test
    fun implementMap() {
        val testObserver: TestObserver<String> = TestObserver()
        switchMapObservable.implementMap().blockingSubscribe(testObserver)
    }
}