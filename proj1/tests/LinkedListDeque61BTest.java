import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /**
     * In this test, we add three elements to verify that addFirst works correctly.
     */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]

        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void add_first_from_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void add_last_from_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void add_first_nonempty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("placeholder_1");
        lld1.addFirst("placeholder_2");
        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.toList()).containsExactly("front", "middle", "back", "placeholder_2", "placeholder_1")
                .inOrder();
    }

    @Test
    public void add_last_nonempty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();
    }

    @Test
    public void to_list_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.toList()).containsExactly().inOrder();
    }

    @Test
    public void to_list_nonempty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.toList()).containsExactly("front", "middle", "back")
                .inOrder();
    }

    @Test
    public void add_first_after_remove_to_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("placeholder_1");
        lld1.addFirst("placeholder_2");
        String ph_2 = lld1.removeFirst();
        assertThat(ph_2).isEqualTo("placeholder_2");
        String ph_1 = lld1.removeFirst();
        assertThat(ph_1).isEqualTo("placeholder_1");
        assertThat(lld1.toList()).containsExactly().inOrder();

        lld1.addFirst("back");
        lld1.addFirst("middle");
        lld1.addFirst("front");

        assertThat(lld1.toList()).containsExactly("front", "middle", "back")
                .inOrder();
    }

    @Test
    public void add_last_after_remove_to_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        String ph_2 = lld1.removeLast();
        assertThat(ph_2).isEqualTo("placeholder_2");
        String ph_1 = lld1.removeLast();
        assertThat(ph_1).isEqualTo("placeholder_1");
        assertThat(lld1.toList()).containsExactly().inOrder();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.toList()).containsExactly("front", "middle", "back")
                .inOrder();
    }

    @Test
    public void remove_first() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();

        String ph_1 = lld1.removeFirst();
        assertThat(ph_1).isEqualTo("placeholder_1");
        String ph_2 = lld1.removeFirst();
        assertThat(ph_2).isEqualTo("placeholder_2");
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void remove_last() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();

        String back = lld1.removeLast();
        assertThat(back).isEqualTo("back");
        String middle = lld1.removeLast();
        assertThat(middle).isEqualTo("middle");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front").inOrder();
    }

    @Test
    public void remove_first_to_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();

        String ph_1 = lld1.removeFirst();
        assertThat(ph_1).isEqualTo("placeholder_1");
        String ph_2 = lld1.removeFirst();
        assertThat(ph_2).isEqualTo("placeholder_2");
        lld1.removeFirst();
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly("back").inOrder();
    }

    @Test
    public void remove_last_to_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();

        String back = lld1.removeLast();
        assertThat(back).isEqualTo("back");
        String middle = lld1.removeLast();
        assertThat(middle).isEqualTo("middle");
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("placeholder_1").inOrder();
    }

    @Test
    public void remove_first_to_one() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();

        String ph_1 = lld1.removeFirst();
        assertThat(ph_1).isEqualTo("placeholder_1");
        String ph_2 = lld1.removeFirst();
        assertThat(ph_2).isEqualTo("placeholder_2");
        lld1.removeFirst();
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
    }

    @Test
    public void remove_last_to_one() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2", "front", "middle", "back")
                .inOrder();

        String back = lld1.removeLast();
        assertThat(back).isEqualTo("back");
        String middle = lld1.removeLast();
        assertThat(middle).isEqualTo("middle");
        lld1.removeLast();
        assertThat(lld1.toList()).containsExactly("placeholder_1", "placeholder_2").inOrder();
    }

    @Test
    public void get_first_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.getFirst()).isNull();
    }

    @Test
    public void get_last_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.getLast()).isNull();
    }

    @Test
    public void get_first_valid() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.getFirst()).isEqualTo("placeholder_1");
    }

    @Test
    public void get_last_valid() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.getLast()).isEqualTo("back");
    }

    @Test
    public void get_valid() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.get(0)).isEqualTo("placeholder_1");
        assertThat(lld1.get(3)).isEqualTo("middle");
        assertThat(lld1.get(4)).isEqualTo("back");
    }

    @Test
    public void get_oob_large() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.get(10)).isNull();
    }

    @Test
    public void get_oob_neg() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.get(-10)).isNull();
    }

    @Test
    public void get_recursive_valid() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.getRecursive(0)).isEqualTo("placeholder_1");
        assertThat(lld1.getRecursive(3)).isEqualTo("middle");
        assertThat(lld1.getRecursive(4)).isEqualTo("back");
    }

    @Test
    public void get_recursive_oob_large() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.getRecursive(10)).isNull();
    }

    @Test
    public void get_recursive_oob_neg() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        assertThat(lld1.getRecursive(-10)).isNull();
    }

    @Test
    public void size() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.size()).isEqualTo(0);
        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.addLast("front");
        assertThat(lld1.size()).isEqualTo(3);
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1.size()).isEqualTo(5);
    }

    @Test
    public void size_after_remove_to_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.size()).isEqualTo(0);
        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        assertThat(lld1.size()).isEqualTo(2);
        lld1.removeLast();
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void size_after_remove_from_empty() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.size()).isEqualTo(0);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(0);
        lld1.removeFirst();
        assertThat(lld1.size()).isEqualTo(0);
    }

    @Test
    public void is_empty_true() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.removeLast();
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isTrue();
    }

    @Test
    public void is_empty_false() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("placeholder_1");
        lld1.addLast("placeholder_2");
        lld1.removeFirst();
        assertThat(lld1.isEmpty()).isFalse();
    }

}