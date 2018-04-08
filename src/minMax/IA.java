package minMax;

import java.util.LinkedList;
import java.util.List;

import jeuEchecs.Case;
import jeuEchecs.Deplacement;
import jeuEchecs.Echiquier;
import jeuEchecs.FenetreJeu;
import jeuEchecs.Piece;
import jeuEchecs.Position;
import minMax.Tree.minmax;

public class IA {

	private FenetreJeu fdj;

	public IA(FenetreJeu fdj) {
		this.fdj = fdj;
	}

	public void jouer(Echiquier e, String color, int depht) {
		Tree t = new Tree((Echiquier) e.clone());
		List<Tree> tl1 = new LinkedList<>();
		tl1.add(t);
		List<Tree> children = buildChildren(e, tl1, color, 0, depht);
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

	private List<Tree> buildChildren(Echiquier e, List<Tree> t, String color, int depth, int maxdepth) {
		System.out.println("depth= " + depth);
		if (depth == maxdepth) {
			return t;
		}
		List<Tree> out = new LinkedList<>();
		for (Tree tree : t) {
			out.addAll(buildChildren(e, buildDirectChildren(e, tree, color), color, depth + 1, maxdepth));
		}
		return out;
	}

	private List<Tree> buildDirectChildren(Echiquier e, Tree t, String color) {
		List<Tree> out = new LinkedList<>();
		String moveColor;
		if (t.getValueMinmax() == minmax.max) {
			moveColor = color;
		} else // t.getValueMinmax()==minmax.min
		{
			moveColor = color == "noir" ? "blanc" : "noir";
		}
		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++) {
				for (int y2 = 0; y2 < 8; y2++)
					for (int x2 = 0; x2 < 8; x2++) {
						Echiquier eCopy = (Echiquier) e.clone();
						fdj.jouerUnCoup(eCopy, y, x, moveColor);
						if (FenetreJeu.getPieceTampon() != null) {
							if (fdj.jouerUnCoup(eCopy, y2, x2, moveColor)) {
								out.add(new Tree(t, eCopy, new Deplacement(new Position(y, x), new Position(y2, x2))));
							}
						}
					}
			}
		return out;
	}

	public int value(Echiquier e, String color) {
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
