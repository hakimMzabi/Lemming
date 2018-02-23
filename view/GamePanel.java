package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import block.Block;
import block.Destructible;
import block.Indestructible;
import controller.GameObserver;
import lemming.Bloqueur;
import lemming.Bombeur;
import lemming.Charpentier;
import lemming.Foreur;
import lemming.Grimpeur;
import lemming.Lemming;
import lemming.Lemming.LemmingType;
import lemming.Parachutiste;
import lemming.Tunnelier;
import model.Change;
import model.Change.ChangeType;
import model.Coordinate;
import model.Game;

public class GamePanel implements GameObserver {
	private final EnumMap<Block.Type, Color> color;
	private final EnumMap<Lemming.LemmingType, Color> color1;

	private int scale;
	JFrame frame = new JFrame("Lemmings");
	private Game game;
	private Coordinate x;
	private List<Block> map = new ArrayList<>();
	private List<Lemming> lemmingList = new ArrayList<>();
	private MouseListener mouseListener;
	private JComponent component = new JComponent() {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (Block c : map) {
				g.setColor(color.get(c.getType()));
				g.fillRect(c.getBlockCoordinate().getX() * scale, c.getBlockCoordinate().getY() * scale, scale, scale);
			}

			for (Lemming l : lemmingList) {
				g.setColor(color1.get(l.getLemmingType()));
				g.fillRect(l.getBody().getX() * scale, l.getBody().getY() * scale, scale, scale);

			}
			this.addMouseListener(mouseListener);
		}

	};

	private boolean finish;

	public GamePanel(Game game, int scale) {

		this.scale = scale;
		this.game = game;
		this.map = game.getBlockList();
		this.lemmingList = game.getLemmingList();

		color1 = new EnumMap<>(Lemming.LemmingType.class);
		color1.put(Lemming.LemmingType.LEMMING, Color.BLUE);
		color1.put(Lemming.LemmingType.BLOQUEUR, new Color(0, 139, 139));
		color1.put(Lemming.LemmingType.BOMBEUR, new Color(153, 0, 0));
		

		color = new EnumMap<>(Block.Type.class);
		color.put(Block.Type.DESTRUCTIBLE, Color.GRAY);
		color.put(Block.Type.INDESTRUCTIBLE, Color.BLACK);
		color.put(Block.Type.LAVE, Color.RED);
		color.put(Block.Type.ENTREE, Color.PINK);
		color.put(Block.Type.SORTIE, Color.MAGENTA);
		color.put(Block.Type.ESPACE, Color.WHITE);
		color.put(Block.Type.TELEPORTEUR, Color.YELLOW);
		color.put(Block.Type.TELEPORTEUR2, Color.GREEN);
		color.put(Block.Type.EXPLOSEUR, Color.ORANGE);
		color.put(Block.Type.GENERATEUR, Color.DARK_GRAY);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		ButtonGroup groupe = new ButtonGroup();

		JRadioButton br1 = new JRadioButton("BLOQUEUR");
		groupe.add(br1);
		panel.add(br1);

		JRadioButton br2 = new JRadioButton("FOREUR");
		groupe.add(br2);
		panel.add(br2);

		JRadioButton br3 = new JRadioButton("GRIMPEUR");
		groupe.add(br3);
		panel.add(br3);

		JRadioButton br4 = new JRadioButton("BOMBEUR");
		groupe.add(br4);
		panel.add(br4);

		JRadioButton br5 = new JRadioButton("PARACHUTISTE");
		groupe.add(br5);
		panel.add(br5);

		JRadioButton br6 = new JRadioButton("CHARPENTIER");
		groupe.add(br6);
		panel.add(br6);

		JRadioButton br7 = new JRadioButton("TUNNELIER");
		groupe.add(br7);
		panel.add(br7);

		component.setLayout(new GridLayout(8, 0));
		component.add(panel);

		this.mouseListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {// l'ecouteur du click du jeu pour activer les foncions
				x = new Coordinate(e.getX() / 30, e.getY() / 30);

				for (Lemming l : game.getLemmingList()) {// faire le test pour chaque bouton afin d'activer la bonne
															// fonction
					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br1.isSelected())) {

						l.changeStateTo(new Bloqueur(l));
					}
					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br3.isSelected())) {

						l.changeStateTo(new Grimpeur(l));
					}
					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br7.isSelected())) {

						l.changeStateTo(new Tunnelier(l));
					}
					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br4.isSelected())) {

						l.changeStateTo(new Bombeur(l));
					}

					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br5.isSelected())) {
						l.changeStateTo(new Parachutiste(l));
					}

					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br2.isSelected())) {

						l.changeStateTo(new Foreur(l));
					}

					if ((x.getX() == l.getBody().getX() && x.getY() == l.getBody().getY()) && (br6.isSelected())) {

						l.changeStateTo(new Charpentier(l));
					}
				}
				;	
		}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

		};

		frame.add(component, BorderLayout.SOUTH);
		frame.setSize(game.getWidth() * scale, game.getHeight() * scale);
		frame.setContentPane(component);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		game.registerObserver(this);

	}

	@Override
	public void update(List<Change> o) {
		// TODO Auto-generated method stub
		component.repaint();
	}

}
