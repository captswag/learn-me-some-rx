package operators.map

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class FlatMapObservableTest {

    private lateinit var flatMapObservable: MapObservable
    private lateinit var testScheduler: TestScheduler

    @BeforeEach
    fun setup() {
        flatMapObservable = FlatMapObservable()
        testScheduler = TestScheduler()
    }

    @Test
    fun implementMap() {
        val valueList = mutableListOf("2: Henlo", "5: Hi")
        val testObserver: TestObserver<String> = flatMapObservable.implementMap(testScheduler).test()
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(2)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add("1: Hello")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(3)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add("4: Yola")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(4)

        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS)
        valueList.add("3: Hola")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(5)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}