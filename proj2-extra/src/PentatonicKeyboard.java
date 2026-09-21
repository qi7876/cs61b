import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A five-key pentatonic keyboard played with the computer keyboard.
 * No graphics required — the StdDraw window just needs to exist (and have
 * focus!) so it can receive your key presses.
 */
public class PentatonicKeyboard {
    // The C major pentatonic scale: C4, D4, E4, G4, A4.
    // Suggested keys: a s d f g.
    // TODO: Create one GuitarString per note. An array of GuitarStrings and a
    // String of keyboard characters (like "asdfg") will keep your code
    // short — charAt and indexOf are your friends.
    private static GuitarString[] gsl = new GuitarString[] { new GuitarString(261.63), new GuitarString(293.66),
            new GuitarString(329.63), new GuitarString(392.00), new GuitarString(440.00) };
    private static String keys = "asdfg";

    static void main() {
        // StdDraw needs a window to capture keystrokes. This is enough:
        StdDraw.setCanvasSize(400, 100);
        StdDraw.text(0.5, 0.5, "click here, then play: a s d f g");

        while (true) {
            // TODO: If the user has typed a key (StdDraw.hasNextKeyTyped()),
            // read it with StdDraw.nextKeyTyped() and pluck the
            // matching string (ignore keys that aren't yours).
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                gsl[keys.indexOf(key)].pluck();
            }

            // TODO: Compute the superposition of the strings — the sum of
            // every string's sample() — and play it with
            // StdAudio.play(sum). Then tic() every string.
            Double sum = 0.0;
            for (GuitarString gs : gsl) {
                sum += gs.sample();
            }
            StdAudio.play(sum);
            for (GuitarString gs : gsl) {
                gs.tic();
            }
        }
    }
}
