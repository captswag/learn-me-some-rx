package subjects

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PublishSubjectTest {

    private lateinit var publishSubject: Subject<PublishSubject<Int>>

    @BeforeEach
    fun setup() {
        publishSubject = Subject(PublishSubject.create())
    }

    @Test
    fun emissionTest() {
        val testObserver1: TestObserver<Int> = publishSubject.test()
        publishSubject.emit(1)
        publishSubject.emit(2)
        publishSubject.emit(3)
        val testObserver2: TestObserver<Int> = publishSubject.test()
        publishSubject.emit(4)
        publishSubject.complete()

        testObserver1.assertValues(1, 2, 3, 4)
        testObserver1.assertValueCount(4)
        testObserver1.assertNoErrors()
        testObserver1.assertComplete()
        testObserver1.dispose()

        testObserver2.assertValue(4)
        testObserver2.assertValueCount(1)
        testObserver2.assertNoErrors()
        testObserver1.assertComplete()
        testObserver2.dispose()
    }
}