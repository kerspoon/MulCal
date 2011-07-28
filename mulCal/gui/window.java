package mulCal.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class window {

	private JFrame frmMulCal;
	private JTable table;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window window = new window();
					window.frmMulCal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        
        frmMulCal = new JFrame();
        frmMulCal.setTitle("MulCal");
        frmMulCal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        frmMulCal.getContentPane().add(splitPane, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        splitPane.setLeftComponent(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        String[] columnNames = {"ID",
                "Equation",
                "Result",
                "Comment"};
        
        Object[][] data = {
        	    {"A", "1+1", new Integer(2), ""},
        	    {"B", "1+2", new Integer(3), ""},
        	    {"C", "1+3", new Integer(4), "comment"},
        	    {"D", "1+4", new Integer(5), ""},
        	    {"E", "1+5", new Integer(6), ""},
        	    {"F", "1+6", new Integer(7), ""},
        	};
        
        table = new JTable(data, columnNames);
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{"A", "1+1", new Integer(2), ""},
        		{"B", "1+2", new Integer(3), ""},
        		{"C", "1+3", new Integer(4), "comment"},
        		{"D", "1+4", new Integer(5), ""},
        		{"E", "1+5", new Integer(6), ""},
        		{"F", "1+6", new Integer(7), ""},
        	},
        	new String[] {
        		"ID", "Equation", "Result", "Comment"
        	}
        ));
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(350);
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        panel.add(scrollPane);
        
        
        JPanel panelBottom = new JPanel();
        splitPane.setRightComponent(panelBottom);
        panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        textField = new JTextField();
        panelBottom.add(textField);
        textField.setColumns(40);
        
        JButton btnNewButton = new JButton("Calcluate");
        panelBottom.add(btnNewButton);
        
        frmMulCal.pack();
	}
}
