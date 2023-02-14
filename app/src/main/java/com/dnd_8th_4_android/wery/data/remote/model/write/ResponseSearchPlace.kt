package com.dnd_8th_4_android.wery.data.remote.model.write

data class ResponseSearchPlace(
    val documents: List<Document>,
    val meta: Meta
) {
    data class Meta(
        val is_end: Boolean,
        val pageable_count: Int,
        val total_count: Int
    )

    data class Document(
        val place_name: String,
        val road_address_name: String,
        val x: String,
        val y: String
    )
}