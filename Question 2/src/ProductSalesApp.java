import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// 1️⃣ Interface
interface IProductSales {
    int[][] GetProductSales();
    int GetTotalSales();
    int GetSalesOverLimit();
    int GetSalesUnderLimit();
    int GetProductsProcessed();
    double GetAverageSales();
}

// 2️⃣ ProductSales class
class ProductSales implements IProductSales {

    private int[][] salesData;
    private final int SALES_LIMIT = 500;

    public ProductSales(int[][] salesData) {
        this.salesData = salesData;
    }

    @Override
    public int[][] GetProductSales() {
        return salesData;
    }

    @Override
    public int GetTotalSales() {
        int total = 0;
        for (int[] year : salesData) {
            for (int sale : year) {
                total += sale;
            }
        }
        return total;
    }

    @Override
    public int GetSalesOverLimit() {
        int count = 0;
        for (int[] year : salesData) {
            for (int sale : year) {
                if (sale >= SALES_LIMIT) count++;
            }
        }
        return count;
    }

    @Override
    public int GetSalesUnderLimit() {
        int count = 0;
        for (int[] year : salesData) {
            for (int sale : year) {
                if (sale < SALES_LIMIT) count++;
            }
        }
        return count;
    }

    @Override
    public int GetProductsProcessed() {
        return salesData.length;
    }

    @Override
    public double GetAverageSales() {
        int total = GetTotalSales();
        int count = 0;
        for (int[] year : salesData) count += year.length;
        return (double) total / count;
    }
}

// 3️⃣ GUI class
public class ProductSalesApp extends JFrame {

    private JTextArea textArea;
    private JLabel yearsLabel;
    private JButton loadButton, saveButton;
    private ProductSales productSales;
    private String[] products = {"Microphone", "Speaker", "Mixing Desk"};

    public ProductSalesApp() {
        setTitle("Sound Retailer Product Sales");
        setSize(550, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sample sales data
        int[][] salesData = {
            {300, 150, 700},  // Year 1
            {250, 200, 600}   // Year 2
        };
        productSales = new ProductSales(salesData);

        // Text area
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Panel for buttons and label
        JPanel panel = new JPanel(new FlowLayout());
        loadButton = new JButton("Load Product Data");
        saveButton = new JButton("Save Product Data");
        yearsLabel = new JLabel("Number of years processed: 0");
        yearsLabel.setEnabled(false);

        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(yearsLabel);
        add(panel, BorderLayout.SOUTH);

        // Menu system
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem loadItem = new JMenuItem("Load Product Data");
        JMenuItem saveItem = new JMenuItem("Save Product Data");
        JMenuItem clearItem = new JMenuItem("Clear");

        fileMenu.add(exitItem);
        toolsMenu.add(loadItem);
        toolsMenu.add(saveItem);
        toolsMenu.add(clearItem);
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);

        // Button actions
        loadButton.addActionListener(e -> loadProductData());
        saveButton.addActionListener(e -> saveProductData());

        // Menu actions
        loadItem.addActionListener(e -> loadProductData());
        saveItem.addActionListener(e -> saveProductData());
        clearItem.addActionListener(e -> clearData());
        exitItem.addActionListener(e -> System.exit(0));
    }

    private void loadProductData() {
        StringBuilder sb = new StringBuilder();
        int[][] salesData = productSales.GetProductSales();

        sb.append("DATA LOG\n");
        sb.append("====================\n");

        for (int year = 0; year < salesData.length; year++) {
            sb.append("Year ").append(year + 1).append(":\n");
            for (int i = 0; i < salesData[year].length; i++) {
                int sale = salesData[year][i];
                sb.append("  ").append(products[i]).append(": ").append(sale);
                if (sale >= 500) {
                    sb.append(" (Over Limit)\n");
                } else {
                    sb.append(" (Under Limit)\n");
                }
            }
            sb.append("\n");
        }

        sb.append("====================\n");
        sb.append("Total Sales: ").append(productSales.GetTotalSales()).append("\n");
        sb.append("Average Sales: ").append(String.format("%.0f", productSales.GetAverageSales())).append("\n");
        sb.append("Sales over limit: ").append(productSales.GetSalesOverLimit()).append("\n");
        sb.append("Sales under limit: ").append(productSales.GetSalesUnderLimit()).append("\n");

        textArea.setText(sb.toString());
        yearsLabel.setText("Number of years processed: " + productSales.GetProductsProcessed());
    }

    private void saveProductData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            writer.write(textArea.getText());
            JOptionPane.showMessageDialog(this, "Data saved to data.txt successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }

    private void clearData() {
        textArea.setText("");
        yearsLabel.setText("Number of years processed: 0");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductSalesApp gui = new ProductSalesApp();
            gui.setVisible(true);
        });
    }
}
