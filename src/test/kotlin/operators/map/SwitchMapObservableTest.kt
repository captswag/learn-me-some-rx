package operators.map

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class SwitchMapObservableTest {

    private lateinit var switchMapObservable: MapObservable
    private lateinit var testScheduler: TestScheduler

    @BeforeEach
    fun setup() {
        switchMapObservable = SwitchMapObservable()
        testScheduler = TestScheduler()
    }

    @Test
    fun implementMap() {
        val testObserver: TestObserver<String> = switchMapObservable.implementMap(testScheduler).test()
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValue("5: Hi")
        testObserver.assertValueCount(1)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}