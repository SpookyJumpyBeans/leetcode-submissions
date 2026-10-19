// 2115. Find All Possible Recipes from Given Supplies
// https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/
// Medium | Java | Accepted 2026-10-16
// Runtime 62 ms | Memory 48 MB

class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
       Map<String, Integer> needed = new HashMap<>();
       Map<String, List<String>> dag = new HashMap<>();
       //DAG question
       //Start with the nodes with 0 incoming edges (the supplies) and traverse towards the recipes
       //For every recipe that we explore all its incoming edges, we also add it to the queue
       //This runs Kahn's
       for(int i = 0; i<recipes.length; i++)
       {
            List<String> ing = ingredients.get(i);
            needed.put(recipes[i], ing.size()); //We keep track of how many ingredients each recipe needs (how many incoming edges each node has)
            for(int j = 0; j<ing.size(); j++)
            {
                dag.computeIfAbsent(ing.get(j), k -> new ArrayList<>()).add(recipes[i]); //We want to connect the ingredients to their parents
            }
       }
       Queue<String> kahn = new ArrayDeque<>(); //Start Kahn's
       for(int i = 0; i<supplies.length; i++)
       {
        kahn.add(supplies[i]); //Add all initial supplies to the queue
       }
       ArrayList<String> ans = new ArrayList<>();
       while(!kahn.isEmpty())
       {
        String curr = kahn.poll();
        List<String> parents = dag.get(curr); //Get the recipes associated with this ingredient
        if(parents==null)
        {
            continue;
        }
        for(int i = 0; i<parents.size(); i++)
        {
            String parent = parents.get(i);
            needed.put(parent, needed.get(parent)-1); //Subtract 1 from the number of incoming edges the parent has
            if(needed.get(parent)==0) //If the parent has 0 edges, we can make this recipe, so add it to the queue to see if there are any nested recipes that require other recipes
            {
                ans.add(parent);
                needed.remove(parent); //Remove the entry as we already made the recipe
                kahn.add(parent);
            }
        }
       }
       return ans; //Return all the possible recipes we can make
    }
}
