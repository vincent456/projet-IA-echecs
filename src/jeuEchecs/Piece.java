package jeuEchecs;

/**
 * classe abstraite Piece servant de point de depart pour tous les types de
 * pieces d'un jeu d'echecs
 * 
 * 
 */

public abstract class Piece {

	/**
	 * nom de la pi�ce selon les conventions
	 */
	private String nom;
	/**
	 * couleur de la pi�ce
	 */
	private String couleur;

	/**
	 * constructeur permettant d'initialiser le nom et la couleur d'un objet Piece
	 *
	 * @param prend
	 *            en parametre le nom de la piece
	 * @param prend
	 *            en parametre la couleur de la piece
	 */
	public Piece(String nom, String couleur) {
		setNom(nom);
		setCouleur(couleur);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		if ((couleur == "noir") || (couleur == "blanc"))
			this.couleur = couleur;
	}

	/**
	 * m�thode abstraite a implementer dans les sous-classe, va servir a verifier la
	 * validite du deplacement des piece
	 * 
	 * @param Deplacement
	 *            de la piece
	 * @return true ou false dependamment de la validit� du deplacement
	 */

	public abstract boolean estValide(Deplacement deplacement);

}
