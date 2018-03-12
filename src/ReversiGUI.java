import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

class ReversiGUI extends JPanel {

    static JLabel score1;   // Счет игрока
    static JLabel score2;   // Счет компьютера
    static JLabel restart; // Надпись рестарт
    static JButton newGame; // Кнопка старта новый игры
    static JButton[] cell;  // Ячейки
    static Reversi board;   // Создаем объект board класса Reversi
    static ArrayList<Reversi> arrReversi = new ArrayList<>();    // Лист из объектов реверси

    static int playerScore = 2;  //  Очки игрока (начальные)
    static int pcScore = 2;      //  Очки бота (начальные)
    private static Reversi start;       //  Объект start класса Reversi
    private static int rows = 8;        //  Размер игрового поля 8х8, это строки
    private static int cols = 8;        //  Это колонны

    ReversiGUI() {
        super(new BorderLayout()); // Создаем слой
        setPreferredSize(new Dimension(800, 700));
        setLocation(0, 0);

        board = new Reversi();
        start = board;
        arrReversi.add(new Reversi(board));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 60));

        // создаем кнопку рестарта
        newGame = new JButton();
        newGame.setPreferredSize(new Dimension(70, 50));
        try {
            Image img = ImageIO.read(getClass().getResource("images/start.png"));
            newGame.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
          }
        newGame.addActionListener(new Action());

        // надпись рестарт игры
        restart = new JLabel();
        restart.setText(" Press to restart: " + "\n");
        restart.setFont(new Font("Serif", Font.BOLD, 22));
        panel.add(restart);

        JLabel name = new JLabel();
        name.setLocation(650, 680);
        panel.add(newGame);
        add(panel, BorderLayout.SOUTH);

        JPanel boardPanel = new JPanel(new GridLayout(8, 8)); // Игровое поле, задаем цвет поля,
        cell = new JButton[64];                                         // величину клеток
        int k = 0;
        for (int row = 0; row < rows; row++) {
            for (int colum = 0; colum < cols; colum++) {
                cell[k] = new JButton();
                cell[k].setSize(50, 50);
                cell[k].setBackground(Color.orange);
                cell[k].setBorder(BorderFactory.createLineBorder(Color.darkGray));
                if (board.gameCells[row][colum].getCh() == 'X') {
                    try {
                        Image img = ImageIO.read(getClass().getResource("images/dark.png"));
                        cell[k].setIcon(new ImageIcon(img));
                    } catch (IOException ignored) {
                    }
                } else if (board.gameCells[row][colum].getCh() == 'O') {
                    try {
                        Image img = ImageIO.read(getClass().getResource("images/light.png"));
                        cell[k].setIcon(new ImageIcon(img));
                    } catch (IOException ignored) {
                    }
                } else if (row == 2 && colum == 4 || row == 3 && colum == 5 ||
                        row == 4 && colum == 2 || row == 5 && colum == 3) {
                    try {
                        Image img = ImageIO.read(getClass().getResource("images/legalMoveIcon.png"));
                        cell[k].setIcon(new ImageIcon(img));
                    } catch (IOException ignored) {
                    }
                }
                cell[k].addActionListener(new Action());
                boardPanel.add(cell[k]);
                k++;
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(); // Создаем поле
        scorePanel.setPreferredSize(new Dimension(210, 800));

        JLabel dark = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/dark.png"));
            dark.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
        }
        JLabel light = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/light.png"));
            light.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
        }

        //Выводим счет игры
        score1 = new JLabel();
        score1.setText(" Player : " + playerScore + "  ");
        score1.setFont(new Font("Serif", Font.BOLD, 22));

        score2 = new JLabel();
        score2.setText(" Computer : " + pcScore + "  ");
        score2.setFont(new Font("Serif", Font.BOLD, 22));

        // Настраиваем меню справа от игрового поля
        c.gridx = 0; // Номер столбца
        c.gridy = 1; // Номер строки
        scorePanel.add(dark, c);
        c.gridx = 1;
        c.gridy = 1;
        scorePanel.add(score1, c);

        c.gridx = 0;
        c.gridy = 2;
        scorePanel.add(light, c);
        c.gridx = 1;
        c.gridy = 2;
        scorePanel.add(score2, c);

        add(scorePanel, BorderLayout.EAST); // добавляем панель со счетом справа от игрового поля
    }

    // класс отвечающий за нажатие кнопок
    static class Action implements ActionListener {

        Image imgDark, imgLight, imgMove;
        Action(){
            try {
                imgDark = ImageIO.read(getClass().getResource("images/dark.png"));
            } catch (IOException ignored) {
            }

            try {
                imgLight = ImageIO.read(getClass().getResource("images/light.png"));
            } catch (IOException ignored) {
            }

            try {
                imgMove = ImageIO.read(getClass().getResource("images/legalMoveIcon.png"));
            } catch (IOException ignored) {
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == newGame) {
                board.reset();
                arrReversi.clear();
                arrReversi.add(new Reversi(start));
                int k = 0;
                for (int row = 0; row < rows; row++) {
                    for (int colum = 0; colum < cols; colum++) {
                        cell[k].setIcon(null);
                        if (board.gameCells[row][colum].getCh() == 'X') {
                                cell[k].setIcon(new ImageIcon(imgDark));
                        } else if (board.gameCells[row][colum].getCh() == 'O') {
                                cell[k].setIcon(new ImageIcon(imgLight));
                        }
                        if (row == 2 && colum == 4 || row == 3 && colum == 5 ||
                                row == 4 && colum == 2 || row == 5 && colum == 3) {
                                cell[k].setIcon(new ImageIcon(imgMove));
                        }
                        k++;
                    }
                }
            } else {
                for (int i = 0; i < 64; i++) {
                    if (e.getSource() == cell[i]) {
                        int xCor, yCor;
                        int ct;
                        int arr[] = new int[3];
                      //  System.out.println("button : " + i);
                        if (i == 0) {
                            xCor = 0;
                            yCor = 0;
                        } else {
                            yCor = i % 8;
                            xCor = i / 8;
                        }
                        ct = board.play(xCor, yCor);
                        if (ct == 0) {
                            arrReversi.add(new Reversi(board));
                            board.controlElements(arr);
                            playerScore = arr[0];
                            pcScore = arr[1];
                            score1.setText("Player : " + playerScore + "  ");
                            score2.setText("Computer : " + pcScore + "  ");
                        }
                        if (ct == 0 || ct == -1) {
                            board.play();
                            arrReversi.add(new Reversi(board));
                            ArrayList<Integer> arrList = new ArrayList<>();
                            int k = 0;
                            for (int row = 0; row < rows; row++) {
                                for (int colum = 0; colum < cols; colum++) {
                                    if (board.gameCells[row][colum].getCh() == 'X') {
                                            cell[k].setIcon(new ImageIcon(imgDark));
                                    } else if (board.gameCells[row][colum].getCh() == 'O') {
                                            cell[k].setIcon(new ImageIcon(imgLight));
                                    } else if (board.gameCells[row][colum].getCh() == '.') {
                                        cell[k].setIcon(null);
                                    }
                                    k++;
                                }
                            }
                            board.findLegalMove(arrList);
                            for (int j = 0; j < arrList.size(); j += 2) {
                                    cell[arrList.get(j) * rows + arrList.get(j + 1)].setIcon(new ImageIcon(imgMove));
                            }
                            board.controlElements(arr);
                            playerScore = arr[0];
                            pcScore = arr[1];
                            score1.setText("Player : " + playerScore + "  ");
                            score2.setText("Computer : " + pcScore + "  ");
                        }
                    }

                }
                // вывод сообщений об окончании игры
                int st = board.endOfGame();
                if (st == 0) {
                    if (playerScore > pcScore)
                        JOptionPane.showMessageDialog(null, "No legal move!\nPlayer Win!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "No legal move!\nComputer Win!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                } else if (st == 1 || st == 3) {
                    JOptionPane.showMessageDialog(null, "Computer Win!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                } else if (st == 2 || st == 4) {
                    JOptionPane.showMessageDialog(null, "Player Win!", "Game Over", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }

    }
}
