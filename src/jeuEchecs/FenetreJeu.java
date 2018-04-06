package jeuEchecs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import minMax.IA;

/**
 * classe Fenetre jeu servant � representer la GUI du jeu d'echec, contient la
 * planche de jeu, les boutons debuter et reset, et le champ texte informant le
 * joueur sur le tour. Contient �galement deux jPanel contenant les pieces
 * mang�es.
 *
 */
public class FenetreJeu extends JFrame {
	/**
	 * Echiquier du jeu, contient le tableau de case.
	 */
	private Echiquier e; // echiquier
	private JLabel[][] tab; // tableau de JLabels

	private JPanel panelControle = new JPanel(); // panel du haut
	private JPanel panelGrille = new JPanel(); // panel du bas ( grille )
	GridLayout gridLayout1 = new GridLayout();

	private JButton boutonDebuter = new JButton();
	private JTextField champTexte = new JTextField();
	private JButton boutonReset = new JButton();
	private JPanel panelblanc = new JPanel();
	private JPanel panelnoir = new JPanel();

	private static IA ia;

	/**
	 * Constructeur, appelle m�thode JBInit
	 */
	public FenetreJeu() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * initialise la surface de jeu. Cr�� tout les �lements et initialise leur
	 * position leur couleur.. etc
	 */
	private void jbInit() throws Exception {

		tab = new JLabel[8][8]; // cr�ation du tableau de JLabel
		e = new Echiquier(); // cr�ation de l'�chiquier

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(784, 585));
		this.setTitle("Jeu d'Echecs");
		panelControle.setBounds(new Rectangle(5, 10, 550, 45));
		panelControle.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		panelControle.setLayout(null);
		panelGrille.setBounds(new Rectangle(5, 65, 550, 465));
		panelGrille.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		panelGrille.setLayout(gridLayout1);
		gridLayout1.setColumns(8);
		gridLayout1.setRows(8);
		this.getContentPane().add(panelnoir, null);
		this.getContentPane().add(panelblanc, null);
		this.getContentPane().add(panelGrille, null);
		panelControle.add(boutonReset, null);
		panelControle.add(champTexte, null);
		panelControle.add(boutonDebuter, null);
		this.getContentPane().add(panelControle, null);
		boutonDebuter.setBounds(new Rectangle(15, 10, 130, 25));
		boutonDebuter.setText("DEBUTER");
		champTexte.setBounds(new Rectangle(160, 10, 215, 25));

		// les �couteurs
		boutonReset.setText("RESET");
		boutonReset.setBounds(new Rectangle(390, 10, 130, 25));
		GestionnaireEvenement gest = new GestionnaireEvenement();
		boutonDebuter.addMouseListener(gest);
		boutonReset.addMouseListener(gest);

		// cr�ation des labels
		panelblanc.setBounds(new Rectangle(570, 65, 75, 480));
		panelblanc.setBackground(new Color(255, 255, 255));
		panelblanc.setLayout(new FlowLayout());
		panelnoir.setBounds(new Rectangle(655, 65, 75, 475));
		panelnoir.setBackground(new Color(100, 100, 100));
		panelnoir.setLayout(new FlowLayout());

		// J'attribue la couleur aux JLabels
		int a = 1;
		for (int ligne = 0; ligne < 8; ligne++) {
			a = a == 1 ? 0 : 1;
			for (int colonne = 0; colonne < 8; colonne++) {
				tab[colonne][ligne] = new JLabel(); // cr��ation du JLabel
				tab[colonne][ligne].setOpaque(true);
				panelGrille.add(tab[colonne][ligne]); // ajouter au Panel
				tab[colonne][ligne].setOpaque(true);
				tab[colonne][ligne].setHorizontalAlignment(SwingConstants.CENTER); // pour
				tab[colonne][ligne].addMouseListener(gest); // ajouter l'��couteur aux
				if ((colonne + 1) % 2 == a)
					tab[colonne][ligne].setBackground(new Color(255, 255, 255));
				else
					tab[colonne][ligne].setBackground(new Color(100, 100, 100));

			}
		}

	}

	/**
	 * classe privee pour la gestion des evenement de la souris.
	 *
	 * @author Francois
	 *
	 */
	private class GestionnaireEvenement extends MouseAdapter {
		/**
		 * methode s'excutant si l'on clique sur la surface de jeu. La methode determine
		 * ensuite ou est-ce que l'on cliquer et fait les action en consequence
		 *
		 */
		@Override
		public void mouseClicked(MouseEvent eve) {
			// si on clique sur le bouton d�buter
			if (eve.getSource() == boutonDebuter) {
				// initialise le champ texte, apelle la m�thode d�buter, et initialise toute les
				// variables
				champTexte.setText("C'est le tour aux blanc");
				boutonDebuter.setEnabled(false);
				e.debuter(); // code
				String dossierIcone = "Icone/";
				char[] ordrePiece = { 'T', 'C', 'F', 'D', 'R', 'F', 'C', 'T' };
				int increment = 1;
				int ligne = 0;
				char couleur = 'N';
				Piece tempo = null;
				e.debuter(); // code

				// Je place les ic�nes des pi�ces sur leur cases respectives
				while (increment >= -1) {
					for (int ctr = 0; ctr <= 7; ctr++) {
						tab[ctr][ligne].setIcon(new ImageIcon(dossierIcone + ordrePiece[ctr] + couleur + ".gif"));
						switch (ordrePiece[ctr]) {
						case 'T':
							tempo = new Tour(ligne < 5 ? "noir" : "blanc");
							break;

						case 'C':
							tempo = new Cavalier(ligne < 5 ? "noir" : "blanc");
							break;

						case 'F':
							tempo = new Fou(ligne < 5 ? "noir" : "blanc");
							break;

						case 'D':
							tempo = new Reine(ligne < 5 ? "noir" : "blanc");
							break;

						case 'R':
							tempo = new Roi(ligne < 5 ? "noir" : "blanc");
							break;
						}
						e.getCase(ctr, ligne).setPiece(tempo);
						tab[ctr][ligne + increment].setIcon(new ImageIcon(dossierIcone + 'P' + couleur + ".gif"));
						e.getCase(ctr, ligne + increment).setPiece(new Pion(ligne < 5 ? "noir" : "blanc"));

					}
					couleur = 'B';
					increment -= 2;
					ligne = 7;
				}

			}
			// si on clique sur le bouton reset
			else if (eve.getSource() == boutonReset) {
				// j'appelle la �thode RAZ
				RAZ();

			}

			else if (eve.getSource() instanceof JLabel) // donc on a cliqu� sur un Label
			{
				for (int i = 0; i < 8; i++)
					// je d�termine sur quelle Jlabel on a cliqu�
					for (int j = 0; j < 8; j++)
						if (eve.getSource() == tab[j][i]) {
							ligneClic = i;
							colonneClic = j;
						}
				// si on a cliqu� sur une case non vide et que le tampon n'est pas null
				if (couleurControle == "blanc" && pieceTampon == null)
					jouerUnCoup(e, colonneClic, ligneClic, couleurControle);
				else if (couleurControle == "blanc" && pieceTampon != null) {
					if (jouerUnCoup(e, colonneClic, ligneClic, couleurControle)) {
						couleurControle = "noir";
						champTexte.setText("C'est le tour aux " + couleurControle);
						ia.jouer(e, "noir");
						couleurControle = "blanc";
						champTexte.setText("C'est le tour aux " + couleurControle);
					}
				}

			}

		}
	}

	private static Piece pieceTampon = null;
	private static ImageIcon iconeTampon;
	private static int ligneClic;
	private static int colonneClic;
	private static Position depart, arrivee;
	private static String couleurControle = "blanc";
	private static Position temp = null;

	public static Piece getPieceTampon() {
		return pieceTampon;
	}

	public boolean jouerUnCoup(Echiquier e, int colonneClic, int ligneClic, String couleurControle) {
		if ((e.getCase(colonneClic, ligneClic).getPiece() != null | pieceTampon != null)) {
			// si le tampon est null
			if (pieceTampon == null) {
				// si c'est au tour de la couleur de controle � jouer
				if (e.getCase(colonneClic, ligneClic).getPiece().getCouleur().equals(couleurControle)) {
					// J'initialise la piece tampon a la piece sur laquelle on a cliqu�
					pieceTampon = e.getCase(colonneClic, ligneClic).getPiece();
					iconeTampon = (ImageIcon) tab[colonneClic][ligneClic].getIcon();
					temp = new Position(colonneClic, ligneClic);
					tab[colonneClic][ligneClic].setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 5));
				}

			} else {
				// je cr�� un d�placement
				Deplacement deplacement = new Deplacement(temp, new Position(colonneClic, ligneClic));
				// je v�rifie si le d�placement est valide, si le chemin est possible et si il
				// est possible, pour un pion de manger la piece
				if ((pieceTampon.estValide(deplacement) && e.cheminPossible(deplacement))
						| e.captureParUnPionPossible(deplacement)) {
					// je cr�� un jLabel avec l'ic�ne de la pi�ce manger
					JLabel manger = new JLabel(tab[colonneClic][ligneClic].getIcon());
					manger.setHorizontalAlignment(SwingConstants.CENTER);

					// je l'ajoute au bon jPanel
					if (couleurControle.equals("blanc"))
						panelblanc.add(manger);
					else
						panelnoir.add(manger);

					/*
					 * je v�rifie si la pi�ce manger est un roi, si oui le jeu est termin� et
					 * L'utilisateurs peut choisir si il veut continuer a jouer ou non
					 */
					if (e.getCase(colonneClic, ligneClic).getPiece() instanceof Roi) {
						if (JOptionPane.showConfirmDialog(null,
								"F�licitation vous avez gagn� ! D�sirez-vous jouer de nouveau ?\n", "Mine !",
								JOptionPane.YES_NO_OPTION) == 0) {
							RAZ();
							tab[temp.getColonne()][temp.getLigne()]
									.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));
						}

						else
							System.exit(0);

					} else// si on d�pose la piece sur une case vide
					{
						// on met le tampon sur la case vide et on vide le tampon apr�s
						e.getCase(temp.getColonne(), temp.getLigne()).setPiece(null);
						tab[temp.getColonne()][temp.getLigne()]
								.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));

						tab[colonneClic][ligneClic].setIcon(iconeTampon);
						e.getCase(colonneClic, ligneClic).setPiece(pieceTampon);
						tab[temp.getColonne()][temp.getLigne()].setIcon(null);

						pieceTampon = null;
						iconeTampon = null;
						temp = null;

						// couleurControle = couleurControle.equals("blanc") ? "noir" : "blanc";
						// if (couleurControle.equals("noir"))
						// ia.jouer(e, "noir");
						// champTexte.setText("C'est le tour aux " + couleurControle);
						return true;
					}
				} else {
					tab[temp.getColonne()][temp.getLigne()]
							.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));
					pieceTampon = null;
					iconeTampon = null;
					temp = null;

				}

			}

		}
		return false;
	}

	// Je remet tout les attributs de la classe a 0
	public void RAZ() {
		for (int ligne = 0; ligne < 8; ligne++)
			for (int colonne = 0; colonne < 8; colonne++) {
				tab[colonne][ligne].setIcon(null);
				e.getCase(colonne, ligne).setPiece(null);

			}
		champTexte.setText("");
		boutonDebuter.setEnabled(true);
		e.debuter();
		String couleurControle = "noir";

		panelblanc.removeAll();
		panelblanc.repaint();
		panelnoir.removeAll();
		panelnoir.repaint();

	}

	// main pour pouvoir ex�cuter l'interface graphique
	public static void main(String[] args) {
		FenetreJeu j = new FenetreJeu();
		j.setVisible(true);
		j.setLocation(100, 130);
		j.setDefaultCloseOperation(EXIT_ON_CLOSE); // ferme le processus associ�
		ia = new IA(j, "noir");
	}
}