package mulCal.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import mulCal.main.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class window {

	private JFrame frmMulCal;
	private Main main;
	
	private JTextField txtCalculate;
	private JButton btnCalculate;
	private HistoryTableModel tblmdlHistory;
    
	/**
	 * Create the application.
	 */
	public window() {
		main = new Main();
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frmMulCal = new JFrame("MulCal");
		frmMulCal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel newContentPane = new JPanel();
		{
			newContentPane.setLayout(new BorderLayout());
			newContentPane.setOpaque(true); //content panes must be opaque
			
			JPanel topPanel = new JPanel();
			{
				topPanel.setLayout(new GridLayout(1,1));

				JScrollPane scrollPane;
		        {
		        	tblmdlHistory = new HistoryTableModel(main.history);
			        JTable tblHistory = new JTable(tblmdlHistory);
			        {
				        tblHistory.getColumnModel().getColumn(0).setPreferredWidth(50);
				        tblHistory.getColumnModel().getColumn(1).setPreferredWidth(250);
				        tblHistory.getColumnModel().getColumn(2).setPreferredWidth(100);
				        tblHistory.getColumnModel().getColumn(3).setPreferredWidth(350);
				        tblHistory.setCellSelectionEnabled(true);
				        tblHistory.setFillsViewportHeight(true);
			        }
			        scrollPane = new JScrollPane(tblHistory);
		        }
		        topPanel.add(scrollPane);
			}
			
			JPanel bottomPanel = new JPanel();
			{
				bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		        
				txtCalculate = new JTextField();
				{
					txtCalculate.setColumns(40);
				}
				
		        btnCalculate = new JButton("Calcluate");
		        {
			        btnCalculate.addMouseListener(new MouseAdapter() {
			        	@Override
			        	public void mouseClicked(MouseEvent e) {
			        		String equationString = txtCalculate.getText(); 
							try {
								main.equation(equationString);
								// TODO update table
								tblmdlHistory.newRow();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								throw new RuntimeException("bad equation");
							}
			        	}
			        });
		        }
		        
				bottomPanel.add(txtCalculate);
		        bottomPanel.add(btnCalculate);
			}
			
			newContentPane.add(topPanel, BorderLayout.CENTER);
			newContentPane.add(bottomPanel, BorderLayout.SOUTH);
		}
        frmMulCal.setContentPane(newContentPane);
        frmMulCal.pack();
        frmMulCal.setVisible(true);
	}
	
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
}
