package modals

import io.reactivex.rxjava3.core.Observable
import pojo.Chat

interface Repository {
    fun getChatMessages(): Observable<Chat>
}