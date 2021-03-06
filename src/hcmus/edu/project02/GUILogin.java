package hcmus.edu.project02;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.TreeMap;

public class GUILogin extends JFrame {
    // Window width, height
    public static int DEFAULT_WIDTH = 350;
    public static int DEFAULT_HEIGHT = 170;
    public static int DEFAULT_COMPONENT_HEIGHT = 25;
    public static int DEFAULT_BTN_WIDTH = 100;
    public static int DEFAULT_INPUT_COLUMN = 20;
    public static EmptyBorder DEFAULT_EMPTY_BORDER = new EmptyBorder(10, 10, 10, 10);
    public static Dimension INPUT_FIELD_MAX_SIZE = new Dimension(Integer.MAX_VALUE, DEFAULT_COMPONENT_HEIGHT);
    public static Dimension BUTTON_MAX_SIZE = new Dimension(DEFAULT_BTN_WIDTH, DEFAULT_COMPONENT_HEIGHT);
    public static String PATH = "accounts.txt";

    // Large headerLabel
    JLabel headerLabel = new JLabel("<html><span style='font-size:16px;color:red>Login</span></html>");
    JFrame mainFrame = this;
    TreeMap<String, Account> accountMap = AccountsFileControllers.read(PATH);

    public GUILogin() throws IOException {
        // Set title for window
        setTitle("Register");
        // Setting the width and height of frame
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set main layout
        setLayout(new BorderLayout());

        // Create panel contains Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        add(headerPanel, BorderLayout.NORTH);
        // Put headerLabel to center of headerPanel
        setupHeader(headerPanel);

        // Create panel contains login form
        JPanel labelPanel = new JPanel();
        add(labelPanel, BorderLayout.WEST);
        JPanel tfPanel = new JPanel();
        add(tfPanel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);
        // Put components into form
        initializeForm(labelPanel, tfPanel, btnPanel);

        // Setting the frame visibility to true
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void setupHeader(JPanel headerPanel) {
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setVerticalAlignment(JLabel.CENTER);
        headerPanel.add(headerLabel);
    }


    public void initializeForm(JPanel labelPanel, JPanel tfPanel, JPanel btnPanel) {

        // labelPanel setting
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(DEFAULT_EMPTY_BORDER);
        // user label
        JLabel userLabel = new JLabel("User");
        labelPanel.add(userLabel);
        // password label
        JLabel pwdLabel = new JLabel("Password");
        marginLabelTop(pwdLabel, 10);
        labelPanel.add(pwdLabel);

        // tfPanel setting
        tfPanel.setLayout(new BoxLayout(tfPanel, BoxLayout.Y_AXIS));
        tfPanel.setBorder(DEFAULT_EMPTY_BORDER);
        // user input text field
        JTextField userInput = new JTextField(DEFAULT_INPUT_COLUMN);
        userInput.setMaximumSize(INPUT_FIELD_MAX_SIZE);
        tfPanel.add(userInput);
        // pwd input text field
        JPasswordField pwdInput = new JPasswordField(DEFAULT_INPUT_COLUMN);
        pwdInput.setMaximumSize(INPUT_FIELD_MAX_SIZE);
        tfPanel.add(pwdInput);

        // Button Panel
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setBorder(DEFAULT_EMPTY_BORDER);
        // login button
        JButton loginBtn = new JButton("Login");
        loginBtn.setMaximumSize(BUTTON_MAX_SIZE);
        btnPanel.add(loginBtn);
        // login btn action listener
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userInput.getText();
                String pwd = new String(pwdInput.getPassword());

                boolean access = true;
                if (!accountMap.containsKey(user)) {
                    access = false;
                    JOptionPane.showMessageDialog(mainFrame,"User not found!!!");
                }
                if (access) {
                    Account current = new Account(user, pwd);
                    if (current.equals(accountMap.get(current.getUser()))) {
                        mainFrame.dispose();
                        GUIMessage app = new GUIMessage(user);
                    }
                    else JOptionPane.showMessageDialog(mainFrame,"NO!!!");
                }
            }
        });

        // Register Btn
        JButton regBtn = new JButton("Register");
        regBtn.setMaximumSize(BUTTON_MAX_SIZE);
        btnPanel.add(regBtn);
        // RegBtn action listener
        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    mainFrame.dispose();
                    JFrame regPage = new GUIRegister();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    private static void marginLabelTop(JLabel target, int size) {
        target.setBorder(new EmptyBorder(size, 0, 0 ,0));
    }
}
