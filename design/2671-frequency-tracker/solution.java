// 2671. Frequency Tracker
// https://leetcode.com/problems/frequency-tracker/
// Medium | Java | Accepted 2026-09-08
// Runtime 36 ms | Memory 109.8 MB

class FrequencyTracker {
    int[] map = new int[100001]; //Since we know that the range of possible values goes from 1 to 100000, we can just declare it implicitly
    int[] freq=  new int[100001];
    public FrequencyTracker() {
        
    }
    
    public void add(int number) {
        freq[map[number]++]--; //Get the frequency of this number and subtract 1 from the count of frequencies (number of other numbers that also have this frequency)
        //We want to do this since this number isn't part of this frequency anymore
        //The map[number]++ first evaluates the freq at map[number] and then increments the map[number] frequency
        freq[map[number]]++; //Now increment the count of the new frequency by 1 since the number is now part of this new frequency
    }
    
    public void deleteOne(int number) {
        if(map[number]==0) //If the number has a frequency of 0, return since it doesn't exist
        {
            return;
        }
        freq[map[number]--]--; //Get the frequency of this number and subtract 1 from the count of frequencies, and then decrement the frequency of the number itself
        freq[map[number]]++; //Add 1 to the count of the new frequency since this number is now part of this frequency
    }
    
    public boolean hasFrequency(int frequency) {
        return freq[frequency] > 0;
    }
}

/**
 * Your FrequencyTracker object will be instantiated and called as such:
 * FrequencyTracker obj = new FrequencyTracker();
 * obj.add(number);
 * obj.deleteOne(number);
 * boolean param_3 = obj.hasFrequency(frequency);
 */
