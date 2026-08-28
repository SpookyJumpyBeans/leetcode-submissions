// 438. Find All Anagrams in a String
// https://leetcode.com/problems/find-all-anagrams-in-a-string/
// Medium | C++ | Accepted 2026-08-07
// Runtime 3 ms | Memory 11.7 MB

class Solution {
public:
    vector<int> findAnagrams(string s, string p) {
        if(s.size()<p.size())
        {
            return vector<int>();
        }
        vector<int> countp(26);
        vector<int> counts(26);
        for(char i : p)
        {
            countp[i-'a']++;
        }
        vector<int> ans;
        for(int k = 0; k<p.size(); k++)
        {
            counts[s[k]-'a']++;
        }
        bool f = false;
        for(int k = 0; k<26; k++)
            {
                if(counts[k]!=countp[k])
                {
                    f = true;
                    break;
                }
            }
            if(!f)
            {
                ans.push_back(0);
            }
        int i = 0;
        int j = p.size();
        while(j<s.size())
        {
            counts[s[i]-'a']--;
            counts[s[j]-'a']++;
            bool flag = false;
            for(int k = 0; k<26; k++)
            {
                if(counts[k]!=countp[k])
                {
                    flag = true;
                    break;
                }
            }
            if(!flag)
            {
                ans.push_back(i+1);
            }
            i++;
            j++;
        }
        return ans;
    }
};
