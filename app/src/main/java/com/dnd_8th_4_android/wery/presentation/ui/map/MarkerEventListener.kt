package com.dnd_8th_4_android.wery.presentation.ui.map

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

// 마커 클릭 이벤트 리스너
class MarkerEventListener(val context: Context, val type: Int) : MapView.POIItemEventListener {
    override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
        // 마커 클릭 시
        if (type == 0) { // 피드 마커 일 때

        } else { // 미션 마커 일 때
            MapMissionBottomDialog().show((context as AppCompatActivity).supportFragmentManager, null)
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {}

    override fun onCalloutBalloonOfPOIItemTouched(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        buttonType: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(
        mapView: MapView?,
        poiItem: MapPOIItem?,
        mapPoint: MapPoint?
    ) {
    }
}