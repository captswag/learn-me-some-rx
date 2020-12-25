package subjects

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.ReplaySubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReplaySubjectTest {

    private lateinit var replaySubject: Subject<ReplaySubject<Int>>

    @BeforeEach
    fun setup() {
        replaySubject = Subject(ReplaySubject.create())
    }

    @Test
    fun emissionTest() {
        val testObserver1: TestObserver<Int> = replaySubject.test()
        replaySubject.emit(1)
        replaySubject.emit(2)
        replaySubject.emit(3)
        val testObserver2: TestObserver<Int> = replaySubject.test()
        replaySubject.emit(4)
        replaySubject.complete()

        testObserver1.assertValues(1, 2, 3, 4)
        testObserver1.assertValueCount(4)
        testObserver1.assertNoErrors()
        testObserver1.assertComplete()
        testObserver1.dispose()

        testObserver2.assertValues(1, 2, 3, 4)
        testObserver2.assertValueCount(4)
        testObserver2.assertNoErrors()
        testObserver2.assertComplete()
        testObserver2.dispose()
    }
}