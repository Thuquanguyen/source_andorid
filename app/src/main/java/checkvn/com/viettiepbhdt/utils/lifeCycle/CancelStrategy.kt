package checkvn.com.viettiepbhdt.utils.lifeCycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.Job

class CancelStrategy(owner: LifecycleOwner, val job: Job) : LifecycleObserver {

    init {
        owner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        job.cancel()
    }

}