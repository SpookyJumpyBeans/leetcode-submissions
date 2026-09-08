// 2034. Stock Price Fluctuation 
// https://leetcode.com/problems/stock-price-fluctuation/
// Medium | Java | Accepted 2026-09-07
// Runtime 86 ms | Memory 132.5 MB

class StockPrice {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    int maxTimestamp = Integer.MIN_VALUE;
    int latest = 0;
    Map<Integer, Integer> stocks = new HashMap<>();
    PriorityQueue<int[]> pricesMin = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    PriorityQueue<int[]> pricesMax = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0])); // [Price, Timestamp]
    public StockPrice() {
        
    }
    
    public void update(int timestamp, int price) {
        //Use a map to map the current timestamp to the price
        //This is a O(1) lookup to set the timestamp to a new or updated price
        //This is how we're going to check for outdated entries in our priority queues
        stocks.put(timestamp, price);
        pricesMin.add(new int[]{price, timestamp}); //We now put the entry into our min/max heaps, sorted by price
        pricesMax.add(new int[]{price, timestamp});
        while(stocks.get(pricesMin.peek()[1])!=pricesMin.peek()[0]) //We look at the stock on top of the pq with the lowest/highest price
        //If this price doesn't match what we have in our map for the timestamp, then we know we updated this price and this is an outdated entry
        //This can't be the min/max price anymore, so poll it
        //Keep polling until we get a price/timestamp pair that matches our map of accurate pairs
        {
            pricesMin.poll();
        }
        min = pricesMin.peek()[0]; //Make the min the first price that matches the map's price/timestamp pairing
        while(stocks.get(pricesMax.peek()[1])!=pricesMax.peek()[0])
        {
            pricesMax.poll();
        }
        max = pricesMax.peek()[0]; //Do the same with the max heap
        maxTimestamp = Math.max(maxTimestamp, timestamp); //Check if this timestamp is larger than the max timestamp
        if(timestamp==maxTimestamp)
        {
            latest = price; //If it is, set the latest price to this timestamp's price
        }
    }
    
    public int current() {
        return latest;
    }
    
    public int maximum() {
        return max;
    }
    
    public int minimum() {
        return min;
    }
}

/**
 * Your StockPrice object will be instantiated and called as such:
 * StockPrice obj = new StockPrice();
 * obj.update(timestamp,price);
 * int param_2 = obj.current();
 * int param_3 = obj.maximum();
 * int param_4 = obj.minimum();
 */
