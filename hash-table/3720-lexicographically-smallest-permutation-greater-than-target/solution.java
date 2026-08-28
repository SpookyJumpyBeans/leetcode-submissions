// 3720. Lexicographically Smallest Permutation Greater Than Target
// https://leetcode.com/problems/lexicographically-smallest-permutation-greater-than-target/
// Medium | Java | Accepted 2026-08-27
// Runtime 8 ms | Memory 46.7 MB

/*
class Solution {
    String ans = "";
    boolean[] visited;
    String temp;
    StringBuilder targ;

    //TRIED BACKTRACKING
    //BACKTRACKING ONLY WORKS IF THE CONSTRAINTS ARE N<=20
    public String lexGreaterPermutation(String s, String target) {
        visited = new boolean[s.length()];
        temp = s;
        targ = new StringBuilder(target);
        recurse(new StringBuilder());
        return ans;
    }

    public void recurse(StringBuilder currString)
    {
        if(currString.length()==temp.length())
        {
            if(ans.length()==0 &&currString.compareTo(targ)>0)
            {
                ans = currString.toString();
            }
            else
            {
                int best = currString.toString().compareTo(ans);
                if(best<0)
                {
                    ans = currString.toString();
                }
            }
          return;
        }
        for(int i = 0; i<temp.length(); i++)
        {
            if(currString.length()>0 && currString.toString().compareTo(targ.substring(0, currString.length()))<0)
            {
                continue;
            }
            if(!visited[i])
            {
                visited[i] = true;
                currString.append(temp.charAt(i));
                recurse(currString);
                currString.deleteCharAt(currString.length()-1);
                visited[i] = false;
            }
        }
    }
}
*/

class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] count = new int[26];
        for(int i = 0; i<s.length(); i++)
        {
            count[s.charAt(i)-'a']++;
        }
        int ind = 0;
        StringBuilder savedString = new StringBuilder();
        int indStart = -1;
        StringBuilder ans = new StringBuilder();
        int[] temp = new int[26];
        while(ind<target.length())
        {
            int letter = target.charAt(ind)-'a';
            if(count[letter]>0)
            {
                for(int i = target.charAt(ind)-'a'+1; i<26; i++)
                {
                    if(count[i]>0)
                    {
                        savedString = new StringBuilder(ans.toString());
                        indStart = i;
                        temp = count.clone();
                        break;
                    }
                }
                ans.append((char)(letter+'a'));
                count[target.charAt(ind)-'a']--;
                ind++;
            }
            else
            {
                boolean flag = false;
                for(int i = target.charAt(ind)-'a'+1; i<26; i++)
                {
                    if(count[i]>0)
                    {
                        ans.append((char)(i+'a'));
                        count[i]--;
                        flag = true;
                        break;
                    }
                }
                System.out.println(ans);
                for(int i = 0; i<26; i++)
                {
                    ans.repeat((char)(i+'a'), count[i]);
                }
                break;
            }
        }
        if(ans.toString().compareTo(target)>0)
        {
            return ans.toString();
        }
        if(indStart==-1)
        {
            return "";
        }
        savedString.append((char)(indStart+'a'));
        temp[indStart]--;
        for(int i = 0; i<26; i++)
        {
            if(temp[i]>0)
            {
                savedString.repeat((char)(i+'a'), temp[i]);
            }
        }
        return savedString.toString();
    }
}
