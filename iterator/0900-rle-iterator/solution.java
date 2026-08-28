// 900. RLE Iterator
// https://leetcode.com/problems/rle-iterator/
// Medium | Java | Accepted 2026-08-10
// Runtime 4 ms | Memory 43.9 MB

class RLEIterator {
    int[] encoded;
    int index = 0;
    public RLEIterator(int[] encoding) {
        encoded = encoding;
    }
    
    public int next(int n) {
        int last = -1;
       while(n>0)
       {
        if(index>=encoded.length)
        {
            return -1;
        }
        if(encoded[index]>0)
        {
            last = encoded[index+1];
            int temp = encoded[index];
            encoded[index] = Math.max(0, encoded[index]-n); //1
            n = Math.max(0, n-temp);
        }
        if(encoded[index]<=0)
        {
            index+=2;
        }
       }
       return last;
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(encoding);
 * int param_1 = obj.next(n);
 */
