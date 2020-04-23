package ro.weekendrrsapps.sdk.networking

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

object Request {

    const val JKEY_PROTOCOL = "jsonrpc"
    const val JKEY_ID = "id"
    const val JKEY_PARAMS = "params"
    const val JKEY_METHOD = "method"

    const val JVAL_PROTOCOL = "2.0"

    var requestId = 0

    fun getRequestData(method: String, params: JSONObject?): JSONObject {
        val requestData = JSONObject()
        try {
            requestData.put(JKEY_PROTOCOL, JVAL_PROTOCOL)
            requestData.put(JKEY_ID, ++requestId)
            requestData.put(JKEY_METHOD, method)
            requestData.put(JKEY_PARAMS, params ?: JSONObject())
        } catch (ex: JSONException) {
            Log.e("Request", "Failed to build request:${ex.message}")
        }
        return requestData
    }
}