import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {

    // Components for the text editor
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JScrollPane scrollPane;

    // Constructor to set up the GUI
    public TextEditor() {
        // Set up the frame
        setTitle("Basic Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize the text area and add a scroll pane
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        // Initialize the menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create and add "Open" menu item
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(this);
        fileMenu.add(openItem);

        // Create and add "Save" menu item
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        fileMenu.add(saveItem);

        // Create and add "Exit" menu item
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);
        fileMenu.add(exitItem);

        // Add the file menu to the menu bar
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Initialize the file chooser
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        // Make the text editor visible
        setVisible(true);
    }

    // ActionListener for menu items
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }

    // Method to open a text file
    private void openFile() {
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File could not be opened", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to save the text to a file
    private void saveFile() {
        int returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File could not be saved", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextEditor::new);
    }
}
