package subjects

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.AsyncSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AsyncSubjectTest {

    private lateinit var asyncSubject: Subject<AsyncSubject<Int>>

    @BeforeEach
    fun setup() {
        asyncSubject = Subject(AsyncSubject.create())
    }

    @Test
    fun emissionTest() {
        val testObserver1: TestObserver<Int> = asyncSubject.test()
        asyncSubject.emit(1)
        asyncSubject.emit(2)
        asyncSubject.emit(3)
        val testObserver2: TestObserver<Int> = asyncSubject.test()
        asyncSubject.emit(4)
        asyncSubject.complete()

        testObserver1.assertValue(4)
        testObserver1.assertValueCount(1)
        testObserver1.assertNoErrors()
        testObserver1.assertComplete()
        testObserver1.dispose()

        testObserver2.assertValue(4)
        testObserver2.assertValueCount(1)
        testObserver2.assertNoErrors()
        testObserver2.assertComplete()
        testObserver2.dispose()
    }
}