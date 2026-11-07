import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M];
        int N = oomages.size();
        for (Oomage oomage : oomages) {
            buckets[Math.floorMod(oomage.hashCode(), M)] += 1;
        }
        for (int n : buckets) {
            if (n < N / 50 || n > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
