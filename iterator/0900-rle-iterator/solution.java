// 900. RLE Iterator
// https://leetcode.com/problems/rle-iterator/
// Medium | Java | Accepted 2026-10-12
// Runtime 4 ms | Memory 43.9 MB

class RLEIterator {
    int[] iterator;
    int currInd = 0;
    public RLEIterator(int[] encoding) {
        iterator = encoding;
    }
    
    public int next(int n) {
        while(currInd<iterator.length && n>0)
        {
            int temp = n;
            int val = iterator[currInd+1];
            n = Math.max(0, n-iterator[currInd]);
            iterator[currInd] = Math.max(0, iterator[currInd]-temp);
            if(iterator[currInd]==0)
            {
                currInd+=2;
            }
            if(n==0)
            {
                return val;
            }
        }
        return -1;
    }
}

/**
 * Your RLEIterator object will be instantiated and called as such:
 * RLEIterator obj = new RLEIterator(encoding);
 * int param_1 = obj.next(n);
 */
