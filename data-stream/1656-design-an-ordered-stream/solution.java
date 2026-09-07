// 1656. Design an Ordered Stream
// https://leetcode.com/problems/design-an-ordered-stream/
// Easy | Java | Accepted 2026-09-06
// Runtime 75 ms | Memory 47.6 MB

class OrderedStream {
    String[] stream;
    int pointer = 0;
    public OrderedStream(int n) {
        stream = new String[n];
    }
    
    public List<String> insert(int idKey, String value) {
        stream[idKey-1] = value;
        List<String> list = new ArrayList<>();
        while(pointer<stream.length && stream[pointer]!=null)
        {
            list.add(stream[pointer]);
            pointer++;
        }
        return list;
    }
}

/**
 * Your OrderedStream object will be instantiated and called as such:
 * OrderedStream obj = new OrderedStream(n);
 * List<String> param_1 = obj.insert(idKey,value);
 */
