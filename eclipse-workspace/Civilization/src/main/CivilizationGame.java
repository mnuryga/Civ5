package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import map.Tile;

public class CivilizationGame {
	//JGRAPHICS CONSTRUCTORS
	private JFrame frame = new JFrame("Civilization");
	private JPanel leftPanel = new JPanel(); //200 from right
	private JPanel topPanel = new JPanel(); //50 from top
	private JPanel mapPanel = new JPanel();
	private JScrollPane mapPane;
	private JButton[][] $mapButtons = new JButton[Tile.getMAP_SIZE()][Tile.getMAP_SIZE()];
	
	//BUTTON LISTENERS
	private TileListener tileListener = new TileListener();
	
	//Import tile graphics
	/*static ImageIcon forestTileII = new ImageIcon(Tile.class.getResource("map/resources/forestTile.png"));	
	static ImageIcon grassTileII = new ImageIcon(Tile.class.getResource("map/resources/grassTile.png"));
	static ImageIcon mountainTileII = new ImageIcon(Tile.class.getResource("src/map/resources/mountainTile.png"));
	static ImageIcon sandTileII = new ImageIcon(Tile.class.getResource("src/map/resources/sandTile.png"));
	static ImageIcon waterTileII = new ImageIcon(Tile.class.getResource("src/map/resources/waterTile.png"));*/
	
	public CivilizationGame(){
		//ADD STUFF
		
	}
	
	public void display(){
		//JFrame
		frame.setPreferredSize(new Dimension(1920, 1015));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setVisible(true);
		//frame.setResizable(false);
		
		//mapPane
		mapPanel.setPreferredSize(new Dimension(2500,2500));
		mapPanel.setLayout(null);
		mapPane = new JScrollPane(mapPanel);
		mapPane.setBounds(200, 50, 1705, 927);
		mapPane.setAutoscrolls(true);
		mapPane.getVerticalScrollBar().setUnitIncrement(16);
		mapPane.getHorizontalScrollBar().setUnitIncrement(16);
		//mapPane.setPreferredSize(new Dimension(2000, 2000));
		//mapPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//mapPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//mapPane.setBackground(Color.red);
		frame.add(mapPane);

		for (int i = 0; i < $mapButtons.length; i++) {
			for (int j = 0; j < $mapButtons[i].length; j++) {
				$mapButtons[i][j] = new JButton(Tile.get$map()[i][j].getTileImageIcon());
				$mapButtons[i][j].addActionListener(tileListener);
				$mapButtons[i][j].setBounds(j*50, i*50, 50, 50);
				/*$mapButtons[i][j].setBorder(null);
				$mapButtons[i][j].setBorderPainted(false);*/
				mapPanel.add($mapButtons[i][j]);
			}
		}
		
		frame.pack();
	}
	
	public class TileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//if (e.getSource() == ) { 
		}
	}
}
