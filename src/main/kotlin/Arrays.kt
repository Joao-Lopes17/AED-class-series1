data class Point(var x:Int, var y:Int)

fun count(v: Array<Int>, l: Int, r: Int, element: Int): Int {
        if(v.isEmpty()) return 0
        if(l>r) return -1
        val mid = (l+r)/2
        var rightI = 0
        var leftI = 0
        if(v[mid] > element){
            rightI = upperBound(v,l,mid-1,element)
            leftI = lowerBound(v,l,mid-1,element)
        }
        else if(v[mid] < element){
            rightI = upperBound(v,mid+1, r, element)
            leftI = lowerBound(v,mid+1, r, element)
        }
        else {
            rightI = upperBound(v,l,r,element)
            leftI = lowerBound(v,l,r,element)
        }
        return rightI - leftI + 1
}

fun minAbsSum(ar:Array<Int>): Pair<Int, Int>? {
    if(ar.isEmpty() || ar[0] == ar[ar.size-1]) return null
    var sum = 0
    var i = 0
    var j = ar.size-1
    var k = ar[0] + ar[j]
    if(k<0) k = -k
    var P = Pair(ar[i], ar[j])
    var modsum: Int
    while(i<j){
        sum = ar[i] + ar[j]
        if(sum<0) modsum = -sum else modsum = sum
        if(k > modsum) {
            k = modsum
            P = Pair(ar[i], ar[j])
        }
        when {
            sum == 0 -> return Pair(ar[i], ar[j])
            sum < 0 -> i++
            sum > 0 -> j--
        }
    }
    return P
}

fun countSubKSequences(a: Array<Int>, k: Int): Int {
    val s = a.size
    var count = 0
    var sum = 0
    for(i in 0 .. s - k){
        sum = 0
        for(j in i .. k + i - 1){
            sum += a[j]
        }
        if(sum % k == 0) count++
    }
    return count
}

fun countEquals( points1: Array<Point>, points2: Array<Point>, cmp: (p1:Point, p2:Point )-> Int): Int {
    if(points1.isEmpty() || points2.isEmpty()) return 0
    var i = 0
    var j = 0
    var count = 0
    val s1 = points1.size
    val s2 = points2.size
    while(i < s1 && j < s2) {
        when {
            cmp(points1[i], points2[j]) > 0 -> j++
            cmp(points1[i], points2[j]) < 0 -> i++
            cmp(points1[i], points2[j]) == 0 -> {
                count++
                if (i < s1 - 1) i++ else return count
                if (j < s2 - 1) j++ else return count
            }
        }
    }
    return count
}

fun lowerBound(v: Array<Int>, l: Int, r: Int, element: Int): Int{
    var left = l
    var right = r
    while(left<=right) {
        val mid = (left + right) / 2
        if (v[mid] < element) {
            left = mid + 1
        } else right = mid - 1
    }
    return left
}
fun upperBound(v: Array<Int>, l: Int, r: Int, element: Int): Int {
    var left = l
    var right = r
    while(left<=right) {
        val mid = (left+ right) / 2
        if (v[mid] <= element) {
            left = mid + 1
        } else right = mid - 1
    }
    return right
}






