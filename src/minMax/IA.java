package minMax;

import java.util.LinkedList;
import java.util.List;

import jeuEchecs.Case;
import jeuEchecs.Echiquier;
import jeuEchecs.FenetreJeu;
import jeuEchecs.Piece;

public class IA {

	private String color;
	private FenetreJeu fdj;

	public void init(FenetreJeu fdj, String color) {
		this.color = color;
		this.fdj = fdj;
	}

	public static void jouer(Echiquier e, String color) {
		Tree t = new Tree((Echiquier) e.clone());
		List<Tree> children = buildChildren(e, t);
	}

	private static List<Tree> buildChildren(Echiquier e, Tree t) {
		LinkedList<Tree> out = new LinkedList<>();
		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {
				for (int y2 = 0; y2 < 8; y2++)
					for (int x2 = 0; x2 < 8; x2++) {
						Echiquier eCopy = (Echiquier) e.clone();
					}
			}
		return out;
	}

	private static int value(Echiquier e, String color) {
		int out = 0;
		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {
				Case cas = e.getCase(y, x);
				Piece piece = cas.getPiece();
				if (piece != null) {
					if (piece.getCouleur() == color) {
						// friendly
						switch (piece.getNom()) {
						case ("Cavalier"):
							out += 3;
							break;
						case ("Fou"):
							out += 3;
							break;
						case ("Pion"):
							out += 1;
							break;
						case ("Reine"):
							out += 9;
							break;
						case ("Roi"):
							out += 1000;
							break;
						default:
							break;
						}
					} else {
						// hostile
						switch (piece.getNom()) {
						case ("Cavalier"):
							out -= 3;
							break;
						case ("Fou"):
							out -= 3;
							break;
						case ("Pion"):
							out -= 1;
							break;
						case ("Reine"):
							out -= 9;
							break;
						case ("Roi"):
							out -= 1000;
							break;
						default:
							break;
						}
					}
				}
			}
		return out;
	}
}
