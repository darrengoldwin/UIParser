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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.json.JSONArray;

import com.google.gson.Gson;


public class GUI {

	private JFrame frame;
	private JFrame buttonFrame;
	private JFrame valuesFrame;
	private JTextField x;
	private JTextField y;
	private JTextField width;
	private JTextField height;
	private JTextField text;
	
	private JPanel panel;
	private JPanel panelValues;
	
	private ActionListener newButtonActionListener;
	private ActionListener newLabelActionListener;
	private ActionListener newTextFieldActionListener;
	private ActionListener changeButtonActionListener;
	private ActionListener deleteButtonActionListener;
	private ActionListener saveButtonActionListener;
	private ActionListener forwardButtonActionListener;
	private ActionListener backwardButtonActionListener;
	private ActionListener loadButtonActionListener;
	private MouseListener setFieldActionListener;
	private MouseMotionListener motionListener;
	
	private JButton button;
	private JButton label;
	private JButton textField;
	private JButton saveButton;
	private JButton loadButton;
	private JButton deleteButton;
	private JButton changeButton;
	private JButton forwardButton;
	private JButton backwardButton;
	
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
		frame.setTitle("Panel");
		frame.setBounds(0, 0, 1366, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 110, 1366, 658);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		panelValues = new JPanel();
		panelValues.setBounds(0, 0, 1350, 112);
		frame.getContentPane().add(panelValues);
		panelValues.setLayout(null);
		
		changeButton = new JButton("Change");
		changeButton.setBounds(804, 10, 100, 25);
		panelValues.add(changeButton);
		
		forwardButton = new JButton("Forward");
		forwardButton.setBounds(804, 35, 100, 25);
		panelValues.add(forwardButton);
		
		backwardButton = new JButton("Backward");
		backwardButton.setBounds(804, 60, 100, 25);
		panelValues.add(backwardButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(804, 85, 100, 25);
		panelValues.add(deleteButton);
		
		JLabel textLbl = new JLabel("Text: ");
		textLbl.setBounds(604, 10, 62, 20);
		panelValues.add(textLbl);
		
		text = new JTextField();
		text.setBounds(666, 10, 126, 20);
		panelValues.add(text);
		text.setColumns(10);
		
		JLabel xLbl = new JLabel("X: ");
		xLbl.setBounds(604, 30, 62, 20);
		panelValues.add(xLbl);
		
		x = new JTextField();
		x.setBounds(666, 30, 126, 20);
		panelValues.add(x);
		x.setColumns(10);
		
		JLabel yLbl = new JLabel("Y: ");
		yLbl.setBounds(604, 50, 62, 20);
		panelValues.add(yLbl);
		
		y = new JTextField();
		y.setBounds(666, 50, 126, 20);
		panelValues.add(y);
		y.setColumns(10);
		
		JLabel widthLbl = new JLabel("Width: ");
		widthLbl.setBounds(604, 70, 62, 20);
		panelValues.add(widthLbl);
		
		width = new JTextField();
		width.setBounds(666, 70, 126, 20);
		panelValues.add(width);
		width.setColumns(10);
		
		JLabel heightLbl = new JLabel("Height: ");
		heightLbl.setBounds(604, 90, 62, 20);
		panelValues.add(heightLbl);
		
		height = new JTextField();
		height.setBounds(666, 90, 126, 20);
		panelValues.add(height);
		height.setColumns(10);
		
		button = new JButton("New Button");
		button.setBounds(10, 36, 126, 23);
		panelValues.add(button);
		
		textField = new JButton("New Text");
		textField.setBounds(10, 61, 126, 23);
		panelValues.add(textField);
		
		label = new JButton("New Label");
		label.setBounds(10, 11, 126, 23);
		panelValues.add(label);
		
		loadButton = new JButton("Load");
		loadButton.setBounds(174, 36, 132, 23);
		panelValues.add(loadButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(174, 11, 132, 23);
		panelValues.add(saveButton);
		saveButton.addActionListener(saveButtonActionListener);
		loadButton.addActionListener(loadButtonActionListener);
		label.addActionListener(newLabelActionListener);
		textField.addActionListener(newTextFieldActionListener);
		button.addActionListener(newButtonActionListener);
		deleteButton.addActionListener(deleteButtonActionListener);
		backwardButton.addActionListener(backwardButtonActionListener);
		forwardButton.addActionListener(forwardButtonActionListener);
		changeButton.addActionListener(changeButtonActionListener);
		
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
				checkBounds();
				panel.repaint();
				panel.revalidate();
			}
		};
		
		backwardButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Component[] components = panel.getComponents();
				panel.removeAll();
				for(int i =0; i< components.length; i++) {
					System.out.println(components[i].getClass());
					if(components[i] == currentComponent) {
						System.out.println("a");
						if(i+1 < components.length)
							panel.add(components[i+1]);
						panel.add(components[i]);
						i++;
					}
					else
						panel.add(components[i]);
				}
					
				panel.repaint();
				panel.revalidate();
				
			}
		};
		
		forwardButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Component[] components = panel.getComponents();
				panel.removeAll();
				for(int i =0; i< components.length; i++) {
					if(components[i] == currentComponent) {
						panel.add(components[i],i-1);
					}
					else
						panel.add(components[i]);
				}
					
						
				panel.repaint();
				panel.revalidate();
			}
		};
		
		loadButtonActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				JFileChooser fileChooser = new JFileChooser("C:\\Users\\USER\\eclipse-workspace\\ParserExample\\src\\components.json");
				fileChooser.showOpenDialog(frame);
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
					String s = br.readLine();
					s = s.substring(8, s.length()-2);
					System.out.println(s);
					
					Gson g = new Gson();
					UI[] components = g.fromJson(s, UI[].class);
					
					for(int i =0; i< components.length; i++) {
						
						if(components[i].getType().equals("BUTTON")) {
							JButton button = new JButton(components[i].getText());
							button.setFont(new Font("Arial", Font.PLAIN, 13));
							button.setBounds(components[i].getX(), components[i].getY(), components[i].getWidth(), components[i].getHeight());
							button.setVisible(true);
							button.setEnabled(false);
							button.addMouseListener(setFieldActionListener);
							button.addMouseMotionListener(motionListener);
							setFields(button);
							panel.add(button);
						}
						
						if(components[i].getType().equals("LABEL")) {
							JLabel label = new JLabel(components[i].getText());
							label.setFont(new Font("Arial", Font.PLAIN, 13));
							label.setBounds(components[i].getX(), components[i].getY(), components[i].getWidth(), components[i].getHeight());
							label.setVisible(true);
							label.addMouseListener(setFieldActionListener);
							label.addMouseMotionListener(motionListener);
							setFields(label);
							panel.add(label);
						}
						
						if(components[i].getType().equals("TEXTFIELD")) {
							JTextField textField = new JTextField(components[i].getText());
							textField.setFont(new Font("Arial", Font.PLAIN, 13));
							textField.setBounds(components[i].getX(), components[i].getY(), components[i].getWidth(), components[i].getHeight());
							textField.setVisible(true);
							textField.setEnabled(false);
							textField.addMouseListener(setFieldActionListener);
							textField.addMouseMotionListener(motionListener);
							Border border = BorderFactory.createLineBorder(new Color(212,209,208), 1);
							textField.setBorder(border);
							textField.setBackground(new Color(235,234,230));
							textField.setForeground(Color.black);
							setFields(textField);
							panel.add(textField);
							
						}
						
					}
					panel.repaint();
					panel.revalidate();	
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					
				}
				
			}
		};
		
		saveButtonActionListener = new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.repaint();
				panel.revalidate();
				
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
				//File f = new File("C:\\Users\\Torrigan\\workspace\\UIParser\\src\\components.json");
				File f = new File("C:\\Users\\USER\\eclipse-workspace\\ParserExample\\src\\components.json");
				
				
				
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
				panel.add(button, 0);
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
				panel.add(label, 0);
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
				Border border = BorderFactory.createLineBorder(new Color(212,209,208), 1);
				textField.setBorder(border);
				textField.setBackground(new Color(235,234,230));
				textField.setForeground(Color.black);
				setFields(textField);
				panel.add(textField, 0);
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
				component.setBounds(e.getXOnScreen()-50, e.getYOnScreen()-30, component.getWidth(), component.getHeight());
				setFields(component);
			}
		};
		
		setFieldActionListener = new MouseListener() {
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {
				checkBounds();
				setFields((Component)e.getComponent());
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
		};
		
	}
	
	public void checkBounds() {
		int x = currentComponent.getX();
		int y = currentComponent.getY();
		if(x + currentComponent.getWidth() > panel.getWidth()) 
			x = panel.getWidth()-currentComponent.getWidth();
		else if(x < 0)
			x = 0;
		if(y + currentComponent.getHeight() > panel.getHeight())
			y =panel.getHeight()- currentComponent.getHeight();
		else if(y < 0){
			y =0;
		}
		currentComponent.setBounds(x, y, currentComponent.getWidth(), currentComponent.getHeight());
		setFields(currentComponent);
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
