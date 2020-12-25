package subjects

import io.reactivex.rxjava3.observers.TestObserver
import io.reactivex.rxjava3.subjects.Subject

class Subject<T : Subject<Int>>(private val subject: T) {
    fun test(): TestObserver<Int> = subject.test()

    fun emit(value: Int) {
        subject.onNext(value)
    }

    fun complete() {
        subject.onComplete()
    }
}