package jeuEchecs;

/**
 * classe deplacement servant � repr�senter le deplacement d'une piece dans un
 * jeu d'echec
 *
 *
 */
public class Deplacement {

	/**
	 * Deplacement sur l'axe des X
	 */
	private double deplacementX;

	/**
	 * Deplacement sur l'axe des Y
	 */
	private double deplacementY;

	/**
	 * Coordonnee de la case d'arrivee
	 */
	private Position arrivee;

	/**
	 * Coordonnee de la case de depart
	 */
	private Position depart;

	/**
	 * Constructeur d'un objet d�placement, calcul les d�placement sur les axes X et
	 * Y. Ces valeurs ne sont pas donn� de mani�re absolue car le pion � besoin de
	 * conna�tre la direction dans laquelle il se d�place.
	 * 
	 * @param Prend
	 *            en param�tre les coordonnees de depart et d'arrive du deplacement
	 */
	public Deplacement(Position depart, Position arrivee) {
		this.arrivee = arrivee;
		this.depart = depart;
		this.deplacementX = arrivee.getColonne() - depart.getColonne();
		this.deplacementY = arrivee.getLigne() - depart.getLigne();
	}

	public double getDeplacementX() {
		return deplacementX;
	}

	public double getDeplacementY() {
		return deplacementY;
	}

	public Position getArrivee() {
		return arrivee;
	}

	public Position getDepart() {
		return depart;
	}

	// v�rifie si le d�placement est nul.
	public boolean isNul() {
		return deplacementX == 0 && deplacementY == 0;
	}

}
