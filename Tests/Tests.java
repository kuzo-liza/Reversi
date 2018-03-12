import java.util.ArrayList;
import org.junit.Test;

public class Tests {

    @Test
    public void testing1() {
        Reversi test = new Reversi();
        test.play(5, 6);
    }

    @Test
    public void testing2() {
        Reversi test = new Reversi();
        test.play();
    }

    @Test
    public void testing3() {
        Reversi test = new Reversi();
        int[] testArr = new int[3];
        testArr[0]=0;
        testArr[1]=1;
        testArr[2]=2;
        test.controlElements(testArr);
    }

    @Test
    public void testing4() {
        Reversi test = new Reversi();
        test.reset();
    }

    @Test
    public void testing5() {
        Reversi test = new Reversi();
        test.endOfGame();
    }

    @Test
    public void testing6() {
        Reversi test = new Reversi();
        ArrayList<Integer> testArr = new ArrayList<>();
        test.findLegalMove(testArr);
    }
}
