package modals

import io.reactivex.rxjava3.core.Observable
import pojo.Chat

interface Repository {
    fun getInterval(): Observable<Long>
    fun getChatMessages(): Observable<Chat>
}