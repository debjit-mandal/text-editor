import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame implements ActionListener {
  private JTextArea textArea;
  private JFileChooser fileChooser;
  private String currentFile;

  public Main() {
    setTitle("Text Editor");
    setSize(800, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    initComponents();
    setVisible(true);
  }

  private void initComponents() {
    textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane, BorderLayout.CENTER);

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem newMenuItem = new JMenuItem("New");
    JMenuItem openMenuItem = new JMenuItem("Open");
    JMenuItem saveMenuItem = new JMenuItem("Save");
    JMenuItem saveAsMenuItem = new JMenuItem("Save As");
    JMenuItem exitMenuItem = new JMenuItem("Exit");

    newMenuItem.addActionListener(this);
    openMenuItem.addActionListener(this);
    saveMenuItem.addActionListener(this);
    saveAsMenuItem.addActionListener(this);
    exitMenuItem.addActionListener(this);

    fileMenu.add(newMenuItem);
    fileMenu.add(openMenuItem);
    fileMenu.add(saveMenuItem);
    fileMenu.add(saveAsMenuItem);
    fileMenu.addSeparator();
    fileMenu.add(exitMenuItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar);

    fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
  }

  private void newFile() {
    textArea.setText("");
    currentFile = null;
  }

  private void openFile() {
    int returnVal = fileChooser.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          sb.append(line);
          sb.append("\n");
        }
        reader.close();
        textArea.setText(sb.toString());
        currentFile = file.getAbsolutePath();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void saveFile() {
    if (currentFile != null) {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
        writer.write(textArea.getText());
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      saveFileAs();
    }
  }

  private void saveFileAs() {
    int returnVal = fileChooser.showSaveDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(textArea.getText());
        writer.close();
        currentFile = file.getAbsolutePath();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void exit() {
    System.exit(0);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    switch (command) {
      case "New":
        newFile();
        break;
      case "Open":
        openFile();
        break;
      case "Save":
        saveFile();
        break;
      case "Save As":
        saveFileAs();
        break;
      case "Exit":
        exit();
        break;
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Main());
  }
}