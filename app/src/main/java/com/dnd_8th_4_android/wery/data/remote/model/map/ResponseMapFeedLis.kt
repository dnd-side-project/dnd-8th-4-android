import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMapFeedList(
    val data: List<ResultMapFeedData>,
) : BaseResponse() {
    data class ResultMapFeedData(
        val id: Int,
        val groupId: Int,
        val counts:Int,
        val latitude: Double,
        val longitude: Double,
        val userId: Int,
        val contentImageUrl:String,
        val location:String
    )
}