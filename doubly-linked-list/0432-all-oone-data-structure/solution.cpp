// 432. All O`one Data Structure
// https://leetcode.com/problems/all-oone-data-structure/
// Hard | C++ | Accepted 2026-07-23
// Runtime 160 ms | Memory 96.1 MB

class AllOne {
private:

struct Bucket{
    int count;
    unordered_set<string> keys;
};
list<Bucket> order;
map<string, list<Bucket>::iterator> maps;

public:
    void inc(string key) {
        if(maps.contains(key))
        {
            int nextC = maps[key]->count+1;
            list<Bucket>::iterator targetIt;
            if(next(maps[key])==order.end() || next(maps[key])->count!=nextC)
            {
                targetIt = order.insert(next(maps[key]), Bucket{nextC, {key}});
            }
            else
            {
                next(maps[key])->keys.insert(key);
                targetIt = next(maps[key]);
            }
            maps[key]->keys.erase(key);
            if(maps[key]->keys.empty())
            {
                order.erase(maps[key]);
            }
            maps[key] = targetIt;
        }
        else
        {
            if(order.empty() || order.front().count>1)
            {
                auto newBucket = order.insert(order.begin(), Bucket{1, {key}});
                maps[key] = newBucket;
            }
            else
            {
                order.front().keys.insert(key);
                maps[key] = order.begin();
            }
        }
    }
    
    void dec(string key) {
        int prevC = maps[key]->count-1;
        if(prevC==0)
        {
            maps[key]->keys.erase(key);
            if(maps[key]->keys.empty())
            {
                order.erase(maps[key]);
            }
            maps.erase(key);
        }
        else
        {
            list<Bucket>::iterator prevB;
                if(maps[key]==order.begin() || prev(maps[key])->count!=prevC)
                {
                    prevB = order.insert(maps[key], Bucket{prevC, {key}});
                }
                else
                {
                    prev(maps[key])->keys.insert(key);
                    prevB = prev(maps[key]);
                }
                maps[key]->keys.erase(key);
                if(maps[key]->keys.empty())
                {
                    order.erase(maps[key]);
                }
                maps[key] = prevB;
        }
    }
    
    string getMaxKey() {
        if(!order.empty())
        {
            return *(order.back().keys.begin());
        }
        return "";
    }
    
    string getMinKey() {
         if(!order.empty())
        {
            return *(order.front().keys.begin());
        }
        return "";
    }
};

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne* obj = new AllOne();
 * obj->inc(key);
 * obj->dec(key);
 * string param_3 = obj->getMaxKey();
 * string param_4 = obj->getMinKey();
 */
