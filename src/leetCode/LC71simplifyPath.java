package leetCode;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class LC71simplifyPath {

    public String simplifyPath(String path) {
        //用栈
        Deque<String> deQue = new ArrayDeque<>();

        String[] names = path.split("/");

        StringBuilder res = new StringBuilder();

        for (String name : names) {
            if (name.isEmpty() || name.equals(".")) {
                continue;
            } else if(name.equals("..")){
                if (!deQue.isEmpty()) {
                    deQue.pollLast();
                }
            } else {
                deQue.offerLast(name);
            }
        }


        if (deQue.isEmpty()) {
            res.append("/");
        } else {
            while(!deQue.isEmpty()) {
                res.append("/").append(deQue.pollFirst());
            }
        }


        return res.toString();


    }
}
