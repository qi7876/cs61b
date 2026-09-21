import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    public void add_first() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.toList()).containsExactly().inOrder();
        ad.addFirst("back");
        assertThat(ad.toList()).containsExactly("back").inOrder();
        ad.addFirst("middle");
        ad.addFirst("front");
        assertThat(ad.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void add_last() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.toList()).containsExactly().inOrder();
        ad.addLast("front");
        assertThat(ad.toList()).containsExactly("front").inOrder();
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void get_first() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.getFirst()).isNull();
        ad.addLast("middle");
        assertThat(ad.getFirst()).isEqualTo("middle");
        ad.addFirst("front");
        ad.addLast("back");
        assertThat(ad.getFirst()).isEqualTo("front");
    }

    @Test
    public void get_last() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.getLast()).isNull();
        ;
        ad.addLast("front");
        assertThat(ad.getLast()).isEqualTo("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.getLast()).isEqualTo("back");
    }

    @Test
    public void get() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.get(0)).isNull();
        ad.addLast("front");
        assertThat(ad.get(0)).isEqualTo("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.get(1)).isEqualTo("middle");
        assertThat(ad.get(2)).isEqualTo("back");
        assertThat(ad.get(3)).isNull();
        assertThat(ad.get(-1)).isNull();
    }

    @Test
    public void get_recursive() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.getRecursive(0)).isNull();
        ad.addLast("front");
        assertThat(ad.getRecursive(0)).isEqualTo("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.getRecursive(1)).isEqualTo("middle");
        assertThat(ad.getRecursive(2)).isEqualTo("back");
        assertThat(ad.getRecursive(3)).isNull();
        assertThat(ad.getRecursive(-1)).isNull();
    }

    @Test
    public void is_empty() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();
        ad.addLast("front");
        assertThat(ad.isEmpty()).isFalse();
        ad.removeLast();
        assertThat(ad.isEmpty()).isTrue();
    }

    @Test
    public void size() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.size()).isEqualTo(0);
        ad.addLast("front");
        assertThat(ad.size()).isEqualTo(1);
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.size()).isEqualTo(3);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(0);
    }

    @Test
    public void remove_first() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.removeFirst()).isNull();
        ad.addLast("front");
        assertThat(ad.removeFirst()).isEqualTo("front");
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.toList()).containsExactly("front", "middle", "back").inOrder();
        assertThat(ad.removeFirst()).isEqualTo("front");
        assertThat(ad.removeFirst()).isEqualTo("middle");
        assertThat(ad.removeFirst()).isEqualTo("back");
        assertThat(ad.removeFirst()).isNull();
    }

    @Test
    public void remove_last() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.removeLast()).isNull();
        ad.addLast("front");
        assertThat(ad.removeLast()).isEqualTo("front");
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.toList()).containsExactly("front", "middle", "back").inOrder();
        assertThat(ad.removeLast()).isEqualTo("back");
        assertThat(ad.removeLast()).isEqualTo("middle");
        assertThat(ad.removeLast()).isEqualTo("front");
        assertThat(ad.removeLast()).isNull();
    }

    @Test
    public void grow() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("1");
        ad.addLast("2");
        ad.addLast("3");
        ad.addLast("4");
        ad.addLast("5");
        ad.addLast("6");
        ad.addLast("7");
        assertThat(ad.size()).isEqualTo(7);
        ad.addLast("8");
        ad.addLast("9");
        ad.addLast("10");
        ad.addLast("11");
        assertThat(ad.size()).isEqualTo(11);
    }

    @Test
    public void shrink() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("1");
        ad.addLast("2");
        ad.addLast("3");
        ad.addLast("4");
        ad.addLast("5");
        ad.addLast("6");
        ad.addLast("7");
        assertThat(ad.size()).isEqualTo(7);
        ad.addLast("8");
        ad.addLast("9");
        ad.addLast("10");
        ad.addLast("11");
        assertThat(ad.size()).isEqualTo(11);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(6);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(3);
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(0);
    }
}
