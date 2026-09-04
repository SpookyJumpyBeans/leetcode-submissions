// 140. Word Break II
// https://leetcode.com/problems/word-break-ii/
// Hard | Java | Accepted 2026-09-03
// Runtime 0 ms | Memory 43.4 MB

class Solution {
    List<String> ans = new ArrayList<>();
    String str;
    Set<String> dict;
    public List<String> wordBreak(String s, List<String> wordDict) {
        dict = new HashSet<>(wordDict);
        str = s;
        StringBuilder string = new StringBuilder();
        recurse(string, 0);
        return ans;
    }

    public void recurse(StringBuilder curr, int ind)
    {
        if(ind==str.length())
        {
            ans.add(curr.toString().substring(0, curr.toString().length()-1));
            return;
        }
        for(int i = ind+1; i<=str.length(); i++)
        {
            if(dict.contains(str.substring(ind, i)))
            {
                StringBuilder temp = new StringBuilder(curr.toString());
                curr.append(str.substring(ind, i));
                curr.append(" ");
                recurse(curr, i);
                curr = temp;
            }
        }
    }
}
