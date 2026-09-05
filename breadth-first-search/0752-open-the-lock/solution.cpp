// 752. Open the Lock
// https://leetcode.com/problems/open-the-lock/
// Medium | C++ | Accepted on NeetCode 2026-09-04

class Solution {
public:
    int openLock(vector<string>& deadends, string target) {
        unordered_set<string> visited(deadends.begin(), deadends.end());
        
        if (visited.contains("0000")) return -1;
        if (target == "0000") return 0; // Check target immediately
        
        queue<pair<string, int>> q;
        q.push({"0000", 0});
        visited.insert("0000");
        
        while (!q.empty()) {
            auto [curr, steps] = q.front(); // Structured binding is cleaner
            q.pop();
            
            // Mutate this single string container in-place to avoid heap allocations
            string neighbor = curr; 
            int next_steps = steps + 1;
            
            for (int i = 0; i < 4; i++) {
                char original_char = neighbor[i];
                
                // Spin Forward
                neighbor[i] = (original_char == '9') ? '0' : original_char + 1;
                if (neighbor == target) return next_steps;
                if (!visited.contains(neighbor)) {
                    visited.insert(neighbor);
                    q.push({neighbor, next_steps});
                }
                
                // Spin Backward
                neighbor[i] = (original_char == '0') ? '9' : original_char - 1;
                if (neighbor == target) return next_steps;
                if (!visited.contains(neighbor)) {
                    visited.insert(neighbor);
                    q.push({neighbor, next_steps});
                }
                
                // Restore character for the next wheel position iteration
                neighbor[i] = original_char; 
            }
        }
        return -1;
    }
};
