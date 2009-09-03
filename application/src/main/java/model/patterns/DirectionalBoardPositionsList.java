package model.patterns;

import java.util.ArrayList;

public class DirectionalBoardPositionsList {
	ArrayList<Integer> indexList;

	public DirectionalBoardPositionsList() {
		indexList = new ArrayList<Integer>();
	}

	public void add(int position) {
		indexList.add(position);
	}

	public int get(int i) {
		return indexList.get(i);
	}

	public boolean contains(int position) {
		return indexList.contains(position);
	}

	public String toString() {
		String listString = "";

		for (int i = 0; i < indexList.size(); i++) {
			listString += indexList.get(i) + " ";
		}

		return listString;
	}

	public int size() {
		return indexList.size();
	}

	public int getIndexFor(int item) {
		return indexList.indexOf(item);
	}

}
