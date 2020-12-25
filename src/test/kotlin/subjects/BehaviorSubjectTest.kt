package subjects

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BehaviorSubjectTest {

    private lateinit var behaviorSubject: Subject<BehaviorSubject<Int>>

    @BeforeEach
    fun setup() {
        behaviorSubject = Subject(BehaviorSubject.create())
    }

    @Test
    fun emissionTest() {
        val testObserver1: TestObserver<Int> = behaviorSubject.test()
        behaviorSubject.emit(1)
        behaviorSubject.emit(2)
        behaviorSubject.emit(3)
        val testObserver2: TestObserver<Int> = behaviorSubject.test()
        behaviorSubject.emit(4)
        behaviorSubject.complete()

        testObserver1.assertValues(1, 2, 3, 4)
        testObserver1.assertValueCount(4)
        testObserver1.assertNoErrors()
        testObserver1.assertComplete()
        testObserver1.dispose()

        testObserver2.assertValues(3, 4)
        testObserver2.assertValueCount(2)
        testObserver2.assertNoErrors()
        testObserver2.assertComplete()
        testObserver2.dispose()
    }
}