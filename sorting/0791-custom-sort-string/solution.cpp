// 791. Custom Sort String
// https://leetcode.com/problems/custom-sort-string/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    string customSortString(string order, string s) {
        map<char, int> map;
        int count = 0;
        string other;
        for(char sss : s)
        {
            for(char t : order)
            {
                if(sss==t)
                {
                    map[sss]++;
                }
            }
            if(!order.contains(sss))
            {
                other+=sss;
            }
            count++;
        }


        string ans;
        cout<<other;
        for(char ss : order)
        {
            if(s.contains(ss))
            {
            for(int i = 0; i<map[ss]; i++)
            {
                ans+=ss;
            }
            }
        }
        ans+=other;
        return ans;
    }
};
