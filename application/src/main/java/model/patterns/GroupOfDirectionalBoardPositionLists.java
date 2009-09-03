package model.patterns;

import java.util.ArrayList;

public class GroupOfDirectionalBoardPositionLists {
	private ArrayList<DirectionalBoardPositionsList> group;

	public GroupOfDirectionalBoardPositionLists() {
		group = new ArrayList<DirectionalBoardPositionsList>();
	}

	public DirectionalBoardPositionsList get(int i) {
		return group.get(i);
	}

	public void add(DirectionalBoardPositionsList list) {
		group.add(list);

	}

	public int size() {
		return group.size();
	}

}
