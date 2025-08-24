package problema
import java.io.BufferedReader
import java.util.PriorityQueue
fun main(args: Array<String>) {
    val startTime = System.currentTimeMillis()

    if (args.size < 2) {
        println("Error in input.")
        return
    }

    // Create a priority queue of elements that include both the line content and file name
    val queue = PriorityQueue<Pair<String?, BufferedReader>> { a, b ->
        a.first?.compareTo(b.first ?: "") ?: 1 // If b is null, an empty string will be used for comparison
    }

    // Add elements to the queue
    for (i in 1 until args.size) {
        val br = createReader(args[i])
        val word = br.readLine()
        queue.offer(Pair(word, br))
    }

    // Create output
    val output = createWriter(args[0])

    // Print first word and declare variables
    var line: String? = queue.peek().first
    var last: String? = null
    output.println(line)

    // Sort the files into the output
    while (queue.isNotEmpty() && queue.peek().first != null) {
        // Eliminate repetitions between iterations
        if (last == line) {
            queue.next(line)
            line = queue.peek()?.first
        }
        // Eliminate repetitions between different files
        while (queue.peek() != null && queue.peek().first != null && line == queue.peek().first && line != null) {
            // Skip over the consecutive repeated elements in the same file
            queue.next(line)
        }
        // Get the new smallest word
        line = queue.peek()?.first
        // Print it if it isn't null and advance to the next different word in the file
        if (line != null) {
            output.println(line)        // Print if it isn't null
            queue.next(line)            // Advance to the next different word in file
            last = queue.peek().first   // Save the first word of the new queue to compare in next iteration
        }
    }

    output.close()
    val endTime = System.currentTimeMillis()
    val elapsedTime = endTime - startTime

    println("Elapsed time: $elapsedTime milliseconds")
}

fun PriorityQueue<Pair<String?, BufferedReader>>.next(line: String?) {
    var memo: String? = line
    while (memo != null && line == memo) {
        val br = this.peek().second
        memo = br.readLine()
        if (memo != null) {
            this.offer(Pair(memo, br))
            this.poll()
        } else {
            this.poll()
            return
        }
    }
}
