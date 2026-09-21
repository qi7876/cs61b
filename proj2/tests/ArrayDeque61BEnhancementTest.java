import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.Iterator;

public class ArrayDeque61BEnhancementTest {
    @Test
    public void iter() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        Iterator<Integer> iter = ad.iterator();
        assertThat(iter.hasNext()).isTrue();
        assertThat(iter.next()).isEqualTo(1);
        assertThat(iter.next()).isEqualTo(2);
        assertThat(iter.next()).isEqualTo(3);
        assertThat(iter.hasNext()).isFalse();
        assertThat(iter.next()).isNull();
    }

    @Test
    public void add_first_without_to_list() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad).containsExactly();
        ad.addFirst("back");
        assertThat(ad).containsExactly("back");
        ad.addFirst("midade");
        ad.addFirst("front");
        assertThat(ad).containsExactly("front", "midade", "back");
    }

    @Test
    public void equal() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        Deque61B<String> ad2 = new ArrayDeque61B<>();
        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");

        String ad5 = "ArrayDeque";

        Deque61B<String> ad6 = new ArrayDeque61B<>();
        ad6.addLast("front");
        ad6.addLast("middle");

        Deque61B<String> ad7 = new ArrayDeque61B<>();
        ad7.addLast("front");
        ad7.addLast("back");
        ad7.addLast("middle");

        assertThat(ad).isEqualTo(ad2);
        assertThat(ad.equals(ad)).isTrue();
        assertThat(ad.equals(null)).isFalse();
        assertThat(ad.equals(ad5)).isFalse();
        assertThat(ad.equals(ad6)).isFalse();
        assertThat(ad.equals(ad7)).isFalse();
    }

    @Test
    public void to_string() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        System.out.println(ad);
    }

}
