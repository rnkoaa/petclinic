package com.petclinic.common

import com.google.api.core.ApiFutureCallback
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.QuerySnapshot
import com.google.cloud.firestore.WriteResult
import reactor.core.publisher.MonoSink


class ApiFutureCallbackImpl(val sink: MonoSink<WriteResult>) : ApiFutureCallback<WriteResult> {
    override fun onFailure(t: Throwable?) {
        sink.error(t!!)
    }

    override fun onSuccess(result: WriteResult?) {
        sink.success(result)
    }
}


class ApiFutureQuerySnapshotCallbackImpl(val sink: MonoSink<QuerySnapshot>) : ApiFutureCallback<QuerySnapshot> {
    override fun onFailure(t: Throwable?) {
        sink.error(t!!)
    }

    override fun onSuccess(result: QuerySnapshot?) {
        sink.success(result)
    }
}

class ApiFutureDocumentSnapshotCallbackImpl(val sink: MonoSink<DocumentSnapshot>) : ApiFutureCallback<DocumentSnapshot> {
    override fun onFailure(t: Throwable?) {
        sink.error(t!!)
    }

    override fun onSuccess(result: DocumentSnapshot?) {
        sink.success(result)
    }
}
