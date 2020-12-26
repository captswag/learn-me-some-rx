package transformer

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.LinkedList
import java.util.Queue
import kotlin.Comparator

class MapIndexPair<T>(private val index: Int, private val value: T) {
    fun index(): Int = index
    fun value(): T = value

    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean {
        return try {
            (other as MapIndexPair<T>).let {
                it.index == this.index && it.value == this.value
            }
        } catch (exception: ClassCastException) {
            return false
        }
    }
}

class MapWithIndex<T> : ObservableTransformer<T, MapIndexPair<T>> {
    private var index = 0
    override fun apply(upstream: Observable<T>): ObservableSource<MapIndexPair<T>> {
        return upstream.map { value ->
            MapIndexPair(index++, value)
        }
    }
}

class Async<T> : ObservableTransformer<T, T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.io())
    }
}

// I haven't tested the below with same elements and the streams are expected to be sorted by default
class OrderedMergeWith<T>(private val upstream2: Observable<T>, private val comparator: Comparator<T>) :
    ObservableTransformer<T, T> {
    override fun apply(upstream1: Observable<T>): ObservableSource<T> {
        return Observable.create { emitter ->
            val queue1: Queue<T> = LinkedList()
            val queue2: Queue<T> = LinkedList()

            var readyToFlush: Boolean = false
            upstream1.doOnNext {
                queue1.add(it)
                compareQueues(queue1, queue2, comparator, emitter)
            }.doOnComplete {
                if (readyToFlush) {
                    flushBoth(queue1, queue2, emitter)
                }
                readyToFlush = true
            }.subscribe()

            upstream2.doOnNext {
                queue2.add(it)
                compareQueues(queue1, queue2, comparator, emitter)
            }.doOnComplete {
                if (readyToFlush) {
                    flushBoth(queue1, queue2, emitter)
                }
                readyToFlush = true
            }.subscribe()
        }
    }

    // Remove the smallest element of the two
    private fun compareQueues(
        queue1: Queue<T>,
        queue2: Queue<T>,
        comparator: Comparator<T>,
        emitter: ObservableEmitter<T>
    ) {
        while (queue1.peek() != null && queue2.peek() != null) {
            val element1 = queue1.peek()
            val element2 = queue2.peek()
            val output = comparator.compare(element1, element2)
            if (output > 0) {
                queue2.remove()
                emitter.onNext(element2)
            } else {
                queue1.remove()
                emitter.onNext(element1)
            }
        }
    }

    private fun flushBoth(queue1: Queue<T>, queue2: Queue<T>, emitter: ObservableEmitter<T>) {
        while (queue1.peek() != null) {
            emitter.onNext(queue1.poll())
        }
        while (queue2.peek() != null) {
            emitter.onNext(queue2.poll())
        }
        emitter.onComplete()
    }
}