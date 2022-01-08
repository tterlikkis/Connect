package Connect;
public class Connect {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    public static void main(String[] args)
    {
        Window WINDOW = new Window(WIDTH, HEIGHT, "Connect");
        mainMenu(WINDOW);
    }

    public static void mainMenu(Window W)
    {
        W.getContentPane().removeAll();
        MainMenu menu = new MainMenu(WIDTH, HEIGHT, W);
        W.add(menu);
        W.revalidate();
        W.repaint();
    }

    public static void customMenu(Window W)
    {
        W.getContentPane().removeAll();
        CustomMenu custom = new CustomMenu(WIDTH, HEIGHT, W);
        W.add(custom);
        W.revalidate();
        W.repaint();
    }

    public static void game(int c, int r, int pC, int vC, Window W)
    {
        W.getContentPane().removeAll();
        Game game = new Game(WIDTH, HEIGHT, c, r, pC, vC, W);
        W.add(game);
        W.revalidate();
        W.repaint();
    }
}