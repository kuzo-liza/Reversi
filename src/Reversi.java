import java.util.ArrayList;

public class Reversi {

    private int rows = 8;      // Число строк
    private int cols = 8;      // Число столбцов в игре
    private int userCont = 0;  // Счёт игрока
    private int computerCont = 0;  // Счёт бота

    public Cell gameCells[][];  // Для создания массива ячеек

    Reversi() {  // Инициализирует карту ячеек
        int mid = rows / 2; // Середина
        gameCells = new Cell[8][8]; // Создание квадратного массива

        // Этот цикл инициализирует ячейки и задает им координаты
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                gameCells[i][j] = new Cell();
                char c = (char) ('a' + j); // используются только буквы
                if ((i == mid - 1) && (j == mid - 1)) {
                    gameCells[i][j].setPosition(c, 'X', i + 1);
                } else if ((i == mid - 1) && (j == mid)) {
                    gameCells[i][j].setPosition(c, 'O', i + 1);
                } else if ((i == mid) && (j == mid - 1)) {
                    gameCells[i][j].setPosition(c, 'O', i + 1);
                } else if ((i == mid) && (j == mid)) {
                    gameCells[i][j].setPosition(c, 'X', i + 1);
                } else {
                    gameCells[i][j].setPosition(c, '.', i + 1);
                }
            }
        }
    }

    Reversi(Reversi obje) {  // Проинициализировали и добавили цвет ячейки
        int y;
        char c, x;
        gameCells = new Cell[8][8];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                gameCells[i][j] = new Cell();
                c = obje.gameCells[i][j].getCh();
                y = obje.gameCells[i][j].getCorY();
                x = obje.gameCells[i][j].getCorX();
                gameCells[i][j].setPosition(x, c, y);   // Поместили в нужную позицию проинициализированную ячейку
            }
        }
    }

    public void findLegalMove(ArrayList<Integer> arr) { // Ищем возможные ходы
        int change;
        change = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (gameCells[i][j].getCh() == '.') {   // Если клеточка помечена точкой, выполни move, см. метод ниже
                    int numberOfMoves[] = new int[1];   // Возможное число ходов
                    move(i, j, change, 'X', 'O', numberOfMoves);
                    if (numberOfMoves[0] != 0) {
                        arr.add(i);
                        arr.add(j);
                    }
                }
            }
        }
    }

    public void play() //метод для игры бота
    {
        int change, max = 0, mX = 0, mY = 0;
        change = 0;
        int numberOfMoves[] = new int[1];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (gameCells[i][j].getCh() == '.') {   // Если клетка помечена точкой
                    move(i, j, change, 'O', 'X', numberOfMoves);    // Выполни move
                    if (max < numberOfMoves[0]) {   // Если max меньше возможного числа ходов
                        max = numberOfMoves[0];     // Перезапишем max и введём новые координаты
                        mX = i;
                        mY = j;
                    }
                }
            }
        }
        computerCont = max;
        if (computerCont == 0) {
            computerCont = -1; // Если счетчик ходов равен 0, компьютеру некуда ходить
            return;
        }
        change = 1;
        move(mX, mY, change, 'O', 'X', numberOfMoves);  // Не равен 0, ходим согласно move
    }

    public int play(int xCor, int yCor) // аналогичный метод для пользователя
    {
        int status;
        int change, max = 0;
        int numberOfMoves[] = new int[1];
        change = 0;
        for (int i = 0; i < rows; ++i) { // В цикле смотрим возможные ходы и записываем в max
            for (int j = 0; j < cols; ++j) {
                if (gameCells[i][j].getCh() == '.') {
                    move(i, j, change, 'X', 'O', numberOfMoves);
                    if (max < numberOfMoves[0])
                        max = numberOfMoves[0];
                }
            }
        }
        userCont = max;// max - это возможный ход
        if (userCont == 0)  // max на выходе из цикла равен 0 - мы не нашли куда ходить, вернет -1
        {
            userCont = -1;
            return -1;
        }
        // Не равно 0 - ходим, смотрим точки и заполняем методом move то, на что нажали
        change = 1;
        if (!(gameCells[xCor][yCor].getCh() == '.'))
            return 1;

        status = move(xCor, yCor, change, 'X', 'O', numberOfMoves);
        if (status == -1)
            return 1;
        for (int i = 0; i < 8; i++) {       // Эта конструкция пробегает по игровому полю и получает значения ячеек,
            for (int j = 0; j < 8; j++) {   //  попутно записывая их в консоль
                System.out.printf("%c", gameCells[i][j].getCh());
            }
            System.out.println("");
        }
        return 0;
    }

    public int endOfGame() {
        int[] arr = new int[3];
        int cross, circular, point;
        controlElements(arr);   // Смотрим состояние игры и записываем каких ячеек сколько в массив из 3 элементов
        cross = arr[0]; // Количество черных ячеек
        circular = arr[1];  // Белых
        point = arr[2]; // Пустых

        if ((userCont == -1 && computerCont == -1) || point == 0) {
            if (userCont == -1 && computerCont == -1) // Cчет нелегальный, верни 0
                return 0;
            if (circular > cross)   // Белых больше, чем черных
                return 1;
            else if (cross > circular)  // Черных больше, чем белых
                return 2;
            else //     нулевые счета
                return 3;
        }
        return -1;
    }

    public void controlElements(int arr[]) {        // Контролируем состояние, где какие элементы, см. консоль
        int cross = 0, circular = 0, point = 0; // Счетчики для Черных, пустых и Белых

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (gameCells[i][j].getCh() == 'X')             // Черные ячейки(Игрок)
                    cross++;
                else if (gameCells[i][j].getCh() == 'O')        // Белые ячейки
                    circular++;
                else if (gameCells[i][j].getCh() == '.')        // Пустые ячейки
                    point++;
            }
        }
        arr[0] = cross;     // Записываем полученные значения счетчиков в массив, который передаем endOfGame(выше)
        arr[1] = circular;
        arr[2] = point;
    }

    //метод начинает новую игру, внутри инициализируем поле и заполняем его
    //стартовыми значениями, чистим доску

    public void reset() {
        int mid = rows / 2;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = (char) (97 + j);
                if ((i == mid - 1) && (j == mid - 1)) {
                    gameCells[i][j].setPosition(c, 'X', i + 1);
                } else if ((i == mid - 1) && (j == mid)) {
                    gameCells[i][j].setPosition(c, 'O', i + 1);
                } else if ((i == mid) && (j == mid - 1)) {
                    gameCells[i][j].setPosition(c, 'O', i + 1);
                } else if ((i == mid) && (j == mid)) {
                    gameCells[i][j].setPosition(c, 'X', i + 1);
                } else {
                    gameCells[i][j].setPosition(c, '.', i + 1);
                }
              //  System.out.printf("i : %d, j : %d, c : %c\n", i, j, gameCells[i][j].getCh());
            }
        }
    }

    // Алгоритм хода для бота и для пользователя
    private int move(int xCor, int yCor,
                     int change, // сделан ли шаг (0 - нет, 1 - да)
                     char char1, // черная фишка
                     char char2, // белая фишка
                     int[] numberOfMoves) {
        int cont, st2 = 0, st = 0;
        int status = -1, corX, corY, temp;
        char str;
        int ix, y, x;

        x = xCor;
        y = yCor;
        numberOfMoves[0] = 0;
        if ((x + 1 < rows) && (gameCells[x + 1][y].getCh() == char2)) {
            cont = x;
            while ((cont < rows) && (st2 != -1) && (st != 2)) { // -1 - нельзя ходить
                // 2 - черных ячеек больше, чем белых
                cont++;
                if (cont < rows) {
                    if (gameCells[cont][y].getCh() == char2)
                        st = 1; // белых ячеек больше, чем черных
                    else if (gameCells[cont][y].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = cont - x - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = x; i < cont; i++) {
                    str = gameCells[i][y].getCorX();
                    ix = gameCells[i][y].getCorY();
                    gameCells[i][y].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (gameCells[x - 1][y].getCh() == char2)) {
            cont = x;
            while ((cont >= 0) && (st2 != -1) && (st != 2)) {
                cont--;
                if (cont >= 0) {
                    if (gameCells[cont][y].getCh() == char2)
                        st = 1;
                    else if (gameCells[cont][y].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = x - cont - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = cont; i <= x; ++i) {
                    str = gameCells[i][y].getCorX();
                    ix = gameCells[i][y].getCorY();
                    gameCells[i][y].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((y - 1 >= 0) && (gameCells[x][y - 1].getCh() == char2)) {
            cont = y;
            while ((cont >= 0) && (st2 != -1) && (st != 2)) {
                cont--;
                if (cont >= 0) {
                    if (gameCells[x][cont].getCh() == char2)
                        st = 1;
                    else if (gameCells[x][cont].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = y - cont - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                for (int i = cont; i <= y; ++i) {
                    str = gameCells[x][i].getCorX();
                    ix = gameCells[x][i].getCorY();
                    gameCells[x][i].setPosition(str, char1, ix);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (y + 1 < cols) && (gameCells[x - 1][y + 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX >= 0) && (corY < cols) && (st2 != -1) && (st != 2)) {
                corX--;
                corY++;
                if ((corX >= 0) && (corY < cols)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = x - corX - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX <= x) && (y < corY)) {
                    corX++;
                    corY--;
                    if ((corX <= x) && (y <= corY)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (y - 1 >= 0) && (gameCells[x - 1][y - 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX >= 0) && (corY >= 0) && (st2 != -1) && (st != 2)) {
                corX--;
                corY--;
                if ((corX >= 0) && (corY >= 0)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = x - corX - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX <= x) && (corY <= y)) {
                    corX++;
                    corY++;
                    if ((corX <= x) && (corY <= y)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x + 1 < rows) && (y + 1 < cols) && (gameCells[x + 1][y + 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX < rows) && (corY < cols) && (st2 != -1) && (st != 2)) {
                corX++;
                corY++;
                if ((corX < rows) && (corY < cols)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX >= x) && (corY >= y)) {
                    corX--;
                    corY--;
                    if ((corX >= x) && (corY >= y)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x + 1 < rows) && (y - 1 >= 0) && (gameCells[x + 1][y - 1].getCh() == char2)) {
            corY = y;
            corX = x;
            while ((corX < rows) && (corY >= 0) && (st2 != -1) && (st != 2)) {
                corX++;
                corY--;
                if ((corX < rows) && (corY >= 0)) {
                    if (gameCells[corX][corY].getCh() == char2)
                        st = 1;
                    else if (gameCells[corX][corY].getCh() == char1)
                        st = 2;
                    else
                        st2 = -1;
                }
            }
            if (st == 2) {
                temp = corX - x - 1;
                numberOfMoves[0] += temp;
            }
            if (st == 2 && change == 1) {
                while ((corX >= x) && (corY <= y)) {
                    corX--;
                    corY++;
                    if ((corX >= x) && (corY <= y)) {
                        str = gameCells[corX][corY].getCorX();
                        ix = gameCells[corX][corY].getCorY();
                        gameCells[corX][corY].setPosition(str, char1, ix);
                    }
                }
                status = 0;
            }
        }
        if (status == 0)
            return 0;
        else
            return -1;
    }
}