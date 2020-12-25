package operators.combine

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class ZipObservableTest {

    private lateinit var zipObservable: CombineObservable
    private lateinit var testScheduler: TestScheduler

    @BeforeEach
    fun setup() {
        zipObservable = ZipObservable()
        testScheduler = TestScheduler()
    }

    @Test
    fun implementCombine() {
        val valueList = mutableListOf(1 to "Anjith")
        val testObserver: TestObserver<Pair<Int, String>> = zipObservable.implementCombine(testScheduler).test()
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        valueList.add(2 to "Swetha")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(2)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        valueList.add(3 to "Biergarten")
        testObserver.assertValues(*valueList.toTypedArray())
        testObserver.assertValueCount(3)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}