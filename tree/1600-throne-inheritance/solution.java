// 1600. Throne Inheritance
// https://leetcode.com/problems/throne-inheritance/
// Medium | Java | Accepted 2026-09-10
// Runtime 198 ms | Memory 122.1 MB

class ThroneInheritance {
    
    class Node {
        String name;
        List<Node> children = new ArrayList<>();
        public Node(String name)
        {
            this.name = name;
        }

        public void addChild(Node name)
        {
            children.add(name);
        }
    }

    //This question is graph problem where we make a graph and then traverse it using DFS
    //Can be implemented with an adjacency list but I used nodes to introduce encapsulation

    Node king; //This is the king node
    Map<String, Node> mapping = new HashMap<>(); //This is the O(1) lookup for a node to set its children
    Set<Node> deaths = new HashSet<>(); //This is the set that contains the nodes of individuals who died
    public ThroneInheritance(String kingName) {
        king = new Node(kingName); //Make the root (king) node
        mapping.put(kingName, king); //Map the name to the node
    }
    
    public void birth(String parentName, String childName) {
        Node child = new Node(childName); //Make the child node
        mapping.put(childName, child); //Map the child's name to its node and then add the child node to the list of children for the parent
        mapping.get(parentName).addChild(child);
    }
    
    public void death(String name) {  
        deaths.add(mapping.get(name)); //Add the node associated with the name to the deaths set
    }
    
    public List<String> getInheritanceOrder() {
        return dfs(new ArrayList<>(), king); //Run DFS
    }

    public List<String> dfs(List<String> order, Node curr)
    {
        if(!deaths.contains(curr)) //Only add the name to the order if the deaths set doesn't contain the node
        {
            order.add(curr.name);
        }
        //Otherwise, we still want to look through all the node's children (whether the parent died or not)
        List<Node> children = curr.children; //Get the list of the node's children
        for(int i = 0; i<children.size(); i++)
        {
            dfs(order, children.get(i)); //Go through the list add call dfs on each child
        }
        return order; //Return the order at the end
    }
}

/**
 * Your ThroneInheritance object will be instantiated and called as such:
 * ThroneInheritance obj = new ThroneInheritance(kingName);
 * obj.birth(parentName,childName);
 * obj.death(name);
 * List<String> param_3 = obj.getInheritanceOrder();
 */
