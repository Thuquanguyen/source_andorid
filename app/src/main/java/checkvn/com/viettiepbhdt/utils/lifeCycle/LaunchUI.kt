package checkvn.com.viettiepbhdt.utils.lifeCycle

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

fun launchUI(strategy: CancelStrategy, block: suspend CoroutineScope.() -> Unit): Job =
    MainScope().launch(context = strategy.job, block = block)