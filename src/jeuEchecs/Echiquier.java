package jeuEchecs;

/**
 * classe echiquier servant a representer une planche de jeu
 *
 *
 */

public class Echiquier implements MethodesEchiquier, Cloneable {
	/**
	 * Tableau d'objet Case, contient les cases qui contienne les Piece
	 */
	private Case[][] location;

	/**
	 * Constructeur de la classe Echiquier, cree un tableau de 8X8 de Case qui, par
	 * defaut, ne contienne aucunne piece.
	 *
	 * @param Prend
	 *            en parametre la couleur du cavalier
	 *
	 */
	public Echiquier() {
		location = new Case[8][8];
		for (int ctr = 0; ctr <= 7; ctr++)
			for (int ctr1 = 0; ctr1 <= 7; ctr1++)
				location[ctr][ctr1] = new Case();

	}

	/**
	 * Methode capture par un pion possible, verifie si le deplacement d'un pion en
	 * diagonale est valide. Donc, il verifie si une piece d'une couleur contraire
	 * au pion se trouve a sa diagonale, si oui, le deplacement est accepte
	 *
	 * @param Deplacement
	 *            du pion
	 * @return vrai ou faux si le pion peut manger la piece ou non
	 */
	@Override
	public boolean captureParUnPionPossible(Deplacement deplacement) {
		// Je v�rifie si la pi�ce est un pion
		if (location[deplacement.getDepart().getColonne()][deplacement.getDepart().getLigne()]
				.getPiece() instanceof Pion) {
			// initialisation des variables dont j'aurai besoin dans mes conditions, �
			// savoir la couleur de la pi�ce de d�part et la case d'arriv�.
			Case Arrive = location[deplacement.getArrivee().getColonne()][deplacement.getArrivee().getLigne()];
			String couleurDepart = location[deplacement.getDepart().getColonne()][deplacement.getDepart().getLigne()]
					.getPiece().getCouleur();

			// je v�rifie d'abord si la pi�ce d'arriv� existe et si elle est de la couleur
			// contraire de celle de d�part.
			if (Arrive.estOccupe(couleurDepart.equals("blanc") ? "noir" : "blanc"))
				/*
				 * Je v�rifie si le d�placement est valide, Le d�placement est valide si le
				 * produits du d�placement x et y donne 1 si la couleur de d�part est noir ou -1
				 * si la pi�ce de d�part est blanche.
				 */
				return (deplacement.getDeplacementY()
						* Math.abs(deplacement.getDeplacementX()) == (couleurDepart.equals("noir") ? 1 : -1));
		}
		return false;

	}

	/**
	 * Methode chemin possible, verifie si la piece peut faire le deplacement. Pour
	 * ce faire il verifie si le chemin est libre entre le depart et l'arrive.
	 *
	 * @param Deplacement
	 *            d'une piece
	 * @return vrai ou faux si la piece peut faire le deplacement ou non
	 */
	@Override
	public boolean cheminPossible(Deplacement deplacement) {
		Piece pieceDepart = location[deplacement.getDepart().getColonne()][deplacement.getDepart().getLigne()]
				.getPiece();

		/*
		 * deux premi�re condition fondamentale, que la case d'arriv� sois libre ou
		 * qu'elle poss�de une pi�ce de couleur contraire � celle de la pi�ce de d�part
		 */
		if (!location[deplacement.getArrivee().getColonne()][deplacement.getArrivee().getLigne()]
				.estOccupe(pieceDepart.getCouleur().equals("blanc") ? "blanc" : "noir") | deplacement.isNul()) {
			if (!(pieceDepart instanceof Cavalier)) {
				if (!(pieceDepart instanceof Pion)) {
					// Je v�rifie que le d�placement est sup�rieur � un.
					if (!(Math.abs(deplacement.getDeplacementX()) - Math.abs(deplacement.getDeplacementY()) <= 1
							&& Math.abs(deplacement.getDeplacementX())
									+ Math.abs(deplacement.getDeplacementY()) <= 1)) {

						/*
						 * JumpX et jumpY seront sois 0, 1 ou -1, ils indiquent l'incr�mentation que je
						 * devrai utiliser pour les valeurs X et Y pour v�rifier toute les cases entre
						 * le d�part et l'arriv�
						 */
						int jumpX = deplacement.getDeplacementX() == 0 ? 0
								: (deplacement.getArrivee().getColonne() - deplacement.getDepart().getColonne())
										/ Math.abs(deplacement.getArrivee().getColonne()
												- deplacement.getDepart().getColonne());

						int jumpY = deplacement.getDeplacementY() == 0 ? 0
								: (deplacement.getArrivee().getLigne() - deplacement.getDepart().getLigne()) / Math
										.abs(deplacement.getArrivee().getLigne() - deplacement.getDepart().getLigne());

						// Je v�rifie succ�cessivement toutes les cases entre l'arriv�e et le d�part
						for (int ctrX = deplacement.getDepart().getColonne() + jumpX, ctrY = deplacement.getDepart()
								.getLigne() + jumpY; ctrX != deplacement.getArrivee().getColonne()
										| ctrY != deplacement.getArrivee().getLigne(); ctrX += jumpX, ctrY += jumpY) {
							if (location[ctrX][ctrY].estOccupe()) {
								return false;
							}
						}
						return true;
					} else
						/*
						 * Si le d�placement est �gal il est automatiquement valide car il a pass� les
						 * pr�cedents test. Puisque le d�placement est de 1, je n'ai pas besoin de
						 * v�rifier les cases entre le d�part et l'arriv�
						 */
						return true;
				} else
					// Si c'est un pion, je v�rifie si la case est libre de toute pi�ce.
					return !location[deplacement.getArrivee().getColonne()][deplacement.getArrivee().getLigne()]
							.estOccupe();

			} else
				// je renvoie true car un cavalier peut sauter par dessus les autres pi�ces.
				return true;
		} else
			// Le d�placement est automatiquement invalide si la case d'arriv� contient une
			// pi�ce de m�me couleur que la pi�ce de d�part.
			return false;

	}

	@Override
	public Case getCase(int colonne, int ligne) {
		return location[colonne][ligne];
	}

	/**
	 * Methode servant a placer toute les piece sur l'echiquier. Met donc toute lesp
	 * iece sur les objets cases du tableau location
	 */
	@Override
	public void debuter() {
		int ligne = 7;
		/*
		 * Je fais les instructions deux fois, au premier passage, ligne est �gal � 7 et
		 * la couleur � noir, et au deuxi�me passage, la couleur est blanche et la ligne
		 * est �gal � 0.
		 */
		for (String couleur = "noir"; !couleur.equals("blanc"); couleur = "blanc", ligne = 0) {
			// J'initialise tout mes pi�ces de la premi�res rang�e (tour, cavalier etc...)
			location[0][ligne].setPiece(new Tour(couleur));
			location[1][ligne].setPiece(new Cavalier(couleur));
			location[2][ligne].setPiece(new Fou(couleur));
			location[3][ligne].setPiece(new Reine(couleur));
			location[4][ligne].setPiece(new Roi(couleur));
			location[5][ligne].setPiece(new Fou(couleur));
			location[6][ligne].setPiece(new Cavalier(couleur));
			location[7][ligne].setPiece(new Tour(couleur));
			// Je change de ligne, d�pendament de la couleur que je suis en train de
			// tra�ter.
			ligne += couleur.equals("noir") ? -1 : 1;
			// J'initialise tout mes pions.
			for (int ctr = 0; ctr <= 7; ctr++)
				location[ctr][ligne].setPiece(new Pion(couleur));
		}
	}

	@Override
	public Object clone() {
		Echiquier clone = new Echiquier();
		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {
				Piece piece = this.getCase(y, x).getPiece();
				if (piece == null) {

				} else {
					String couleur = piece.getCouleur();
					if (piece instanceof Cavalier) {
						clone.getCase(y, x).setPiece(new Cavalier(couleur));
					} else if (piece instanceof Fou) {
						clone.getCase(y, x).setPiece(new Fou(couleur));
					} else if (piece instanceof Pion) {
						clone.getCase(y, x).setPiece(new Pion(couleur));
					} else if (piece instanceof Reine) {
						clone.getCase(y, x).setPiece(new Reine(couleur));
					} else if (piece instanceof Roi) {
						clone.getCase(y, x).setPiece(new Roi(couleur));
					} else if (piece instanceof Tour) {
						clone.getCase(y, x).setPiece(new Tour(couleur));
					}
				}
			}
		return clone;
	}

	private void init() {
		this.debuter();
		char[] ordrePiece = { 'T', 'C', 'F', 'D', 'R', 'F', 'C', 'T' };
		int increment = 1;
		int ligne = 0;
		Piece tempo = null;
		this.debuter();
		while (increment >= -1) {
			for (int ctr = 0; ctr <= 7; ctr++) {
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
				this.getCase(ctr, ligne).setPiece(tempo);
				this.getCase(ctr, ligne + increment).setPiece(new Pion(ligne < 5 ? "noir" : "blanc"));
			}
			increment -= 2;
			ligne = 7;
		}
	}

}
