package Connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JPanel{
   
    private int frameWIDTH, frameHEIGHT;
    private Window window;

    public MainMenu(int w, int h, Window W)
    {
        frameWIDTH = w;
        frameHEIGHT = h;
        window = W;
        
        setBackground(Color.CYAN);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBounds(0, 0, frameWIDTH, frameHEIGHT);
        addTitle();
        addClassicButton();
        addCustomButton();
        addExitButton();
    }

    private void addTitle()
    {
        JLabel title = new JLabel("CONNECT");
        title.setFont(new Font("Impact", Font.CENTER_BASELINE, 128));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(title);
        addSpacer(90);
    }

    private void addClassicButton()
    {
        JButton classic = new JButton("Classic");
        ActionListener classicListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connect.game(7, 6, 2, 4, window);
            }
        };
        classic.addActionListener(classicListener);
        classic.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(classic);
        addSpacer(30);
    }

    private void addCustomButton()
    {
        JButton custom = new JButton("Custom");
        ActionListener customListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connect.customMenu(window);
            }
        };
        custom.addActionListener(customListener);
        custom.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(custom);
        addSpacer(30);
    }

    private void addExitButton()
    {
        JButton exit = new JButton("Exit");
        ActionListener exitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        };
        exit.addActionListener(exitListener);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(exit);
        addSpacer(30);
    }

    private void addSpacer(int h)
    {
        JPanel spacer = new JPanel();
        spacer.setMaximumSize(new Dimension(10, h));
        spacer.setAlignmentX(Component.CENTER_ALIGNMENT);
        spacer.setBackground(Color.CYAN);
        this.add(spacer);
    }
}
