package com.petclinic.owner.service

/*
import com.google.firestore.admin.v1.Index
import com.petclinic.OwnerIndexRequest
import com.petclinic.OwnerIndexResponse
import com.petclinic.OwnerIndexServiceGrpcKt
import com.petclinic.OwnerServiceProto
import com.petclinic.owner.model.Owner
import com.petclinic.vet.model.Specialty
import com.petclinic.vet.model.VetSpecialty
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import java.io.Closeable
*/

/*class IndexResponse(val status: Int, val message: String) {

    companion object {
        fun from(res: OwnerIndexResponse): IndexResponse {
            return IndexResponse(res.status, res.errorMessage)
        }
    }

}*/

/*
class OwnerIndexClient(private val channel: ManagedChannel) : Closeable {
    val indexServiceStub = OwnerIndexServiceGrpcKt.OwnerIndexServiceCoroutineStub(channel)


    constructor(
            channelBuilder: ManagedChannelBuilder<*>,
            dispatcher: CoroutineDispatcher
    ) : this(
            channelBuilder
                    .executor(dispatcher.asExecutor())
                    .build()
    )

    fun indexOwner(owner: Owner): IndexResponse = runBlocking {
        val ownerReq = OwnerIndexRequest.newBuilder()
                .setId(owner.id!!.toString())
                .setFirstName(owner.firstName)
                .setLastName(owner.lastName)
                .setAddress(owner.address)
                .setCity(owner.city)
                .setTelephone(owner.telephone)
//                .setPets() //                .setEmail(owner.ema)
//                .setState(owner.st)
                .build()
        println("indexing owner: $ownerReq")
        val res = indexServiceStub.indexOwner(ownerReq)
        IndexResponse.from(res)
    }

    override fun close() {
        channel.shutdown()
    }
}*/
