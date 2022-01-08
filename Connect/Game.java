package Connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game extends JPanel {

    private int frameWIDTH, frameHEIGHT;
    private int boardWIDTH, boardHEIGHT;
    private int spaceWIDTH, spaceHEIGHT;
    private int boardX, boardY;
    
    private int columns, rows;
    private int victoryChain;

    private Color playerColor;
    private int playerCount;
    private int turn;
    private int player;


    private int[][] state;

    Window window;

    public Game(int wit, int h, int c, int r, int pC, int vC, Window win)
    {
        frameWIDTH = wit;
        frameHEIGHT = h;
        columns = c;
        rows = r;
        playerCount = pC;
        victoryChain = vC;
        window = win;

        turn = -1;

        state = new int[columns][rows];

        boardWIDTH = (int) (frameWIDTH * .7);
        boardHEIGHT = (int) (frameHEIGHT * .6);

        spaceWIDTH = (boardWIDTH / columns);
        spaceHEIGHT = (boardHEIGHT / rows);

        boardX = (int) (frameWIDTH * .15);
        boardY = (int) (frameHEIGHT * .2);
        
        clear();
        nextTurn();
        setBackground(Color.CYAN);
        setLayout(null);
        setBounds(0, 0, frameWIDTH, frameHEIGHT);
    }

    //used by areas to tell which color piece to have under mouse
    public Color getPlayerColor()
    {
        return playerColor;
    }

    //increases turn, mods it with playercount to get current player's turn, sets appropriate color
    public void nextTurn()
    {
        player = (++turn % playerCount) + 1;

        switch (player) {
            case 1:
                playerColor = Color.RED;
                break;
            case 2:
                playerColor = Color.BLUE;
                break;
            case 3:
                playerColor = Color.YELLOW;
                break;
            case 4:
                playerColor = Color.GREEN;
                break;
        }

        addBackButton();
    }

    //"drops" a piece down on the current column, it will stay in the lowest (aka i value) empty space. repaint and check for victory
    public void drop(int c)
    {
        boolean piecePlaced = false;

        int i;
        for (i = rows - 1; i >= 0; i--)
            if (state[c][i] == 0) {
                state[c][i] = player;
                piecePlaced = true;
                break;
            }

        if (piecePlaced) {

            if (victoryCheck(c, i))
                displayVictory();

            nextTurn();
        }
    }

    //runs submethods to search for victoryChains
    public boolean victoryCheck(int x, int y)
    {
        if (verticalCheck(x) || horizontalCheck(y) || diagonalCheck()) {
            validate();
            repaint();
            return true;
        }
        return false;
    }

    //checks for consecutive chain of current player in row y
    private boolean horizontalCheck(int y)
    {
        int chain = 0;

        for (int x = 0; x < columns; x++)
            if (state[x][y] == player) {
                chain++;
                if (chain >= victoryChain)
                    return true;
            }
            else
                chain = 0;

        return false;
    }

    //checks for consecutive chain of current player in column x
    private boolean verticalCheck(int x)
    {
        int chain = 0;
        
        for (int y = 0; y < rows; y++)
            if (state[x][y] == player) {
                chain++;
                if (chain >= victoryChain)
                    return true;
            }
            else
                chain = 0;
            
        return false;
    }

    //checks all positive and negative slope lines that could contain a diagonal victory chain
    private boolean diagonalCheck()
    {     
        int yMax = rows - 1;
        
        for (int xStart = 0; xStart <= columns - victoryChain; xStart++)
            if (upRight(xStart, 0) || downRight(xStart, yMax))
                return true;

        for (int yStart = 0; yStart <= rows - victoryChain; yStart++)
            if (upRight(0, yStart) || downRight(0, yStart + victoryChain - 1))
                return true;
        
        return false;
    }

    //check positive slopes
    private boolean upRight(int xStart, int yStart)
    {
        int chain = 0;
        int x = xStart;
        int y = yStart;

        for (;x < columns && y < rows; x++, y++) {
            if (state[x][y] == player) {
                chain++;
                if (chain >= victoryChain)
                    return true;
            }
            else
                chain = 0;
        }

        return false;
    }

    //check negative slopes
    private boolean downRight(int xStart, int yStart)
    {
        int chain = 0;
        int x = xStart;
        int y = yStart;

        for (;x < columns && y >= 0; x++, y--) {
            if (state[x][y] == player) {
                chain++;
                if (chain >= victoryChain)
                    return true;
            }
            else
                chain = 0;
        }

        return false;
    }


    //creates a dialog frame that displays victory and gives flow of control options
    private void displayVictory()
    {
        JDialog victory = new JDialog(window, String.format("Player %d Victory!", player), true);
        victory.setBackground(playerColor);
        victory.setLocationRelativeTo(null);
        victory.setSize(frameWIDTH / 4, frameHEIGHT / 4);
        victory.setLayout(new FlowLayout());

        ActionListener againListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear();
                turn = -1;
                victory.setVisible(false);
            }
        };

        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connect.mainMenu(window);
                victory.setVisible(false);
            }
        };

        ActionListener exitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        };

        JButton again = new JButton("Play Again");  victory.add(again);
        again.addActionListener(againListener);

        JButton menu = new JButton("Return to Main Menu");  victory.add(menu);
        menu.addActionListener(menuListener);

        JButton exit = new JButton("Close Game");   victory.add(exit);
        exit.addActionListener(exitListener);

        victory.setVisible(true);
    }

    //emptys board and repaints
    private void clear()
    {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++)
                state[i][j] = 0;
        validate();
        repaint();
    }

    //adds button to go back to main menu in bottom right
    private void addBackButton()
    {
        JButton back = new JButton("Main Menu");
        ActionListener backListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connect.mainMenu(window);
            }
        };

        back.addActionListener(backListener);
        back.setFont(new Font("Impact", Font.CENTER_BASELINE, 28));
        back.setBounds(boardX + (spaceWIDTH * 4), boardY + boardHEIGHT + 20, spaceWIDTH * 3, spaceHEIGHT);
        back.setVisible(true);
        window.add(back);
        window.repaint();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawBoard(g2d);
        drawGrid(g2d);
        drawMouseAreas(g2d);
        drawPlayerTurn(g2d);
    }

    //draws solid rectangle behind space grid
    private void drawBoard(Graphics2D g)
    {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(boardX, boardY, boardWIDTH, boardHEIGHT);
    }

    //draws space grid in front of "board" rectangle
    private void drawGrid(Graphics2D g)
    {
        for (int i = 0; i < columns; i++)
            for (int j = 0; j < rows; j++) {
                switch(state[i][j]) {
                    case 1:
                        g.setColor(Color.RED);
                        break;
                    case 2:
                        g.setColor(Color.BLUE);
                        break;
                    case 3:
                        g.setColor(Color.YELLOW);
                        break;
                    case 4:
                        g.setColor(Color.GREEN);
                        break;
                    default:
                        g.setColor(Color.CYAN);
                        break;
                }
                g.fillOval((spaceWIDTH * i + boardX) + 2, (spaceHEIGHT * j + boardY + 2), spaceWIDTH - 5, spaceHEIGHT - 5);
            }
    }

    //draw JPanels that act as mouselisteners
    private void drawMouseAreas(Graphics2D g)
    {
        for (int i = 0; i < columns; i++)
            this.add(new Area(i, spaceWIDTH, spaceHEIGHT, boardX, boardY, this));
    }

    //draw text to indicate current player's turn
    private void drawPlayerTurn(Graphics2D g)
    {
        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Impact", Font.CENTER_BASELINE, 24));
        g.drawString(String.format("Player %d's Turn", player), boardX, boardY + boardHEIGHT + 40);
    }
}