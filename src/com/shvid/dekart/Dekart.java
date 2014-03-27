package com.shvid.dekart;

import java.util.Random;

public class Dekart {

	public static void main(String[] args) {
		
		System.out.println("Dekart");
		
		Treap<Integer, Integer, String> treap1 = new Treap<Integer, Integer, String>(5, 500, "hello");
		Treap<Integer, Integer, String> treap2 = new Treap<Integer, Integer, String>(7, 1000, "world");
		Treap<Integer, Integer, String> treap3 = new Treap<Integer, Integer, String>(10, 10, "!");
		
		Treap<Integer, Integer, String> treapR = Treap.merge(Treap.merge(treap1, treap2), treap3);
		
		treapR.print(System.out);
		
		Split<Integer, Integer, String> split = treapR.split(6, false);
		
		if (split.getLesser() != null) {
			split.getLesser().print(System.out);
		}

		if (split.getGreater() != null) {
			split.getGreater().print(System.out);
		}

		Random random = new Random(5);
		putAll(random);
		
		System.out.println("Visualization here:\nhttp://cpettitt.github.io/project/dagre-d3/latest/demo/interactive-demo.html");
		
	}
	
	public static void putAll(Random random) {
		
		Treap<Integer, Integer, String> treap = new Treap<Integer, Integer, String>(7, 555, "first");

		for (int i = 0; i != 5; ++i) {
			int y = random.nextInt(1000);
			treap = treap.put(i, y, "loop" + i, false);
		}
		
		treap = treap.put(2, 333, "double", false);
		treap = treap.put(2, 777, "triple", false);
		
		treap.print(System.out);
		
		System.out.println("2 = " + treap.get(2));
		treap = treap.remove(2);
		treap.print(System.out);
		
		System.out.println("2 = " + treap.get(2));
		treap = treap.remove(2);
		treap.print(System.out);
		
		System.out.println("2 = " + treap.get(2));
		treap = treap.remove(2);
		
		treap.print(System.out);
		System.out.println("2 = " + treap.get(2));
		
		System.out.println("T = " + treap);
		treap = treap.remove(2);
		System.out.println("T = " + treap);
		
		treap.print(System.out);
		
		System.out.println("2 = " + treap.get(2));
		
	}
	
}
