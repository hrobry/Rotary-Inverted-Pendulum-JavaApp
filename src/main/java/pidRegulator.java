import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class pidRegulator extends JFrame {

    private JPanel pidPane;
    private JTextField P;
    private JTextField I;
    private JTextField D;
  ;

     static double pidP=0.1;

     static double pidI=0.1;

     static double pidD=0.1;

     static int reset=0;

    public static int getReset() {
        return reset;
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    pidRegulator frame = new pidRegulator();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public pidRegulator() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 150, 150);
        pidPane = new JPanel();
        pidPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(pidPane);
        pidPane.setLayout(null);

        JLabel proportionalLabel = new JLabel("P");
        proportionalLabel.setFont(new Font("High Tower Text", Font.BOLD, 15));
        proportionalLabel.setBounds(11, 20, 50, 15);
        pidPane.add(proportionalLabel);

        JLabel integratedLabel = new JLabel("I");
        integratedLabel .setFont(new Font("High Tower Text", Font.BOLD, 15));
        integratedLabel .setBounds(11, 61, 50, 15);
        pidPane.add(integratedLabel );

        JLabel differentiationLabel = new JLabel("D");
        differentiationLabel.setFont(new Font("High Tower Text", Font.BOLD, 15));
        differentiationLabel.setBounds(11, 101, 50, 15);
        pidPane.add(differentiationLabel);

        int width =150;

        int height = 30;


        P = new JTextField();
        P.setFont(new Font("High Tower Text", Font.BOLD, 15));
        P.setBounds(30, 15, width, height);
        P.setText("9.5");

        pidPane.add(P);
        P.setColumns(10);

        I = new JTextField();
        I.setFont(new Font("High Tower Text", Font.BOLD, 15));
        I.setBounds(30, 55, width, height);
        I.setText("0.00004");
        pidPane.add(I);
        I.setColumns(10);

        D = new JTextField();
        D.setFont(new Font("High Tower Text", Font.BOLD, 15));
        D.setBounds(30, 95, width, height);
        D.setText("3.9");
        pidPane.add(D);
        D.setColumns(10);

        JButton resetPID = new JButton("RESET");
        resetPID.setBounds(100, 130, 80, 40);
        pidPane.add(resetPID);

        resetPID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset =1;
            }
        });

        JButton sendPID = new JButton("SEND");
        sendPID.setBounds(20, 130, 80, 40);

        pidPane.add(sendPID);

        sendPID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toByte.change=true;
                pidP= Double.parseDouble( P.getText());
                pidI= Double.parseDouble( I.getText()) ;
                pidD= Double.parseDouble( D.getText()) ;

            }
        });
    }
}