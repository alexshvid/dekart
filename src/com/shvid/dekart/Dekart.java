package com.shvid.dekart;

import java.util.Random;

/**
 * 
 * @author Alex Shvid
 *
 */

public class Dekart {

	public static void main(String[] args) {
		
		System.out.println("Dekart");
		
		ImmutableTreap<Integer, Integer, String> treap1 = new ImmutableTreap<Integer, Integer, String>(5, 500, "hello");
		ImmutableTreap<Integer, Integer, String> treap2 = new ImmutableTreap<Integer, Integer, String>(7, 1000, "world");
		ImmutableTreap<Integer, Integer, String> treap3 = new ImmutableTreap<Integer, Integer, String>(10, 10, "!");
		
		ImmutableTreap<Integer, Integer, String> treapR = ImmutableTreap.merge(ImmutableTreap.merge(treap1, treap2), treap3);
		
		treapR.print(System.out);
		
		ImmutableTreap.Split<Integer, Integer, String> split = treapR.split(6, false);
		
		if (split.getLesser() != null) {
			split.getLesser().print(System.out);
		}

		if (split.getGreater() != null) {
			split.getGreater().print(System.out);
		}

		Random random = new Random(5);
		putAll(random);
		
		System.out.println("Visualization here:\nhttp://cpettitt.github.io/project/dagre-d3/latest/demo/interactive-demo.html");
	
		
		IndexedTreap<Integer, String> it1 = new IndexedTreap<Integer, String>(123, "Hello");
		IndexedTreap<Integer, String> it2 = new IndexedTreap<Integer, String>(345, "world");
		IndexedTreap<Integer, String> it3 = new IndexedTreap<Integer, String>(777, "!");
		
		IndexedTreap<Integer, String> it = IndexedTreap.merge(IndexedTreap.merge(it1, it2), it3);
		
		it = it.remove(0);
		
		int size = it.size();
		
		System.out.println("size = " + size);
	   
		for (int i = 0; i != size; ++i) {
			System.out.println("i = " + i + ", val = " + it.get(i));
		}
	}
	
	public static void putAll(Random random) {
		
		ImmutableTreap<Integer, Integer, String> treap = new ImmutableTreap<Integer, Integer, String>(7, 555, "first");

		for (int i = 0; i != 20; ++i) {
			int y = random.nextInt(1000);
			treap = treap.put(i, y, "loop" + i, false);
		}
		
		treap = treap.put(2, 333, "double", false);
		treap = treap.put(2, 777, "triple", false);
		
		treap.print(System.out);
		

		
	}
	
}
