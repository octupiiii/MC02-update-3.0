package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.*;
import View.*;

/**
 * NewMainFrame class represents the main application frame for the "ICE ICE
 * BaBY" vending machine system.
 * It extends JFrame and provides a card layout for switching between different
 * views: Main Menu, Vending Features,
 * and Maintenance Features.
 */
public class NewMainFrame extends JFrame {

    // Card layout for switching view
    private CardLayout cardLayout;
    private MainMenu menu;
    private VendingMachineView vendingVMView;
    private VendingMachine vendingMachine;
    private MaintenanceView maintenanceVM;
    private boolean vmStatus = false;
    private double totalAmount = 0.0;

    /**
     * Constructs a new instance of the NewMainFrame class.
     * Initializes the main application frame with necessary components, sets up the
     * card layout, and adds the
     * MainMenu, VendingMachineView, and MaintenanceView to the card layout.
     * Also sets up event listeners for buttons in the MainMenu, VendingMachineView,
     * and MaintenanceView.
     * Sets the size of the frame and makes it visible.
     */
    public NewMainFrame() {
        super("ICE ICE BaBY");
        cardLayout = new CardLayout();
        menu = new MainMenu();
        vendingMachine = new VendingMachine();
        vendingVMView = new VendingMachineView(vendingMachine);
        maintenanceVM = new MaintenanceView(vendingMachine);

        // sets our layout as a card layout
        setLayout(cardLayout);

        add(menu, "Main Menu");
        add(vendingVMView, "Vending Features");
        add(maintenanceVM, "Maintenance Feature");

        menu.createVM(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();

            if (buttonLabel.equals("Create Vending Machine")) {
                String[] options = { "Regular", "Special" };
                int choice = JOptionPane.showOptionDialog(menu,
                        "Choose an option for the Vending Machine:",
                        "Vending Machine Options", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null,
                        options,
                        options[0]);

                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(menu, "You Successfully Created a Regular Vending Machine!");
                    vendingMachine = new VendingMachine();
                    vendingMachine.initializeMoney();
                    vendingMachine.initializeSlots();
                    vendingVMView.setVendingMachine(vendingMachine);
                    vmStatus = true;
                } else if (choice == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(menu, "You Successfully Created a Special Vending Machine!");
                    vendingMachine = new SpecialVMModel();
                    System.out.println("nahna");
                    vendingMachine.initializeMoney();
                    vendingMachine.initializeSlots();
                    maintenanceVM.setVendingMachine(vendingMachine);
                    vmStatus = true;
                }
            }
        });

        menu.testVM(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Test Vending Machine")) {
                if (vmStatus) {
                    String[] options = { "Vending Features", "Maintenance Features" };
                    int choice = JOptionPane.showOptionDialog(menu,
                            "Choose an option for the Vending Machine",
                            "Vending Machine Options", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,
                            options,
                            options[0]);

                    if (choice == JOptionPane.YES_OPTION) {
                        vendingVMView.setVendingMachine(vendingMachine);
                        menu.setVisible(false);
                        vendingVMView.setVisible(true);
                        this.vendingMachine = vendingVMView.getVendingMachine();
                    } else if (choice == JOptionPane.NO_OPTION) {
                        maintenanceVM.setVendingMachine(vendingMachine);
                        menu.setVisible(false);
                        maintenanceVM.setVisible(true);
                        this.vendingMachine = maintenanceVM.getVendingMachine();
                    }
                } else {
                    // Show an error message if the vending machine is not created yet
                    JOptionPane.showMessageDialog(menu, "Please create a vending machine first!");
                }
            }
        });

        vendingVMView.backButton(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Cancel")) {
                vendingVMView.setVisible(false);
                menu.setVisible(true);
            }
        });

        maintenanceVM.backButton(e -> {
            JButton button = (JButton) e.getSource();
            String buttonLabel = button.getText();
            if (buttonLabel.equals("Cancel")) {
                maintenanceVM.setVisible(false);
                menu.setVisible(true);
            }
        });

        int FRAME_WIDTH = 1200;
        int FRAME_HEIGHT = 700;
        // size of our application frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}