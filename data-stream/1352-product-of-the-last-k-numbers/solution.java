// 1352. Product of the Last K Numbers
// https://leetcode.com/problems/product-of-the-last-k-numbers/
// Medium | Java | Accepted 2026-11-25
// Runtime 19 ms | Memory 95.6 MB

class ProductOfNumbers {
    ArrayList<Integer> prefix = new ArrayList<>();
    public ProductOfNumbers() {
        //Prefix sum but with multiplication
        //Start with the base of 1
        prefix.add(1);
    }
    
    public void add(int num) {
        if(num!=0) //If num != 0, add the next cumulative product of the values in the stream
        {
            prefix.add(prefix.get(prefix.size()-1)*num);
        }
        else //If num == 0, then that means that we can basically clear out the list, since every value from here on out will result in a 0 product
        {
            prefix.clear();
            prefix.add(1); //Restart the list fresh since the last 0 seen
        } 
    }
    
    public int getProduct(int k) {
        return k>=prefix.size() ? 0 : prefix.get(prefix.size()-1)/prefix.get(prefix.size()-k-1);
        //If k is >= prefix.size(), that means that it's asking for a value that we saw before or at the last zero, meaning the product of those values will be 0
        //Else, do the same thing as prefix sum but with division instead
    }
}

/**
 * Your ProductOfNumbers object will be instantiated and called as such:
 * ProductOfNumbers obj = new ProductOfNumbers();
 * obj.add(num);
 * int param_2 = obj.getProduct(k);
 */
