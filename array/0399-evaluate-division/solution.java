// 399. Evaluate Division
// https://leetcode.com/problems/evaluate-division/
// Medium | Java | Accepted 2026-08-22
// Runtime 2 ms | Memory 47 MB

class Solution {
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        
        // 1. Updated Graph Structure
        Map<String, Map<String, Double>> graph = new HashMap<>();
        
        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            double weight = values[i];
            
            // Build the nested maps directly
            graph.putIfAbsent(u, new HashMap<>());
            graph.get(u).put(v, weight);
            
            graph.putIfAbsent(v, new HashMap<>());
            graph.get(v).put(u, 1.0 / weight);
        }
        
        double[] ans = new double[queries.size()];
        
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);
            
            // Fast failure if variables don't exist in our graph at all
            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                ans[i] = -1.0;
                continue;
            }
            
            Queue<Pair<String, Double>> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();
            
            queue.offer(new Pair<>(start, 1.0));
            visited.add(start);
            
            double result = -1.0;
            
            while (!queue.isEmpty()) {
                Pair<String, Double> curr = queue.poll();
                String currNode = curr.getKey();
                double currWeight = curr.getValue();
                
                if (currNode.equals(end)) {
                    result = currWeight;
                    break;
                }
                
                // 2. Iterate through Map Entries instead of a List of Pairs
                Map<String, Double> neighbors = graph.get(currNode);
                if (neighbors != null) {
                    for (Map.Entry<String, Double> neighbor : neighbors.entrySet()) {
                        String nextNode = neighbor.getKey();
                        
                        if (!visited.contains(nextNode)) {
                            visited.add(nextNode);
                            queue.offer(new Pair<>(nextNode, currWeight * neighbor.getValue()));
                        }
                    }
                }
            }
            ans[i] = result;
        }
        return ans;
    }
}
