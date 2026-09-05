// 953. Verifying an Alien Dictionary
// https://leetcode.com/problems/verifying-an-alien-dictionary/
// Easy | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    bool isAlienSorted(vector<string>& words, string order) {
        map<char, char> mapp;
        int ind = 0;
        for(char i = 'a'; i<='z'; i++)
        {
            mapp[order[ind]] = i;
            ind++;
        }
        for(int i = 0; i<words.size(); i++)
        {
            for(int j = 0; j<words[i].length(); j++)
            {
                 words[i][j] = mapp[words[i][j]];
            }
        }
        priority_queue<string, vector<string>, greater<string>> minHeap;
        for(int i = 0; i<words.size(); i++)
        {
            minHeap.push(words[i]);
        }   
        for(int i = 0; i<words.size(); i++)
        {
            string temp = minHeap.top();
            minHeap.pop();
            if(temp!=words[i])
            {
                return false;
            }
        }
        return true;
    }
};
