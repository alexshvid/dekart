package com.shvid.dekart;

public class Dekart {

	public static void main(String[] args) {
		
		System.out.println("Dekart");
		
		Treap<Integer, Integer, String> treap1 = new Treap<Integer, Integer, String>(5, 500, "hello");
		Treap<Integer, Integer, String> treap2 = new Treap<Integer, Integer, String>(7, 1000, "world");
		Treap<Integer, Integer, String> treap3 = new Treap<Integer, Integer, String>(10, 10, "!");
		
		Treap<Integer, Integer, String> treapR = Treap.merge(Treap.merge(treap1, treap2), treap3);
		
		treapR.print(System.out, "");
		
		Split<Integer, Integer, String> split = treapR.split(6);
		
		if (split.getLesser() != null) {
			split.getLesser().print(System.out, "splitLesser ");
		}

		if (split.getGreater() != null) {
			split.getGreater().print(System.out, "splitGreater ");
		}

		
	}
	
}
