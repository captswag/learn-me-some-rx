package miscellaneous

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class MiscTest {

    private lateinit var testScheduler: TestScheduler
    private lateinit var misc: Misc

    @BeforeEach
    fun setup() {
        testScheduler = TestScheduler()
        misc = Misc(testScheduler)
    }

    @Test
    fun maybeWithoutEmission() {
        val testObserver: TestObserver<Int> = misc.maybeWithoutEmission()
            .test()

        testObserver.assertNoValues()
        testObserver.assertValueCount(0)
        testObserver.assertComplete()
        testObserver.dispose()
    }

    @Test
    fun observableTake() {
        val expectedList = mutableListOf(2)
        val testObserver: TestObserver<Int> = misc.observableTake()
            .test()

        testObserver.assertNoValues()

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        testObserver.assertValues(*expectedList.toTypedArray())

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        expectedList.add(2)
        testObserver.assertValues(*expectedList.toTypedArray())

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }

    @Test
    fun singleError() {
        val testObserver: TestObserver<Int> = misc.singleError().test()

        testObserver.assertError(ArithmeticException::class.java)
        testObserver.dispose()
    }

    @Test
    fun singleSuccess() {
        val testObserver: TestObserver<Int> = misc.singleSuccess().test()

        testObserver.assertValue(1)
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
        testObserver.dispose()
    }

    @Test
    fun singleToCompletable() {
        val testObserver: TestObserver<Void> = misc.singleToCompletable().test()

        testObserver.assertComplete()
        testObserver.dispose()
    }
}