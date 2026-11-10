// 380. Insert Delete GetRandom O(1)
// https://leetcode.com/problems/insert-delete-getrandom-o1/
// Medium | Java | Accepted 2026-11-09
// Runtime 33 ms | Memory 99 MB

class RandomizedSet {
    Set<Integer> set = new HashSet<>();
    Map<Integer, Integer> indexToVal = new HashMap<>();
    Map<Integer, Integer> valToIndex = new HashMap<>();
    int currInd = 0;
    //Intuition, use a set as the general tracker for if elements already exist in the set 
    //Keep track of indexes of values in the set starting at index 0 and going up
    //Use a hashmap from index to values for when we want to remove a value that corresponds to a certain index (that index is now freed)
    //Also for the randomly generated getRandom() funciton
    //Use a hashmap from val to index so that we can get the index associated with a value when we remove 
    public RandomizedSet() {
        
    }
    
    public boolean insert(int val) {
        if(set.add(val)) //If the set doesn't already contain the value
        {
            indexToVal.put(currInd, val); //Add the two relationships
            valToIndex.put(val, currInd);
            currInd++; //Increase the index to the next available index to add a value
            return true;
        }
        return false;
    }
    
    public boolean remove(int val) {
        if(set.contains(val)) //If the set contains the value
        {
            set.remove(val); //Remove it
            int curr = valToIndex.remove(val); //Get the index associated with the removed value
            indexToVal.remove(curr); //Remove the connection between the value and its index from both maps
            if(set.size()>0 && curr != currInd-1) //If the set isn't empty and if the value we're trying to remove isn't the last index we just added, then we have to take the last value that we just added and move it to fill the hole left by the value we just removed
            { 
                int lastElem = indexToVal.remove(currInd-1); //Get the current value at the last index we just added
                indexToVal.put(curr, lastElem); //Put this last element in the index of the value we just removed to fill the hole
                //Update both maps
                valToIndex.put(lastElem, curr);
            }
            currInd-=1; //Subtract the current index by 1 since the set decreased in size by 1
            return true;
        }
        return false;
    }
    
    public int getRandom() {
        return indexToVal.get((int) (Math.random() * currInd)); //Return a randomly generated value associated with a randomly generated index from 0 to currInd (non inclusive)
    }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
