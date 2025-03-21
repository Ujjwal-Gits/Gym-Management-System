import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class GymGUI extends JFrame implements ActionListener {
    private ArrayList<GymMember> members = new ArrayList<>();
    private JTextField idField, nameField, locationField, phoneField, emailField, dobField, startDateField,
                       referralField, paidField, removalField, trainerField;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private JComboBox<String> planCombo;
    private JButton addRegularBtn, addPremiumBtn, activateBtn, deactivateBtn, markBtn, upgradeBtn,
                     discountBtn, revertRegBtn, revertPremBtn, payBtn, displayBtn, clearBtn, saveBtn, loadBtn;

    public GymGUI() {
        setTitle("Gym Management System");
        setSize(800, 600);
        setLayout(new GridLayout(0, 2, 10, 10));

        // Initialize components
        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        dobField = new JTextField("YYYY-MM-DD");
        startDateField = new JTextField("YYYY-MM-DD");
        referralField = new JTextField();
        paidField = new JTextField();
        removalField = new JTextField();
        trainerField = new JTextField();

        ButtonGroup genderGroup = new ButtonGroup();
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup.add(maleRadio); genderGroup.add(femaleRadio); genderGroup.add(otherRadio);

        planCombo = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});

        // Buttons
        addRegularBtn = new JButton("Add Regular");
        addPremiumBtn = new JButton("Add Premium");
        activateBtn = new JButton("Activate");
        deactivateBtn = new JButton("Deactivate");
        markBtn = new JButton("Mark Attendance");
        upgradeBtn = new JButton("Upgrade Plan");
        discountBtn = new JButton("Calculate Discount");
        revertRegBtn = new JButton("Revert Regular");
        revertPremBtn = new JButton("Revert Premium");
        payBtn = new JButton("Pay Due");
        displayBtn = new JButton("Display All");
        clearBtn = new JButton("Clear");
        saveBtn = new JButton("Save");
        loadBtn = new JButton("Load");

        // Add components to frame
        add(new JLabel("ID:")); add(idField);
        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Location:")); add(locationField);
        add(new JLabel("Phone:")); add(phoneField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Gender:")); 
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadio); genderPanel.add(femaleRadio); genderPanel.add(otherRadio);
        add(genderPanel);
        add(new JLabel("DOB:")); add(dobField);
        add(new JLabel("Membership Start:")); add(startDateField);
        add(new JLabel("Referral Source:")); add(referralField);
        add(new JLabel("Paid Amount:")); add(paidField);
        add(new JLabel("Removal Reason:")); add(removalField);
        add(new JLabel("Trainer Name:")); add(trainerField);
        add(new JLabel("Plan:")); add(planCombo);
        add(addRegularBtn); add(addPremiumBtn);
        add(activateBtn); add(deactivateBtn);
        add(markBtn); add(upgradeBtn);
        add(discountBtn); add(revertRegBtn);
        add(revertPremBtn); add(payBtn);
        add(displayBtn); add(clearBtn);
        add(saveBtn); add(loadBtn);

        // Add action listeners
        for (Component c : getContentPane().getComponents())
            if (c instanceof JButton) ((JButton)c).addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addRegularBtn) {
                int id = Integer.parseInt(idField.getText());
                String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "Other";
                RegularMember member = new RegularMember(id, nameField.getText(), locationField.getText(),
                        phoneField.getText(), emailField.getText(), gender, dobField.getText(),
                        startDateField.getText(), referralField.getText());
                members.add(member);
                JOptionPane.showMessageDialog(this, "Regular member added!");
            } else if (e.getSource() == addPremiumBtn) {
                int id = Integer.parseInt(idField.getText());
                String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "Other";
                PremiumMember member = new PremiumMember(id, nameField.getText(), locationField.getText(),
                        phoneField.getText(), emailField.getText(), gender, dobField.getText(),
                        startDateField.getText(), trainerField.getText());
                members.add(member);
                JOptionPane.showMessageDialog(this, "Premium member added!");
            } else if (e.getSource() == activateBtn || e.getSource() == deactivateBtn) {
                int id = Integer.parseInt(idField.getText());
                for (GymMember m : members) {
                    if (m.getId() == id) {
                        if (e.getSource() == activateBtn) m.activateMembership();
                        else m.deactivateMembership();
                        JOptionPane.showMessageDialog(this, "Membership updated!");
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (e.getSource() == markBtn) {
                int id = Integer.parseInt(idField.getText());
                for (GymMember m : members) {
                    if (m.getId() == id) {
                        m.markAttendance();
                        JOptionPane.showMessageDialog(this, "Attendance marked: " + m.getAttendance());
                        return;
                    }
                }
            } else if (e.getSource() == upgradeBtn) {
                int id = Integer.parseInt(idField.getText());
                String plan = (String) planCombo.getSelectedItem();
                for (GymMember m : members) {
                    if (m instanceof RegularMember && m.getId() == id) {
                        String result = ((RegularMember)m).upgradePlan(plan);
                        JOptionPane.showMessageDialog(this, result);
                        return;
                    }
                }
            } else if (e.getSource() == payBtn) {
                int id = Integer.parseInt(idField.getText());
                double amount = Double.parseDouble(paidField.getText());
                for (GymMember m : members) {
                    if (m instanceof PremiumMember && m.getId() == id) {
                        String result = ((PremiumMember)m).payDueAmount(amount);
                        JOptionPane.showMessageDialog(this, result);
                        return;
                    }
                }
            } else if (e.getSource() == displayBtn) {
                JTextArea textArea = new JTextArea(20, 50);
                for (GymMember m : members) textArea.append(m.getDetails() + "\n\n");
                JOptionPane.showMessageDialog(this, new JScrollPane(textArea));
            } else if (e.getSource() == clearBtn) {
                for (Component c : getContentPane().getComponents())
                    if (c instanceof JTextField) ((JTextField)c).setText("");
                maleRadio.setSelected(false); femaleRadio.setSelected(false); otherRadio.setSelected(false);
            } else if (e.getSource() == saveBtn) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("members.dat"))) {
                    oos.writeObject(members);
                    JOptionPane.showMessageDialog(this, "Data saved!");
                }
            } else if (e.getSource() == loadBtn) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("members.dat"))) {
                    members = (ArrayList<GymMember>) ois.readObject();
                    JOptionPane.showMessageDialog(this, "Data loaded!");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new GymGUI();
    }
}