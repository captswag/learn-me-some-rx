package transformer

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.TestObserver
import org.junit.jupiter.api.Test

internal class MapWithIndexTest {

    @Test
    fun transformerMapWithIndex() {
        val nameList = arrayOf("Anjith", "Bhaskar", "Manu", "Swetha", "Jithin")
        val testObserver: TestObserver<MapIndexPair<String>> = Observable.fromArray(*nameList)
            .compose(MapWithIndex())
            .doOnNext {
                println("index ${it.index()}, value ${it.value()}")
            }
            .test()
        val expectedList = nameList.mapIndexed { index, name ->
            MapIndexPair(index, name)
        }

        testObserver.assertValues(*expectedList.toTypedArray())
        testObserver.assertValueCount(5)
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.dispose()
    }
}