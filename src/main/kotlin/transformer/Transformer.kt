package transformer

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

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
    var index = 0
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