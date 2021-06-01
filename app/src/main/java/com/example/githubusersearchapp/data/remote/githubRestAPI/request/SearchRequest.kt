package com.example.githubusersearchapp.data.remote.githubRestAPI.request

/**
 * Name	Type In Description
 * accept string header
 * Setting to application/vnd.github.v3+json is recommended.
 *
 * q string query
 * The query contains one or more search keywords and qualifiers. Qualifiers allow you to limit your search to specific areas of GitHub. The REST API supports the same qualifiers as GitHub.com. To learn more about the format of the query, see Constructing a search query. See "Searching users" for a detailed list of qualifiers.
 * sort	string query
 * Sorts the results of your query by number of followers or repositories, or when the person joined GitHub. Default: best match
 * order string query
 *
 * Determines whether the first search result returned is the highest number of matches (desc) or lowest number of matches (asc). This parameter is ignored unless you provide sort.
 * Default: desc
 *
 * per_page	integer	query
 * Results per page (max 100).
 * Default: 30
 *
 * page	integer	query
 * Page number of the results to fetch.
 * Default: 1
 */

data class SearchRequest(
    val q: String,
    val sort: String = SearchRequestSort.DEFAULT.sort,
    val order: String = SearchRequestOrder.DESC.order,
    val perPage: Int = 100,
    val page: Int = 1
)

enum class SearchRequestOrder(val order: String) {
    DESC("desc"),
    ASC("asc")
}

enum class SearchRequestSort(val sort: String) {
    DEFAULT("default"),
    FOLLOWERS("followers"),
    REPOSITORIES("repositories"),
    JOINED("joined")
}