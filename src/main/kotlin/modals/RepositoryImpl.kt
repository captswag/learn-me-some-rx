package modals

import io.reactivex.rxjava3.core.Observable
import pojo.Chat
import java.util.concurrent.TimeUnit

class RepositoryImpl : Repository {

    override fun getInterval(): Observable<Long> = Observable.interval(2, TimeUnit.SECONDS)

    override fun getChatMessages(): Observable<Chat> {
        val id = (0..10).random()
        return Observable.just(Chat(id, getRandomMessage(id), getUnreadStatus(), getRating()))
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

    private fun getUnreadStatus(): Boolean {
        val flag = (0..1).random()
        return flag == 0
    }

    private fun getRating() = (0..5).random()
}