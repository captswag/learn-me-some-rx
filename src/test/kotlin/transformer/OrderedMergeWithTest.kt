package transformer

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class OrderedMergeWithTest {

    private lateinit var testScheduler: TestScheduler
    private lateinit var testObserver: TestObserver<Int>

    @BeforeEach
    fun setup() {
        testScheduler = TestScheduler()
        testObserver = firstStream()
            .compose(OrderedMergeWith(secondStream(), comparator))
            .doOnNext {
                println(it)
            }.test()
    }

    @Test
    fun transformerOrderedMergeWith() {
        val expectedList: MutableList<Int> = mutableListOf(3, 5)

        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testObserver.assertValues(*expectedList.toTypedArray())
        testObserver.assertValueCount(expectedList.size)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        expectedList.add(6)
        expectedList.add(7)
        testObserver.assertValues(*expectedList.toTypedArray())
        testObserver.assertValueCount(expectedList.size)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        expectedList.add(9)
        expectedList.add(10)
        testObserver.assertValues(*expectedList.toTypedArray())
        testObserver.assertValueCount(expectedList.size)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }

    private fun firstStream(): Observable<Int> = Observable.just(5, 7)
        .flatMap {
            Observable.just(it).delay(getDelay(it), TimeUnit.SECONDS, testScheduler)
        }

    private fun secondStream(): Observable<Int> = Observable.just(3, 6, 9, 10)
        .flatMap {
            Observable.just(it).delay(getDelay(it), TimeUnit.SECONDS, testScheduler)
        }

    private val comparator = Comparator<Int> { element1, element2 ->
        when {
            element1 == element2 -> {
                0
            }
            element1 > element2 -> {
                1
            }
            else -> {
                -1
            }
        }
    }

    private fun getDelay(element: Int): Long = when (element) {
        3 -> 1
        6 -> 2
        9 -> 3
        5 -> 4
        7 -> 5
        10 -> 6
        else -> 0
    }
}