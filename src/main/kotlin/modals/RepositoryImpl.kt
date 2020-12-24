package modals

import io.reactivex.rxjava3.core.Observable
import pojo.Chat
import java.util.concurrent.TimeUnit

const val CHAT_ID = 1
const val CHAT_STATUS = true
const val CHAT_RATING = 5

class RepositoryImpl : Repository {

    override fun getInterval(): Observable<Long> = Observable.interval(2, TimeUnit.SECONDS)

    override fun getChatMessages(random: Boolean): Observable<Chat> {
        val id: Int
        val unreadStatus: Boolean
        val rating: Int
        if (random) {
            id = (0..10).random()
            unreadStatus = (0..1).random() == 0
            rating = (0..5).random()
        } else {
            id = CHAT_ID
            unreadStatus = CHAT_STATUS
            rating = CHAT_RATING
        }
        return Observable.just(Chat(id, getRandomMessage(id), unreadStatus, rating))
    }

    private fun getRandomMessage(id: Int): String = when (id) {
        0 -> "And don't waste my time"
        1 -> "Many times"
        2 -> "For reminding me"
        3 -> "Thanks"
        4 -> "Like this one."
        5 -> "Are you not coding? I thought you were learning Python or some shit"
        6 -> "You fool"
        7 -> "Got any nudes lying around?"
        8 -> "Sorry, I forgot that you were brain damaged"
        9 -> "That's your problem"
        else -> "But the way you show off"
    }
}