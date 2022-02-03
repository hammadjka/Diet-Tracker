package clientSide.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import clientSide.model.ConsumableFactory;
import clientSide.model.Consumable;
import com.github.lgooddatepicker.components.DatePicker;

/**
 * A class to output the Add item gui dialog and perform actions on it.
 *
 * @author Hammad
 */
public class AddItem extends JDialog implements ActionListener {

    private JComboBox typeBox = new JComboBox();
    private final JPanel bottomPanel = new JPanel();

    private JLabel weightLabel;

    private JTextField nameField = new JTextField(25);
    private JTextField notesField = new JTextField(25);
    private JTextField priceField = new JTextField(25);
    private JTextField weightField = new JTextField(25);
    private UserInterface mainScreen;
    DatePicker date = new DatePicker();

    /**
     * outputs the Add Item screen.
     */
    public void addItem(UserInterface UI) {
        setTitle("Add Item");
        setSize(400, 400);
        Font customFont = new Font("Sans Serif", Font.BOLD, 13);
        this.mainScreen = UI;

        typeBox.addItem("Food item");
        typeBox.addItem("Drink item");
        typeBox.addActionListener(this);
        typeBox.setFont(customFont);
        typeBox.setBackground(Color.WHITE);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setForeground(Color.white);
        typeLabel.setFont(customFont);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(customFont);

        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setForeground(Color.white);
        notesLabel.setFont(customFont);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(Color.white);
        priceLabel.setFont(customFont);

        weightLabel = new JLabel("Weight:");
        weightLabel.setForeground(Color.white);
        weightLabel.setFont(customFont);

        JLabel dateLabel = new JLabel("Expiry Date:");
        dateLabel.setForeground(Color.white);
        dateLabel.setFont(customFont);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridBagLayout());
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 50, 25));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        fieldPanel.add(typeLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        fieldPanel.add(typeBox, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        fieldPanel.add(nameLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        fieldPanel.add(nameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        fieldPanel.add(notesLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        fieldPanel.add(notesField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        fieldPanel.add(priceLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        fieldPanel.add(priceField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        fieldPanel.add(weightLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        fieldPanel.add(weightField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        fieldPanel.add(dateLabel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 5;
        fieldPanel.add(date, constraints);

        fieldPanel.setBackground(Color.DARK_GRAY);

        JButton addItem = new JButton("Add new item");
        JButton cancel = new JButton("Cancel");

        addItem.setBackground(Color.WHITE);
        addItem.setFocusable(false);
        addItem.addActionListener(this);
        addItem.setFont(customFont);

        cancel.setBackground(Color.WHITE);
        cancel.setFocusable(false);
        cancel.addActionListener(this);
        cancel.setFont(customFont);

        bottomPanel.add(addItem);
        bottomPanel.add(cancel);
        bottomPanel.setBackground(Color.DARK_GRAY);

        add(fieldPanel);
        add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().setBackground(Color.DARK_GRAY);
        setVisible(true);

    }

    /**
     * performs acton relative to the button that is clicked.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == typeBox) {

            if (typeBox.getSelectedItem() == "Drink item") {
                weightLabel.setText("Volume: ");
            } else {
                weightLabel.setText("Weight: ");
            }

        }
        if (e.getActionCommand().equals("Cancel")) {
            dispose();
        } else if (e.getActionCommand().equals("Add new item")) {

            int type;
            String name = nameField.getText();
            String notes = notesField.getText();
            LocalDate pickerDate = date.getDate();

            if (name.isEmpty() || pickerDate == null || priceField.getText().isEmpty() || weightField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            } else {
                LocalDateTime pickerDateTime = pickerDate.atTime(23, 59);
                double price = Double.parseDouble(priceField.getText());
                double weight = Double.parseDouble(weightField.getText());
                if (typeBox.getSelectedItem() == "Drink item") {
                    type = 2;
                } else {
                    type = 1;
                }
                ConsumableFactory consumableFactory = new ConsumableFactory();
                Consumable newFood = consumableFactory.getInstance(type, name, notes, price, weight, pickerDateTime);

                mainScreen.getManager().add(newFood);
                this.dispose();
                mainScreen.updateScreen();
            }
            nameField.setText("");
            notesField.setText("");
            priceField.setText("");
            weightField.setText("");
            date.setDate(null);
        }
    }
}
