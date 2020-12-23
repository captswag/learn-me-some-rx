import modals.RepositoryImpl

fun main() {
    val repository = RepositoryImpl()
    repository.getChatMessages() // Without a take() operator, this will go on forever since getChatMessages is an interval
        .blockingSubscribe { chat ->
            println(chat.message)
        }
}