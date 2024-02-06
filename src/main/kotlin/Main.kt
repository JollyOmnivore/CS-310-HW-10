data class ColorNode(val color: String, val id: Int, val connections: MutableList<String>)

fun main() {
    println("Enter the filename: ")
    val fileName = readLine() ?: return
    val graph = mutableMapOf<String, ColorNode>()

    try {
        val lines = readFile(fileName)
        try {
            for (line in lines) {
                val (source, target) = parseColors(line)
                graph.getOrPut(source) { ColorNode(source, graph.size, mutableListOf()) }
                graph.getOrPut(target) { ColorNode(target, graph.size, mutableListOf()) }
                graph[source]?.connections?.add(target)
            }
            printColorGraph(graph)
            printUniqueColorComboCount(graph)
        }
        catch (e: Exception){
            println("Failed to iterate through file or NULL input")
        }
    } catch (e: Exception) {
        println("Failed to load file into variable")
    }
}

fun readFile(fileName: String): List<String> {
    return ClassLoader.getSystemResourceAsStream(fileName)?.bufferedReader()?.readLines()
        ?: throw error("File not found")
}

fun parseColors(line: String): Pair<String, String> {
    val parts = line.split("->")
    if (parts.size != 2) {
        throw error("Invalid format: $line")
    }
    return parts[0].trim() to parts[1].trim()
}

fun printColorGraph(graph: Map<String, ColorNode>) {
    println("==== GRAPH ====")
    for ((color, node) in graph) {
        println("$color:")
        node.connections.forEach { connection ->
            println("   => $connection")
        }
    }
}
fun removeColorInfo(color: String): String {
    val parts = color.split('_')
    var cleanColor = parts[0]
    return cleanColor
}
fun printUniqueColorComboCount(graph: Map<String, ColorNode>)  {
    println("==== COLOR COMBINATIONS ====")
    for ((color, node) in graph) {
        val colorName = removeColorInfo(color)
        val uniqueCombinations = node.connections.map { removeColorInfo(it) }.distinct()

        for (combination in uniqueCombinations) {
            var count = 0
            for (connection in node.connections) {
                if (removeColorInfo(connection) == combination) {
                    count++
                }
            }
            println("$colorName -> $combination: $count")
        }
    }
}

