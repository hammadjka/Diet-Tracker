package clientSide.view;


import clientSide.control.Manager;
import clientSide.model.Consumable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * A class to output the main gui frame and perform actions on it.
 *
 * @author Hammad
 */
public class UserInterface implements ActionListener {

    private final Manager consumableManager = new Manager();
    private final JFrame applicationFrame;
    private JTextField indexField;
    private JDialog removeDialog;
    private JScrollPane ScrollPane;
    JPanel mainPanel = new JPanel();
    JLabel outputLabel;
    private final Font customFont = new Font("Sans Serif", Font.PLAIN, 13);

    /**
     * A constructor for the class that outputs the screen.
     */
    public UserInterface() {
        applicationFrame = new JFrame("Hammad's Food Diary");
        applicationFrame.setSize(650, 600);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addItem = new JButton("Add item");
        addItem.setBackground(Color.white);
        addItem.setFocusable(false);
        addItem.addActionListener(this);
        addItem.setFont(customFont);

        JButton removeItem = new JButton("Remove item");
        removeItem.setBackground(Color.WHITE);
        removeItem.setFocusable(false);
        removeItem.addActionListener(this);
        removeItem.setFont(customFont);

        JButton allItems = new JButton("All items");
        allItems.setBackground(Color.WHITE);
        allItems.setFocusable(false);
        allItems.addActionListener(this);
        allItems.setFont(customFont);

        JButton expiredItems = new JButton("Expired items");
        expiredItems.setBackground(Color.WHITE);
        expiredItems.setFocusable(false);
        expiredItems.addActionListener(this);
        expiredItems.setFont(customFont);

        JButton notExpired = new JButton("Non-expired items");
        notExpired.setBackground(Color.WHITE);
        notExpired.setFocusable(false);
        notExpired.addActionListener(this);
        notExpired.setFont(customFont);

        JButton expiringIn7 = new JButton("Items expiring in 7 days");
        expiringIn7.setBackground(Color.WHITE);
        expiringIn7.setFocusable(false);
        expiringIn7.addActionListener(this);
        expiringIn7.setFont(customFont);


        JPanel topPanel = new JPanel();
        topPanel.add(allItems);
        topPanel.add(expiredItems);
        topPanel.add(notExpired);
        topPanel.add(expiringIn7);
        topPanel.setBackground(Color.DARK_GRAY);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.add(addItem);
        bottomPanel.add(removeItem);

        JPanel eastPanel = new JPanel();
        eastPanel.add(Box.createRigidArea(new Dimension(25, 20)));
        eastPanel.setBackground(Color.DARK_GRAY);

        JPanel westPanel = new JPanel();
        westPanel.add(Box.createRigidArea(new Dimension(25, 20)));
        westPanel.setBackground(Color.DARK_GRAY);

        ScrollPane = new JScrollPane();

        applicationFrame.add(topPanel, BorderLayout.NORTH);
        applicationFrame.add(bottomPanel, BorderLayout.SOUTH);
        applicationFrame.add(eastPanel, BorderLayout.EAST);
        applicationFrame.add(westPanel, BorderLayout.WEST);
        applicationFrame.add(ScrollPane, BorderLayout.CENTER);
        applicationFrame.getContentPane().setBackground(Color.DARK_GRAY);
        applicationFrame.setVisible(true);

    }

    /**
     * updates the main scroll pane with the list of items provided.
     *
     * @parameters a list of Consumables
     */
    private void outputPane(List<Consumable> consumableList) {

        applicationFrame.remove(ScrollPane);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        Border blackline;
        Border dottedLine = BorderFactory.createLineBorder(Color.black);

        String food;
        String type;

        for (int i = 1; i <= consumableList.size(); i++) {

            if (consumableList.get(i - 1).getType() == 1) {
                type = "Food";
            } else {
                type = "Drink";
            }

            food = consumableList.get(i - 1).toString();
            String header = "Item #" + i + " (" + type + ")";
            blackline = BorderFactory.createTitledBorder(dottedLine, header);

            outputLabel = new JLabel(food + "<br>");
            outputLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
            outputLabel.setBorder(blackline);
            mainPanel.add(outputLabel);

        }
        ScrollPane = new JScrollPane(mainPanel);
        applicationFrame.add(ScrollPane, BorderLayout.CENTER);
        applicationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        applicationFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                updateList();
                System.exit(0);
            }
        });
        applicationFrame.setVisible(true);
    }

    /**
     * outputs a dialog frame to remove an item.
     */
    public void remove() {
        removeDialog = new JDialog();
        JPanel mainPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JLabel removeLabel = new JLabel("Enter Item # to remove: ");
        JButton removeButton = new JButton("Remove");
        indexField = new JTextField(5);

        removeDialog.setTitle("Remove item");
        removeDialog.setSize(300, 250);

        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 25, 25));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(removeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(indexField, constraints);

        bottomPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.add(removeButton);

        removeButton.setBackground(Color.WHITE);
        removeButton.setFocusable(false);
        removeButton.addActionListener(this);
        removeButton.setFont(customFont);

        removeLabel.setForeground(Color.WHITE);
        mainPanel.add(removeLabel);
        mainPanel.add(indexField);
        mainPanel.setBackground(Color.DARK_GRAY);
        removeDialog.add(bottomPanel, BorderLayout.SOUTH);
        removeDialog.add(mainPanel, BorderLayout.CENTER);
        removeDialog.setVisible(true);
    }

    /**
     * performs acton relative to the button that is clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("All items")) {
            updateScreen();
        } else if (e.getActionCommand().equals("Expired items")) {

            List<Consumable> tmp = consumableManager.getexpired();
            JLabel l = new JLabel("<html> No expired items to show." + "<html>");
            updateScreen(tmp, l);

        } else if (e.getActionCommand().equals("Non-expired items")) {

            List<Consumable> tmp = consumableManager.getNotExpired();
            JLabel l = new JLabel("<html> No non-expired items to show." + "<html>");
            updateScreen(tmp, l);

        } else if (e.getActionCommand().equals("Items expiring in 7 days")) {

            List<Consumable> tmp = consumableManager.getExpireIn7();
            JLabel l = new JLabel("<html> No items expiring in 7 days to show." + "<html>");
            updateScreen(tmp, l);

        } else if (e.getActionCommand().equals("Add item")) {

            AddItem add = new AddItem();
            add.addItem(this);

        } else if (e.getActionCommand().equals("Remove item")) {
            updateScreen();
            remove();
        } else if (e.getActionCommand().equals("Remove")) {

            if (indexField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(removeDialog, "Invalid Input");
                return;
            }
            int index = Integer.parseInt(indexField.getText());
            if (!consumableManager.isValid(index)) {
                JOptionPane.showMessageDialog(removeDialog, "Invalid Input");
            } else {
                try {
                    consumableManager.remove(index);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                updateScreen();
            }
            indexField.setText("");
            removeDialog.dispose();
        }
    }

    /**
     * A getter for the manager field of the class.
     *
     * @return manager.
     */
    public Manager getManager() {
        return consumableManager;
    }

    /**
     * updates the main scroll pane with the list of all items.
     */
    public void updateScreen() {
        mainPanel = new JPanel();
        if (consumableManager.getFoods().size() == 0) {
            outputLabel = new JLabel("<html> No items to show." + "<html>");
            outputLabel.setFont(new Font("Serif", Font.BOLD, 20));
            mainPanel.add(outputLabel);
        }
        outputPane(consumableManager.getFoods());
    }

    /**
     * updates the main scroll pane and sets the outputLabel accordingly to the list of items provided .
     *
     * @parameters a list of Consumables and a label
     */
    public void updateScreen(List<Consumable> consumableList, JLabel noItems) {
        mainPanel = new JPanel();
        if (consumableList.size() == 0) {
            outputLabel = noItems;
            outputLabel.setFont(new Font("Serif", Font.BOLD, 20));
            mainPanel.add(outputLabel);
        }
        outputPane(consumableList);
    }

    /**
     * calls the manager to save the list of all items to json.
     */
   public void updateList() {
       try {
           consumableManager.exit();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}