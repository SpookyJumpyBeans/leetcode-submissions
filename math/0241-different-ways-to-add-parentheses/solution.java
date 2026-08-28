// 241. Different Ways to Add Parentheses
// https://leetcode.com/problems/different-ways-to-add-parentheses/
// Medium | Java | Accepted 2026-08-22
// Runtime 1 ms | Memory 43.2 MB

class Solution {
    Map<String, List<Integer>> map = new HashMap<>();
    public List<Integer> diffWaysToCompute(String expression) {
        if(map.containsKey(expression))
        {
            return map.get(expression);
        }
        List<Integer> results = new ArrayList<>();
        for(int i = 0; i<expression.length(); i++)
        {
            char c = expression.charAt(i);
            if(c=='+'||c=='-'||c=='*')
            {
                List<Integer> left = diffWaysToCompute(expression.substring(0, i));
                List<Integer> right = diffWaysToCompute(expression.substring(i+1));
                for(int k = 0; k<left.size(); k++)
                {
                    for(int j = 0; j<right.size(); j++)
                    {
                        if(c=='+')
                        {
                        results.add(left.get(k)+right.get(j));
                        }
                         if(c=='-')
                        {
                        results.add(left.get(k)-right.get(j));
                        }
                         if(c=='*')
                        {
                        results.add(left.get(k)*right.get(j));
                        }
                    }
                }
            }
        }
        if(results.size()==0)
        {
            results.add(Integer.parseInt(expression));
        }
        map.put(expression, results);
        return results;
    }
}
