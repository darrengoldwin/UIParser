import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class GUI {

	private JFrame frame;
	private JTextField x;
	private JTextField y;
	private JTextField width;
	private JTextField height;
	private JTextField text;
	
	private JPanel panel;
	private JPanel panelButton;
	private JPanel panelValues;
	
	private ActionListener newButtonActionListener;
	private ActionListener newLabelActionListener;
	private ActionListener newTextFieldActionListener;
	private ActionListener changeButtonActionListener;
	private ActionListener deleteButtonActionListener;
	private ActionListener saveButtonActionListener;
	private MouseListener setFieldActionListener;
	private MouseMotionListener motionListener;
	
	private JButton button;
	private JButton label;
	private JButton textField;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton changeButton;
	
	private Component currentComponent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initAction();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Parser Example");
		frame.setBounds(100, 100, 611, 378);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(23, 46, 381, 264);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(489, 5, 75, 30);
		saveButton.addActionListener(saveButtonActionListener);
		frame.getContentPane().add(saveButton);
		
		panelButton = new JPanel();
		panelButton.setBounds(27, 5, 377, 35);
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
		frame.getContentPane().add(panelButton);
		
		button = new JButton("New Button");
		button.addActionListener(newButtonActionListener);
		panelButton.add(button);
		
		label = new JButton("New Label");
		label.addActionListener(newLabelActionListener);
		panelButton.add(label);
		
		textField = new JButton("New Text");
		textField.addActionListener(newTextFieldActionListener);
		panelButton.add(textField);
		
		panelValues = new JPanel();
		panelValues.setBounds(441, 46, 144, 264);
		frame.getContentPane().add(panelValues);
		panelValues.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel textLbl = new JLabel("Text: ");
		panelValues.add(textLbl);
		
		text = new JTextField();
		panelValues.add(text);
		text.setColumns(10);
		
		JLabel xLbl = new JLabel("X: ");
		panelValues.add(xLbl);
		
		x = new JTextField();
		panelValues.add(x);
		x.setColumns(10);
		
		JLabel yLbl = new JLabel("Y: ");
		panelValues.add(yLbl);
		
		y = new JTextField();
		panelValues.add(y);
		y.setColumns(10);
		
		JLabel widthLbl = new JLabel("Width: ");
		panelValues.add(widthLbl);
		
		width = new JTextField();
		panelValues.add(width);
		width.setColumns(10);
		
		JLabel heightLbl = new JLabel("Height: ");
		panelValues.add(heightLbl);
		
		height = new JTextField();
		panelValues.add(height);
		height.setColumns(10);
		
		changeButton = new JButton("Change");
		changeButton.addActionListener(changeButtonActionListener);
		panelValues.add(changeButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(deleteButtonActionListener);
		panelValues.add(deleteButton);
		
	}
	
	
	public void initAction() {
		
		deleteButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				panel.remove(currentComponent);
				if(panel.getComponentCount() >0)
					setFields((Component)panel.getComponent(0));
				
				panel.repaint();
				panel.revalidate();
			}
		};
		
		changeButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				changeFields(currentComponent);
				panel.repaint();
				panel.revalidate();
			}
		};
		
		saveButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				UI[] components = new UI[panel.getComponentCount()];
				for(int i =0; i< panel.getComponentCount(); i++) {
					String text = null;
					String type = null;
					if(JButton.class.equals(panel.getComponent(i).getClass())) {
						JButton button = (JButton)panel.getComponent(i);
						text = button.getText();
						type = "BUTTON";
					}
					if(JLabel.class.equals(panel.getComponent(i).getClass())) {
						JLabel button = (JLabel)panel.getComponent(i);
						text = button.getText();
						type = "LABEL";
					}
					if(JTextField.class.equals(panel.getComponent(i).getClass())) {
						JTextField button = (JTextField)panel.getComponent(i);
						text = button.getText();
						type = "TEXTFIELD";
					}
					
					int x = panel.getComponent(i).getX();
					int y = panel.getComponent(i).getY();
					int width = panel.getComponent(i).getWidth();
					int height = panel.getComponent(i).getHeight();
					components[i] = new UI(x, y, width, height, text, type);
					
				}
				JSONArray array = new JSONArray(components);
				File f = new File("C:\\Users\\USER\\eclipse-workspace\\ParserExample\\src\\components.json");
				
				//System.out.println(array.toString());
				//File f = new File("C:\\Users\\USER\\eclipse-workspace\\ParserExample\\sample.html");
				
				
				try{
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					bw.write("data = '");
					bw.write(array.toString());
					bw.write("';");
					bw.close();

				} catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
				
				
				
				
			}
		};
		
		newButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JButton button = new JButton("New button");
				button.setFont(new Font("Arial", Font.PLAIN, 13));
				button.setBounds(0, 0, 100, 25);
				button.setVisible(true);
				button.setEnabled(false);
				button.addMouseListener(setFieldActionListener);
				button.addMouseMotionListener(motionListener);
				setFields(button);
				panel.add(button);
				panel.repaint();
				panel.revalidate();
			}
		};
		
		newLabelActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JLabel label = new JLabel("New Label");
				label.setFont(new Font("Arial", Font.PLAIN, 13));
				label.setBounds(0, 0, 100, 25);
				label.setVisible(true);
				label.addMouseListener(setFieldActionListener);
				label.addMouseMotionListener(motionListener);
				setFields(label);
				panel.add(label);
				panel.repaint();
				panel.revalidate();
			}
		};
		
		newTextFieldActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JTextField textField = new JTextField("New TextField");
				textField.setFont(new Font("Arial", Font.PLAIN, 13));
				textField.setBounds(0, 0, 100, 25);
				textField.setVisible(true);
				textField.setEnabled(false);
				textField.addMouseListener(setFieldActionListener);
				textField.addMouseMotionListener(motionListener);
				setFields(textField);
				panel.add(textField);
				panel.repaint();
				panel.revalidate();
			}
		};
		
		
		motionListener = new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {}
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				Component component = (Component)e.getComponent();
				component.setBounds(e.getXOnScreen()-170, e.getYOnScreen()-180, component.getWidth(), component.getHeight());
				setFields(component);
			}
		};
		
		setFieldActionListener = new MouseListener() {
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {
				setFields((Component)e.getComponent());
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setFields((Component)e.getComponent());
				
			}
			
		};
		
	}
	
	public void changeFields(Component component) {
		
		if(JButton.class.equals(component.getClass())) {
			JButton button = (JButton)component;
			button.setText(this.text.getText());
		}
		if(JLabel.class.equals(component.getClass())) {
			JLabel label = (JLabel)component;
			label.setText(this.text.getText());
		}
		if(JTextField.class.equals(component.getClass())) {
			JTextField textField = (JTextField)component;
			textField.setText(""+ this.text.getText());
		}
		
		int x = Integer.parseInt(this.x.getText());
		int y = Integer.parseInt(this.y.getText());
		int height = Integer.parseInt(this.height.getText());
		int width = Integer.parseInt(this.width.getText());
		component.setBounds(x, y, width, height);
		
	}
	
	public void setFields(Component component) {
		currentComponent = component;
		if(JButton.class.equals(component.getClass())) {
			JButton button = (JButton)component;
			this.text.setText(""+ button.getText());
		}
		if(JLabel.class.equals(component.getClass())) {
			JLabel label = (JLabel)component;
			this.text.setText(""+ label.getText());
		}
		if(JTextField.class.equals(component.getClass())) {
			JTextField textField = (JTextField)component;
			this.text.setText(""+ textField.getText());
		}
		
		this.x.setText(""+component.getBounds().x);
		this.y.setText(""+component.getBounds().y);
		this.height.setText(""+component.getBounds().height);
		this.width.setText(""+component.getBounds().width);
	}
	
}
