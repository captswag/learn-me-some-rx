package operators.combine

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class MergeObservableTest {

    private lateinit var mergeObservable: CombineObservable
    private lateinit var testScheduler: TestScheduler

    @BeforeEach
    fun setup() {
        mergeObservable = MergeObservable()
        testScheduler = TestScheduler()
    }

    @Test
    fun implementCombine() {
        val valueList = mutableListOf(4 to "random")
        val testObserver: TestObserver<Pair<Int, String>> = mergeObservable.implementCombine(testScheduler).test()
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS) // Not sure why delayTime of 0 doesn't work
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(999, TimeUnit.MILLISECONDS) // 1 MILLISECOND + 999 MILLISECOND = 1 SECOND
        valueList.add(1 to "random")
        valueList.add(5 to "random")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(3)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add(2 to "random")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(4)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add(3 to "random")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(5)

        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)
        valueList.add(6 to "random")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(6)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}