// 981. Time Based Key-Value Store
// https://leetcode.com/problems/time-based-key-value-store/
// Medium | C++ | Accepted 2026-08-09
// Runtime 55 ms | Memory 137 MB

class TimeMap {
public:
    unordered_map<string, vector<pair<string ,int>>>timeMap;
    unordered_map<string, int> low;
    TimeMap() {
        
    }
    
    void set(string key, string value, int timestamp){
        timeMap[key].push_back(pair<string, int>{value, timestamp});
    }
    
    string get(string key, int timestamp) {
       if(!timeMap.contains(key)||timeMap[key].empty())
       {
        return "";
       }
       const auto& temp = timeMap[key];
       int l = 0;
       int r = temp.size()-1;
       string ans = "";
       while(l<=r)
       {
         int mid = (l+r)/2;
         if(temp[mid].second>timestamp)
         {
            r = mid-1;
         }
         else if(temp[mid].second<=timestamp)
         {
            l = mid+1;
            ans = temp[mid].first;
         }
       }
       return ans;
    }
};

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap* obj = new TimeMap();
 * obj->set(key,value,timestamp);
 * string param_2 = obj->get(key,timestamp);
 */
