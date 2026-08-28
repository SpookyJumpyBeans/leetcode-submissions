// 929. Unique Email Addresses
// https://leetcode.com/problems/unique-email-addresses/
// Easy | Java | Accepted 2026-07-16
// Runtime 9 ms | Memory 46.4 MB

class Solution {
    public int numUniqueEmails(String[] emails) {
        Set<String> set = new HashSet<>();
        for(String email : emails)
        {
            String local = email.substring(0, email.indexOf("@"));
            String domain = email.substring(email.indexOf("@"));
            local = local.replace(".", "");
            if(local.indexOf("+")>=0)
        {            local = local.substring(0, local.indexOf("+"));}
            set.add(local+domain);
        }
        return set.size();
    }
}
