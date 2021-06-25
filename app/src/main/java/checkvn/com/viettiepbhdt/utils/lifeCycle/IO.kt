package checkvn.com.viettiepbhdt.utils.lifeCycle

import checkvn.com.viettiepbhdt.utils.printError
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext

private const val defaultTimeDelay = 200L
private const val timeToRetries = 3

suspend fun <T> io(
    delayTime: Long = defaultTimeDelay,
    retries: Int = timeToRetries,
    block: suspend () -> T
) {
    repeat(retries - 1) {
        try {
            if (!coroutineContext.isActive) throw Exception("Job canceled when trying to execute io")
            block()
            return@repeat
        } catch (exception: Exception) {
            printError(exception)
        }
        delay(delayTime)
    }
}