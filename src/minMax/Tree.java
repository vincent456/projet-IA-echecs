package minMax;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jeuEchecs.Deplacement;
import jeuEchecs.Echiquier;

public class Tree {
	private Tree parent;
	private List<Tree> children;

	public enum minmax {
		min, max
	}

	private minmax valueMinmax;
	private Integer value;
	private Echiquier echiquier;
	private Deplacement deplacement;

	public Tree getParent() {
		return parent;
	}

	public void setParent(Tree parent) {
		this.parent = parent;
	}

	public List<Tree> getChildren() {
		return children;
	}

	public void setChildren(List<Tree> children) {
		this.children = children;
	}

	public minmax getValueMinmax() {
		return valueMinmax;
	}

	public void setValueMinmax(minmax valueMinmax) {
		this.valueMinmax = valueMinmax;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Echiquier getEchiquier() {
		return echiquier;
	}

	public void setEchiquier(Echiquier echiquier) {
		this.echiquier = echiquier;
	}

	public Deplacement getDeplacement() {
		return deplacement;
	}

	public void setDeplacement(Deplacement deplacement) {
		this.deplacement = deplacement;
	}

	public Tree(Echiquier e) {
		this.parent = null;
		this.children = new LinkedList<Tree>();
		this.valueMinmax = minmax.max;
		this.echiquier = e;
		this.value = null;
	}

	public Tree(Tree parent, Echiquier e, Deplacement d) {
		this.parent = parent;
		parent.getChildren().add(this);
		this.children = new LinkedList<Tree>();
		if (parent.getValueMinmax() == minmax.min) {
			this.valueMinmax = minmax.max;
		} else // parent.getValueMinmax()==minmax.max
		{
			this.valueMinmax = minmax.min;
		}
		this.echiquier = e;
		this.deplacement = d;
		this.value = null;
	}

	public List<Tree> getLeaves() {
		List<Tree> out = new LinkedList<>();

		Queue<Tree> q = new LinkedList<>();
		q.add(this);
		while (!q.isEmpty()) {
			Tree t = q.remove();
			for (Tree c : t.getChildren()) {
				if (c != null) {
					if (c.getChildren().size() == 0) {
						out.add(c);
					} else {
						q.add(c);
					}
				}
			}
		}
		return out;
	}

	public Integer valueTree() {
		if (this.children.isEmpty()) {
			return this.value;
		} else // !this.children.isEmpty()
		{
			for (Tree t : children) {
				t.setValue(t.valueTree());
			}
			if (this.valueMinmax == minmax.min) {
				Integer min = min();
				return min;
			} else // this.valueMinmax==minmax.max
			{
				Integer max = max();
				return max;
			}
		}
	}

	public Integer min() {
		Integer min = children.get(0).getValue();
		for (Tree t : children) {
			if (t.getValue() == null)
				return null;
			if (t.getValue() < min) {
				min = t.getValue();
			}
		}
		return min;
	}

	public Integer max() {
		Integer max = children.get(0).getValue();
		for (Tree t : children) {
			if (t.getValue() == null)
				return null;
			if (t.getValue() > max) {
				max = t.getValue();
			}
		}
		return max;
	}
}
