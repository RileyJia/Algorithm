import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by jia on 8/10/17.
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int num = 1;
        int prob;
        RandomizedQueue<String> per = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            if (k == 0){
                break;
            } else if (num - 1 < k) {
                per.enqueue(StdIn.readString());
            } else {
                prob = StdRandom.uniform( 1,num + 1);
                if (prob <= k){
                    per.dequeue();
                    per.enqueue(StdIn.readString());
                } else {
                    String noUse = StdIn.readString();
                }

            }
            num += 1;
        }

        for (String item : per) {
            System.out.println(item);
        }
/*
        while (!StdIn.isEmpty()) {
            per.enqueue(StdIn.readString());
        }

        for (String item : per) {
            if(num > k) {break;}
            System.out.println(item);
            num += 1;
        }

*/
    }
}
