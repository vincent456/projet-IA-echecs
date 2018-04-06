package minMax;

import java.util.LinkedList;
import java.util.List;

import jeuEchecs.Case;
import jeuEchecs.Deplacement;
import jeuEchecs.Echiquier;
import jeuEchecs.FenetreJeu;
import jeuEchecs.Piece;
import jeuEchecs.Position;

public class IA {

	private String color;
	private FenetreJeu fdj;

	public IA(FenetreJeu fdj, String color) {
		this.color = color;
		this.fdj = fdj;
	}

	public void jouer(Echiquier e, String color) {
		Tree t = new Tree((Echiquier) e.clone());
		List<Tree> children = buildChildren(e, t, color);
		for (Tree tree : children) {
			tree.setValue(value(tree.getEchiquier(), color));
		}
		t.setValue(t.valueTree());
		int valRoot = t.getValue();
		for (Tree tree : children) {
			if (tree.getValue() == valRoot) {
				fdj.jouerUnCoup(e, tree.getDeplacement().getDepart().getColonne(),
						tree.getDeplacement().getDepart().getLigne(), color);
				fdj.jouerUnCoup(e, tree.getDeplacement().getArrivee().getColonne(),
						tree.getDeplacement().getArrivee().getLigne(), color);
				break;
			}
		}
	}

	private List<Tree> buildChildren(Echiquier e, Tree t, String color) {
		LinkedList<Tree> out = new LinkedList<>();
		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {
				for (int y2 = 0; y2 < 8; y2++)
					for (int x2 = 0; x2 < 8; x2++) {
						Echiquier eCopy = (Echiquier) e.clone();
						fdj.jouerUnCoup(eCopy, y, x, color);
						if (FenetreJeu.getPieceTampon() != null) {
							if (fdj.jouerUnCoup(eCopy, y2, x2, color)) {
								out.add(new Tree(t, eCopy, new Deplacement(new Position(y, x), new Position(y2, x2))));
							}
						}
					}
			}
		return out;
	}

	private int value(Echiquier e, String color) {
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
						case ("Tour"):
							out += 5;
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
						case ("Tour"):
							out -= 5;
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
