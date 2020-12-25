package operators.map

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class ConcatMapObservableTest {

    private lateinit var concatMapObservable: MapObservable
    private lateinit var testScheduler: TestScheduler

    @BeforeEach
    fun setup() {
        concatMapObservable = ConcatMapObservable()
        testScheduler = TestScheduler()
    }

    @Test
    fun implementMap() {
        val valueList = mutableListOf("1: Hello")
        val testObserver: TestObserver<String> = concatMapObservable.implementMap(testScheduler).test()
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add("2: Henlo")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(2)

        testScheduler.advanceTimeBy(7, TimeUnit.SECONDS)
        valueList.add("3: Hola")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(3)

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)
        valueList.add("4: Yola")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(4)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add("5: Hi")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(5)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}