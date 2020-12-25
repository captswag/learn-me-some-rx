package operators.combine

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class CombineLatestObservableTest {

    private lateinit var combineLatestObservable: CombineObservable
    private lateinit var testScheduler: TestScheduler

    @BeforeEach
    fun setup() {
        combineLatestObservable = CombineLatestObservable()
        testScheduler = TestScheduler()
    }

    @Test
    fun implementCombine() {
        val valueList = mutableListOf(2 to "Anjith")
        val testObserver: TestObserver<Pair<Int, String>> =
            combineLatestObservable.implementCombine(testScheduler).test()
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add(3 to "Anjith")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(2)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        valueList.add(3 to "Swetha")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(3)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        valueList.add(3 to "Biergarten")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(4)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        valueList.add(3 to "Bhaskar")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(5)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}