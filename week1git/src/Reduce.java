public class Reduce {
    public static int main(int n) {
        int step = 0;
        int i = n;
        while (i != 0){
            if (i % 2 == 0){
                i = i / 2;
            }
            else {
                i -= 1;
            }
            step++;
        }
        return step;
    }
}
