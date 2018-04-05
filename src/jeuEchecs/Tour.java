package jeuEchecs;
/**
 * classe tour servant � repr�senter la tour dans un jeu d'echec
 * 
 */
public class Tour extends Piece {
	/**
	 * Constructeur de la classe cavalier
	 * 
	 * @param Prend
	 *            en parametre la couleur de la tour
	 *
	 */
	public Tour(String Couleur) {
		super("Tour", Couleur);
	}

	/**
	 * Methode estValide, sert a verifier la validite du deplacement du roi
	 * 
	 * @return true ou false si le deplacement du roi est valide
	 * @param Deplacement
	 *            de la piece
	 */
	@Override
	public boolean estValide(Deplacement deplacement) {
		/*
		 * Si le produit du d�placement x et du d�placement y est �gal � 0, c'est que un
		 * des deux d�placements est de 0. Se qui veux dire que le mouvement est
		 * uniquement vertical ou horizontale
		 */
		return deplacement.getDeplacementX() * deplacement.getDeplacementY() == 0 && !deplacement.isNul();
	}

}
