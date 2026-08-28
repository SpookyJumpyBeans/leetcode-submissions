// 131. Palindrome Partitioning
// https://leetcode.com/problems/palindrome-partitioning/
// Medium | Java | Accepted 2025-12-19
// Runtime 8 ms | Memory 65.4 MB

class Solution {
    List<List<String>> ans = new ArrayList<>();
    String glob;
    public List<List<String>> partition(String s) {
        glob = s;
        recurse(new ArrayList<>(), 0);
        return ans;
    }

    public void recurse(List<String> temp, int index)
    {
        if(index==glob.length())
        {
            ans.add(new ArrayList<>(temp));
            return;
        }
        for(int i = index; i<glob.length(); i++)
        {
            String s = glob.substring(index, i+1);
            if(palindrome(s))
            {
                temp.add(s);
                recurse(temp, i+1);
                temp.remove(temp.size()-1);
            }
        }
    }

    public boolean palindrome(String check)
    {
        int pointer1 = 0;
        int pointer2 = check.length()-1;
        while(pointer1<=pointer2)
        {
            if(check.charAt(pointer1)!=check.charAt(pointer2))
            {
                return false;
            }
            pointer1++;
            pointer2--;
        }
        return true;
    }
}
