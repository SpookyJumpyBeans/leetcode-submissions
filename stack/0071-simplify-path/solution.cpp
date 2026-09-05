// 71. Simplify Path
// https://leetcode.com/problems/simplify-path/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    string simplifyPath(string path) {
        vector<string> s;     
        stringstream ss(path);
        string token;

    while (getline(ss, token, '/')) {
        if(token=="" || token==".")
        {
            continue;
        }
        else if(token=="..")
        {
            if(s.size()>0)
            {
                s.pop_back();
            }
        }
        else
        {
            s.push_back(token);
        }
    }
    string ans = "/";
    for(const string& temp : s)
    {
        ans+=temp;
        ans+="/";
    }
    if(ans.length()>1)
    {
    ans.pop_back();
    }
    return ans;
    
    }
};
