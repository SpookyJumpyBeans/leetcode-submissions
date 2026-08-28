// 567. Permutation in String
// https://leetcode.com/problems/permutation-in-string/
// Medium | C++ | Accepted 2026-08-11
// Runtime 0 ms | Memory 9.9 MB

class Solution {
public:
    bool checkInclusion(string s1, string s2) {
        vector<int> s2C(26);
        vector<int> s1C(26);
        if(s2.size()<s1.size())
        {
            return false;
        }
        for(char s : s1)
        {
            s1C[s-'a']++;
        }
        for(int i = 0; i<s1.size(); i++)
        {
            s2C[s2[i]-'a']++;
        }
        bool flag = true;
        for(int i = 0; i<26; i++)
        {
            if(s1C[i]>0 && s2C[i]<s1C[i])
            {
                flag = false;
                break;
            }
        }
        if(flag)
        {
            return true;
        }
        int i = 0; 
        int j = s1.size();
        bool flag1 = true;
        while(j<s2.size())
        {
            s2C[s2[i]-'a']--;
            s2C[s2[j]-'a']++;
            for(int i = 0; i<26; i++)
        {
            if(s1C[i]>0 && s2C[i]<s1C[i])
            {
                flag1 = false;
                break;
            }
        }
        if(flag1)
        {
            return true;
        }
        flag1 = true;
        i++;
        j++;
        }
        return false;
    }
};
