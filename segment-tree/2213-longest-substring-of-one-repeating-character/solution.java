// 2213. Longest Substring of One Repeating Character
// https://leetcode.com/problems/longest-substring-of-one-repeating-character/
// Hard | Java | Accepted 2026-08-15
// Runtime 124 ms | Memory 157.4 MB

class Solution {

   public record Node(int maxLen, int pref, int suff, char l, char r) {}
   
    String s;
    Node[] segmentTree;
    public int[] longestRepeating(String str, String queryCharacters, int[] queryIndices) {
        s = str;
        segmentTree = new Node[str.length()*4];
        buildTree(0, 0, str.length()-1);
        int[] ans = new int[queryIndices.length];
        for(int i = 0; i<queryIndices.length; i++)
        {
            int ind = queryIndices[i];
            char c = queryCharacters.charAt(i);
            updateTree(0, 0, str.length()-1, ind, c);
            ans[i] = segmentTree[0].maxLen();
        }
    return ans;
    }

    public void updateTree(int root, int l, int r, int ind, char ch)
    {
        if(l==r)
        {
            segmentTree[root] = new Node(1, 1, 1, ch, ch);
            return;
        }
        int mid = l + (r-l)/2;
        if(ind<=mid)
        {
            updateTree(2*root+1, l, mid, ind, ch);
        }
        else
        {
            updateTree(2*root+2, mid+1, r, ind, ch);
        }
        segmentTree[root] = merge(segmentTree[2*root+1], segmentTree[2*root+2], mid-l+1, r-mid);
    }

    public void buildTree(int root, int l, int r)
    {
        if(l==r)
        {
            segmentTree[root] = new Node(1, 1, 1, s.charAt(l), s.charAt(l));
            return;
        }
        int left = 2*root+1;
        int right = 2*root+2;
        int mid = l+(r-l)/2;
        buildTree(left, l, mid);
        buildTree(right, mid+1, r);
        segmentTree[root] = merge(segmentTree[left], segmentTree[right], mid-l+1, r-mid);
    }

    public Node merge(Node left, Node right, int leftLen, int rightLen)
    {
        Node root;
        int pre = left.pref();
        int suf = right.suff();
        if(leftLen == left.pref()&& left.r() == right.l())
        {
            pre = left.pref() + right.pref();
        }
        if(rightLen == right.suff()&& left.r() == right.l())
        {
            suf = left.suff() + right.suff();
        }
        int maxL = Math.max(left.maxLen(), right.maxLen());
        if(left.r() == right.l())
        {
            maxL = Math.max(maxL, left.suff() + right.pref());
        }
        root = new Node(maxL, pre, suf, left.l(), right.r());
        return root;
    }
}
