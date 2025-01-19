
fun String.removeChatComment(): String {
    val startTag = "```kotlin"
    val endTag = "```"

    val startIndex = indexOf(startTag)
    val endIndex = indexOf(endTag, startIndex + startTag.length)

    return if (startIndex != -1 && endIndex != -1) {
        substring(startIndex + startTag.length, endIndex).trim()
    } else {
        ""
    }
}
