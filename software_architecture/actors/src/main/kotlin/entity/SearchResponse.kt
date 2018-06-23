package entity

data class SearchResponse(val engine: SearchEngine, val response: String) {
    override fun toString(): String {
        return "SearchResponse($engine: '${prettifyResponse(response)}')"
    }

    companion object {
        fun prettifyResponse(response: String) =
                response.replace(" ", "")
                        .replace("[", "[\n")
                        .replace("]", "]\n")
                        .replace("{", "    {")
                        .replace("},", "},\n")
    }
}