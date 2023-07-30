import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class apps extends JFrame {


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
    private JButton connectButton;
    private JPanel mainPanel;
    private JTextField upField;
    private JCheckBox conectedCheckBox;
    private JButton startButton;
    private JButton stopButton;
     private JTextField downField;
    private  JComboBox comboBox;
    private JButton searchButton;
    private JButton manualButton;
    private JButton disconnectButton;
    private JButton rotaryLeftButton;
    private JButton rotaryRightButton;
    private JTextField infoField;
    private JTextPane datatextPane;
    private JButton pidAvlues;
    private JButton chartButton;

    pidRegulator pid = new pidRegulator(); // creating of pid class instance

    static int rotaryLeft=0;

    static int rotaryRight=0;

    static int manual=0;

    static int start=0;

    static int stop=1;

    static boolean startWork = false;

    static int numberPort=0;

    int buttonH = 30; // button height
    int buttonW = 150; // button width

    SerialPort[] comPort = SerialPort.getCommPorts(); // create static variable witch is counting avalible ports

    public static void main(String[] args) { // testing
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    apps frame = new apps();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public apps() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBounds(200, 200, 500, 500);
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane( mainPanel);
        mainPanel.setLayout(null);

        final JButton startButton = new JButton("START");
        startButton.setBounds(30, 30, buttonW,  buttonH);
        mainPanel.add(startButton);


        final  JButton stopButton = new JButton("STOP");
        stopButton.setBounds(30, 70, buttonW,  buttonH);
        mainPanel.add(stopButton);

        JButton searchButton = new JButton("SEARCH");
        searchButton.setBounds(30, 110, buttonW,  buttonH);
        mainPanel.add(searchButton);

        final JButton connectButton = new JButton("CONNECT");
        connectButton.setBounds(30, 150, buttonW,  buttonH);
        mainPanel.add(connectButton);

        final JButton disconnectButton = new JButton("DISCONNECT");
        disconnectButton.setBounds(30, 190, buttonW,  buttonH);
        mainPanel.add(disconnectButton);

        final  JButton rotaryLeftButton = new JButton("ROTARY LEFT");
        rotaryLeftButton.setBounds(30, 230, buttonW,  buttonH);
        mainPanel.add(rotaryLeftButton);

        final  JButton rotaryRightButton = new JButton("ROTARY RIGHT");
        rotaryRightButton.setBounds(30, 270, buttonW,  buttonH);
        mainPanel.add(rotaryRightButton);

        final JButton manualButton = new JButton("MANUAL");
        manualButton.setBounds(30, 310, buttonW,  buttonH);
        mainPanel.add(manualButton);

        final  JButton pidAvlues = new JButton("PID");
        pidAvlues.setBounds(30, 350,  buttonW,  buttonH);
        mainPanel.add(pidAvlues);

        final  JButton startsendingVal = new JButton("SENDING");
        startsendingVal.setBounds(30, 390,  buttonW,  buttonH);
        mainPanel.add(startsendingVal);

        final JButton chartButton = new JButton("CHART");
        chartButton.setBounds(250, 390, buttonW,  buttonH);
        mainPanel.add(chartButton);

        final JComboBox comboBox=new JComboBox();
        comboBox.setBounds(190, 110,260,30);

        mainPanel.add(comboBox);
        mainPanel.setLayout(null);
        mainPanel.setSize(400,500);
        mainPanel.setVisible(true);

        final  JTextField infoField  =new JTextField("info");
        infoField .setBounds(190,190, 200,30);
        mainPanel.add(infoField );
        mainPanel.setSize(400,400);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        final JTextField upField  =new JTextField("up");
        upField .setBounds(190,230, 200,30);
        mainPanel.add(upField );
        mainPanel.setSize(400,400);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        final  JTextField downField  =new JTextField("down");
        downField .setBounds(190,270, 200,30);
        mainPanel.add(downField );
        mainPanel.setSize(400,400);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        final JTextArea datatextPane=new JTextArea("text");
        datatextPane.setBounds(190,30, 260,70);
        mainPanel.add(datatextPane);
        mainPanel.setSize(300,300);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        final JCheckBox conectedCheckBox = new JCheckBox("connected?");
        conectedCheckBox.setBounds(190,150, 150,30);
        mainPanel.add(conectedCheckBox);
        mainPanel.setSize(400,400);
        mainPanel.setLayout(null);
        mainPanel.setVisible(true);


        try {
            comboBox.addItem(SerialPort.getCommPorts()[0]); // searching of open stm32 port
        }catch(ArrayIndexOutOfBoundsException e)
        {
            datatextPane.setText("procesor stm32 is not connected to PC");
        }
        final toByte bytes = new toByte(comPort, comboBox.getSelectedIndex()); // creating an instance of class witch is communicating with stm32 processor

        new Thread(bytes).start(); // start thread with communication with stm32
        final  scope scope = new scope( "Rotary inverted pendulum scope","Time", "Torque & pendulum Placement",bytes.getMotorPWM()); // creating an scope object instance


        connectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) { // port com is opening
                try {
                    toByte.change=true;
                    comPort[comboBox.getSelectedIndex()].openPort();
                    numberPort=comboBox.getSelectedIndex();
                    if (comPort[comboBox.getSelectedIndex()].isOpen() == true) {
                        conectedCheckBox.setSelected(true);
                        infoField.setText("PC is connected with port");

                    }
                }catch(IndexOutOfBoundsException g)
                {
                    infoField.setText("Choose some port");
                }
            }
        });


        startsendingVal.addActionListener(new ActionListener() { // button witch is not use anymore

            public void actionPerformed(ActionEvent e) {


            }
        });

        chartButton.addActionListener(new ActionListener() { // chart is switch on

            public void actionPerformed(ActionEvent e) {
                Thread th = new Thread(scope);
                th.start();

            }
        });

        startButton.addActionListener(new ActionListener() { // allow to start working

            public void actionPerformed(ActionEvent e) {
                toByte.change=true;
                startWork =true;
                start = 1;
                stop = 0;
                manual = 0;
            }
        });
        stopButton.addActionListener(new ActionListener() { // work is forbidden

            public void actionPerformed(ActionEvent e) {
                toByte.change=true;
                startWork = false;
                stop = 1;
                start = 0;
                manual = 0;
            }
        });

        searchButton.addActionListener(new ActionListener() { // search of any device

            public void actionPerformed(ActionEvent e) {
                comboBox.removeAllItems();
                try {

                    SerialPort[] amountOfPorts = SerialPort.getCommPorts();
                   for(int i=0;i<amountOfPorts.length;i++) {
                       comboBox.addItem(SerialPort.getCommPorts()[i]);
                   }
                }catch(IndexOutOfBoundsException g){
                }
            }
        });
        disconnectButton.addActionListener(new ActionListener() { // disconnect device
            public void actionPerformed(ActionEvent e) {
                try {
                    toByte.change=true;
                    startWork = false;
                    comPort[comboBox.getSelectedIndex()].closePort();

                    comPort[comboBox.getSelectedIndex()].removeDataListener();
                    if (comPort[comboBox.getSelectedIndex()].isOpen() == false) {
                        conectedCheckBox.setSelected(false);
                        infoField.setText("Rozłączono z portem COM");
                    }
                }catch(IndexOutOfBoundsException g){
                }
            }
        });


        rotaryLeftButton.addActionListener(new ActionListener() { // manual mode turn arm left

            public void actionPerformed(ActionEvent e) {
                toByte.change=true;
                rotaryLeft = 1;
                rotaryRight =0;
            }
        });
        rotaryRightButton.addActionListener(new ActionListener() { // manual mode turn arm right

            public void actionPerformed(ActionEvent e) {
                toByte.change=true;
                rotaryLeft = 0;
                rotaryRight =1;
            }
        });
        manualButton.addActionListener(new ActionListener() {  // start manual mode

            public void actionPerformed(ActionEvent e) {
                toByte.change=true;
                manual = 1;
                stop = 0;
                start = 0;
                startWork =false;
            }
        });
        pidAvlues.addActionListener(new ActionListener() { // open a window with pid values

            public void actionPerformed(ActionEvent e) {
                pid .setVisible(true);
                pid .pack();
                pid .setLocationRelativeTo(null);
                pid .setBounds(420, 0, 220, 220);
                pid.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

            }
        });
    }


}


