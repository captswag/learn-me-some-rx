package operators.map

import io.reactivex.rxjava3.core.Observable

abstract class MapObservable {
    // Never change the below value
    fun emitNumbers(): Observable<Int> = Observable.just(1, 2, 3, 4, 5)
    fun joinToString(number: Int): Observable<String> {
        println("joinToString: $number")
        return Observable.just(getRandomString(number))
    }

    abstract fun implementMap(): Observable<String>

    private fun getRandomString(number: Int): String = "$number: ${
        when (number) {
            1 -> "Hello"
            2 -> "Henlo"
            3 -> "Hola"
            4 -> "Yola"
            5 -> "Hi"
            else -> "Vamanos"
        }
    }"

    fun getRandomDelay(number: Int): Long = when (number) {
        1 -> 2
        2 -> 1
        3 -> 7
        4 -> 3
        else -> 1
    }
}