import javax.swing.*;
import java.awt.*;

public class start extends JFrame {

    public static void main(String[] args) {
        new start();
    }

    public start() {
        super("Reversi Game");  // название игры
        setLayout(new BorderLayout());  // установка менеджера компановки(контроль за тем, на каких местах объекты)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Dimension - контроль размера экрана

        int height = screenSize.height; // Получаем размеры экрана
        int width = screenSize.width;   // Получаем размеры экрана
        setSize(width / 2, height / 2);

        setLocationRelativeTo(null);    // Создаем пустое окно

        JPanel pnlLeft = new ReversiGUI();  // Создаем объект панельки
        add(pnlLeft, BorderLayout.CENTER);  // Добавляем действия в центр, по сути процесс отрисовки игры

        setSize(800, 700);  // Передаем размеры экрана
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Закрытие окна
        setVisible(true);
    }
}