package modals

import io.reactivex.rxjava3.observers.TestObserver
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pojo.Chat

internal class RepositoryImplTest {

    private lateinit var repository: Repository

    @BeforeEach
    fun setup() {
        repository = RepositoryImpl()
    }

    @Test
    fun getChatMessages() {
        val testObserver: TestObserver<Chat> = repository.getChatMessages(false)
            .test()

        val message = "Many times"
        val chat = Chat(CHAT_ID, message, CHAT_STATUS, CHAT_RATING)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1) // valueCount is the number of emissions
        testObserver.assertValue(chat) // If there are multiple emissions, use assertValues()
        // Cleans up resources, not compulsory, but STRONGLY recommended
        testObserver.dispose()
    }
}