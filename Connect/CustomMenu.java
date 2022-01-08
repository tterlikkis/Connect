package Connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomMenu extends JPanel {
    
    private int frameWIDTH, frameHEIGHT;
    private Window window;

    private JSpinner cSpinner, rSpinner;
    private JSpinner pCSpinner, vCSpinner;

    public CustomMenu(int w, int h, Window W)
    {
        frameWIDTH = w;
        frameHEIGHT = h;
        window = W;

        setBackground(Color.CYAN);
        setLayout(new GridLayout(7, 4, 0, 50));
        setBounds(0, 0, frameWIDTH, frameHEIGHT);
        addLayer();
        addColumns();
        addRows();
        addPlayerCount();
        addVictoryChain();
        addBackButton();
        addPlayButton();
        addLayer();
    }

    private void addColumns() 
    {
        SpinnerModel cModel = new SpinnerNumberModel(7, 3, 10, 1);
        cSpinner = new JSpinner(cModel);
        cSpinner.setEditor(new JSpinner.DefaultEditor(cSpinner));
        cSpinner.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        JLabel cLabel = new JLabel("Columns: ");
        cLabel.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        addSpacer();
        this.add(cLabel);
        this.add(cSpinner);
        addSpacer();
    }

    private void addRows() 
    {
        SpinnerModel rModel = new SpinnerNumberModel(6, 3, 10, 1);
        rSpinner = new JSpinner(rModel);
        rSpinner.setEditor(new JSpinner.DefaultEditor(rSpinner));
        rSpinner.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        JLabel rLabel = new JLabel("Rows: ");
        rLabel.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        addSpacer();
        this.add(rLabel);
        this.add(rSpinner);
        addSpacer();
    }

    private void addPlayerCount() 
    {
        SpinnerModel pCModel = new SpinnerNumberModel(2, 2, 4, 1);
        pCSpinner = new JSpinner(pCModel);
        pCSpinner.setEditor(new JSpinner.DefaultEditor(pCSpinner));
        pCSpinner.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        JLabel pCLabel = new JLabel("Players: ");
        pCLabel.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        addSpacer();
        this.add(pCLabel);
        this.add(pCSpinner);
        addSpacer();
    }
    private void addVictoryChain() 
    {
        SpinnerModel vCModel = new SpinnerNumberModel(4, 3, 5, 1);
        vCSpinner = new JSpinner(vCModel);
        vCSpinner.setEditor(new JSpinner.DefaultEditor(vCSpinner));
        vCSpinner.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        JLabel vCLabel = new JLabel("Length 2 Win: ");
        vCLabel.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));

        addSpacer();
        this.add(vCLabel);
        this.add(vCSpinner);
        addSpacer();
    }

    private void addPlayButton()
    {
        JButton play = new JButton("Play");
        ActionListener playListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if ((int)vCSpinner.getValue() < (int)cSpinner.getValue() || (int)vCSpinner.getValue() < (int)rSpinner.getValue())
                    Connect.game((int)cSpinner.getValue(), (int)rSpinner.getValue(), (int)pCSpinner.getValue(), (int)vCSpinner.getValue(), window);
                else
                    displayInvalid();    
            }
        };

        play.addActionListener(playListener);
        play.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));
        addSpacer();
        addSpacer();
        this.add(play);
    }

    private void addBackButton()
    {
        JButton back = new JButton("Back");
        ActionListener backListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Connect.mainMenu(window);
            }
        };

        back.addActionListener(backListener);
        back.setFont(new Font("Impact", Font.CENTER_BASELINE, 32));
        this.add(back);
    }

    private void displayInvalid()
    {
        JDialog invalid = new JDialog(window, String.format("Error"), true);
        invalid.setSize(frameWIDTH / 2, frameHEIGHT / 4);
        invalid.setLocationRelativeTo(this);
        invalid.setLayout(new FlowLayout());

        JLabel message1 = new JLabel("You've made an impossible game!"); 
        invalid.add(message1);
        JLabel message2 = new JLabel("Make sure you have enough columns and rows!");
        invalid.add(message2);

        ActionListener okListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                invalid.setVisible(false);
            }
        };

        JButton ok = new JButton("OK");  invalid.add(ok);
        ok.addActionListener(okListener);

        invalid.setVisible(true);
    }

    private void addSpacer()
    {
        JPanel spacer = new JPanel();
        spacer.setBackground(Color.CYAN);
        this.add(spacer);
    }

    private void addLayer()
    {
        for (int i = 0; i < 4; i++)
            addSpacer();
    }
}
