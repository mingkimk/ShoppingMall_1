package Manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Client.ClientChat;
import Client.MsCenter;
import DB.ManagementDAO;
import DB.ManagementDTO;

public class Setting extends JFrame {

	Scanner in = new Scanner(System.in);
	String header[] = { "상품코드", "상품이름", "수량", "가격" };
	JTabbedPane tabpane = new JTabbedPane();
	DefaultTableModel tablemodel = new DefaultTableModel(header, 0);
	JTable table = new JTable(tablemodel);
	JScrollPane tableScroll = new JScrollPane(table);

	// center panel
	JPanel tab_center = new JPanel();
	JPanel tab_south = new JPanel();
	JPanel south_north = new JPanel();
	JPanel south_south = new JPanel();

	JTextField[] txtfield = new JTextField[4];
	JTextField tfield = null;

	int modIntRow = -1;

	String fileName = "data.txt";

	ManagementDAO dao = ManagementDAO.getInstance();
	ManagementDTO dto = null;
	ArrayList<String[]> initList = null;
	// ClientChat ch = null;
	MsCenter mc = null;
	Socket withServer = null;
	Socket withServer2 = null;
	private static MsCenter ms = MsCenter.getInstance();
	String[] strings = null;
	private Socket withClient2;

	public Setting(Socket withClient2) {
		super("관리자 설정");// super의 생성자 호출
	//	this.withServer = withServer;
		this.withClient2 = withClient2;
		Dimension size = new Dimension(600, 400);
		createpanel();
		createtb();
		tablesetting();

		start();

		this.setLocation(300, 300);
		this.setSize(size);
		this.add(tabpane);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		init(initList, withServer2);
	}

	public void start() {
		String msg = "리스트줘";
		ms.list(msg, withClient2);

	}

	public void init(ArrayList<String[]> list, Socket withClient) {
//		String msg ="리스트줘";
//		mc = new MsCenter(withClient, withClient2);
//		mc.list(msg);
		initList = list;
		// this.withClient2=withClient2;
		for (int i = 0; i < initList.size(); i++) {
			tablemodel.addRow(initList.get(i));
			System.out.println(" this is init");
		}
	}

	public void tablesetting() {
		table.setRowMargin(0);
		table.getColumnModel().setColumnMargin(0);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.setShowVerticalLines(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {// 마우스 왼쪽 클릭
					modIntRow = table.getSelectedRow();
					for (int i = 0; i < txtfield.length; i++) {
						txtfield[i].setText((String) table.getValueAt(modIntRow, i));
					}
					modIntRow = table.getSelectedRow();
					// tfield.setText((String) table.getValueAt(modIntRow, 5));
				}
				if (e.getClickCount() == 2) {// 왼쪽 더블 클릭

				}
				if (e.getClickCount() == 3) {

					modIntRow = table.getSelectedRow();

				}
			}
		});
		DefaultTableCellRenderer ts = new DefaultTableCellRenderer();// 테이블의 셀 값을 정렬
		ts.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tc = table.getColumnModel();
		for (int i = 0; i < tc.getColumnCount(); i++) {
			tc.getColumn(i).setCellRenderer(ts);
		}

	}

	private void createtb() {
		south_north.setLayout(new BoxLayout(south_north, BoxLayout.X_AXIS));
		for (int i = 0; i < txtfield.length; i++) {
			south_north.add(txtfield[i] = new JTextField());
		}
		JButton addB = new JButton("추가");
		south_north.add(addB);

		addB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[5];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				tablemodel.addRow(in);
				in[4] = "add";
				//MsCenter mc = new MsCenter(withServer, withServer2);
				ms.allMsg(in);
				// saveToDB(in);
			}

		});

		JButton modB = new JButton("수정");
		south_north.add(modB);
		modB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[5];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				delTableRow(modIntRow);
				tablemodel.insertRow(modIntRow, in);
				in[4] = "mod";
				//MsCenter mc = new MsCenter(withServer, withServer2);
				ms.allMsg(in);
				// editToDB(in);
				modIntRow = -1;
			}
		});

		JButton delB = new JButton("삭제");
		south_north.add(delB);
		delB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[5];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
				}
				// delToDB(in);
				in[4] = "del";
			//	MsCenter mc = new MsCenter(withServer, withServer2);
				ms.allMsg(in);
				delTableRow(table.getSelectedRow());
			}
		});

		JButton orderallB = new JButton("쇼핑몰가기");
		south_south.add(orderallB);

		orderallB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String in[] = new String[4];
				for (int i = 0; i < txtfield.length; i++) {
					in[i] = txtfield[i].getText();
					txtfield[i].setText("");
					// new orderlist();
				}

			}
		});

	}

	private void delTableRow(int row) {
		tablemodel.removeRow(row);

	}

	private void createpanel() {
		this.add(tab_center, "Center");
		this.add(tab_south, "South");

		tab_center.setLayout(new BorderLayout());
		tab_center.add(tableScroll, "Center");
		tab_center.add(tab_south, "South");
		tabpane.add("basket", tab_center);

		tab_south.setLayout(new BorderLayout());
		tab_south.add(south_north, "North");
		tab_south.add(south_south, "South");

	}

}
