package client

import entity.SearchEngine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

open class SearchClient(
        val engine: SearchEngine,
        private val url: String
) {

    fun doRequest(query: String): String {
        val searchUrl = URL("$url?query=$query")
        val searchConnection = searchUrl.openConnection()
        val inStream = BufferedReader(InputStreamReader(searchConnection.getInputStream()))

        var jsonResponse = ""
        while (true) {
            jsonResponse += inStream.readLine() ?: break
        }
        inStream.close()

        return jsonResponse
    }
}