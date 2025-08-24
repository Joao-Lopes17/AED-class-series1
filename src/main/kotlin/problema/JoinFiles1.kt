package problema
import java.io.BufferedReader
data class File(val br: BufferedReader, var word: String?)
data class PriorityQ(val heap: Array<File?>, var size: Int = 0)
fun main(array: Array<String>) {
    val startTime = System.currentTimeMillis()

    if (array.size < 2) {
        println("Error in input.")
        return
    }

    // Create a priority queue of elements that include both the line content and file name
    val queue = PriorityQ(heap = arrayOfNulls(array.size - 1), size = 0)

    // Add elements to the queue
    for (i in 1 until array.size) {
        val br = createReader((array[i]))
        val word = br.readLine()
        queue.offer(File(br, word))
    }
    // Create output
    val output = createWriter(array[0])

    // Print first word and declare variables
    var line: String? = queue.heap[0]!!.word
    var last: String? = null
    output.println(line)

    // Sort the files into the output
    while (queue.heap[0]?.word != null) {
        // Eliminate repetitions between iterations
        if (last == line) {
            queue.next(line)
            if(queue.heap[0]!!.word != null){
                line = queue.heap[0]!!.word
            } else{
                //output.println(line)
                break
            }
        }
        // Eliminate repetitions between different files
        while (line == queue.heap[0]?.word && queue.heap[0]?.word != null && line != null) {
            // Skip over the consecutive repeated elements in the same file
            queue.next(line)
        }
        // Get the new smallest word
        line = queue.heap[0]?.word
        // Print it if it isn't null and advance to the next different word in the file
        if (line != null) {
            output.println(line)        // Print if it isn't null
            queue.next(line)            // Advance to the next different word in file
            last = queue.heap[0]?.word  // Save the first word of the new queue to compare in next iteration
        }
    }
    output.close()
    val endTime = System.currentTimeMillis()
    val elapsedTime = endTime - startTime

    println("Elapsed time: $elapsedTime milliseconds")
}

fun PriorityQ.next(line: String?) {
    var memo: String? = line
    while (heap[0]!!.word != null && line == memo) {
        memo = heap[0]!!.br.readLine()
        if (memo == null) {
            heap[0]!!.word = null
        }else if (memo != line) {
            heap[0]!!.word = memo
        }
    }
    minHeapify(0)
}

fun PriorityQ.minHeapify(pos: Int) {
    val l = left(pos)
    val r = right(pos)
    var p = pos
    if (l < size && heap[l]!!.word != null && (heap[p]!!.word == null || heap[l]!!.word!! < heap[p]!!.word!!)) {
        p = l
    }
    if (r < size && heap[r]!!.word != null && (heap[p]!!.word == null || heap[r]!!.word!! < heap[p]!!.word!!)) {
        p = r
    }
    if (p != pos) {
        exchange(heap, p, pos)
        minHeapify(p)
    } else if (heap[p]!!.word == null && p != size - 1) {
        exchange(heap, p, size - 1)
        size--
        minHeapify(pos)
    }
}

fun PriorityQ.offer(f: File?): Boolean {
    if (heap.size == size) return false
    heap[size++] = f
    decreaseKey()
    return true
}

fun PriorityQ.decreaseKey() {
    var pai = parent(size - 1)
    var pos = size - 1
    while (pai >= 0 && heap[pos] != null && heap[pai] != null && heap[pos]!!.word!! < heap[pai]!!.word!!) {
        exchange(heap, pos, pai)
        pos = pai
        pai = parent(pos)
    }
}

fun exchange(array: Array<File?>, idx1: Int, idx2: Int) {
    val temp = array[idx1]
    array[idx1] = array[idx2]
    array[idx2] = temp
}
fun left(i: Int) = 2 * i + 1
fun right(i: Int) = 2 * i + 2
fun parent(i: Int) = (i - 1) / 2


