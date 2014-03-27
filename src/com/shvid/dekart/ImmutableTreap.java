package com.shvid.dekart;

import java.io.PrintStream;

/**
 * 
 * @author Alex Shvid
 *
 */

public final class ImmutableTreap<K extends Comparable<K>, P extends Comparable<P>, V> {

	private final K key;
	private final P priority;
	private final V value;
	
	private final ImmutableTreap<K, P, V> left;
	private final ImmutableTreap<K, P, V> right;  
	
	public ImmutableTreap(K key, P priority, V value) {
		this.key = key;
		this.priority = priority;
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	public ImmutableTreap(ImmutableTreap<K, P, V> top, ImmutableTreap<K, P, V> left, ImmutableTreap<K, P, V> right) {
		this.key = top.key;
		this.priority = top.priority;
		this.value = top.value;
		this.left = left;
		this.right = right;
	}
	
	public K getKey() {
		return key;
	}
	
	public P getPriority() {
		return priority;
	}
	
	public V getValue() {
		return value;
	}
	
	public ImmutableTreap<K, P, V> get(K x0) {
		
		int c = this.key.compareTo(x0);
		
		if (c == 0) {
			return this;
		}
		else if (c < 0) {
			return this.right != null ? this.right.get(x0) : null;
		}
		else {
			return this.left != null ? this.left.get(x0) : null;
		}

	}
	
	public ImmutableTreap<K, P, V> put(K key, P priority, V value, boolean unique) {
		
		ImmutableTreap<K, P, V> el = new ImmutableTreap<K, P, V>(key, priority, value);
		
		Split<K, P, V> split = split(key, unique);
		
		return merge(merge(split.getLesser(), el), split.getGreater());
		
	}
	
	public ImmutableTreap<K, P, V> remove(K x) {

		Split<K, P, V> split = split(x, true);
		
		if (split.getDeleted() == null) {
			return this;
		}
		
		return merge(split.getLesser(), split.getGreater());
	}
	
	public static <K extends Comparable<K>, P extends Comparable<P>, V> ImmutableTreap<K, P, V> merge(ImmutableTreap<K, P, V> less, ImmutableTreap<K, P, V> greater) {
	    if (less == null) return greater;
	    if (greater == null) return less;

	    int c = less.priority.compareTo(greater.priority);
	    
	    if (c >= 0) {
	        return new ImmutableTreap<K, P, V>(less, less.left, merge(less.right, greater));
	    }
	    else {
	        return new ImmutableTreap<K, P, V>(greater, merge(less, greater.left), greater.right);
	    }
	}
	
	public Split<K, P, V> split(K x0, boolean deleteEquals) {
		
		int c = this.key.compareTo(x0);

		if (c == 0 && deleteEquals) {
			return new Split<K, P, V>(this.left, this.right, this);
		}
		if (c <= 0) {
			
			Split<K, P, V> rightSplit = this.right != null ? this.right.split(x0, deleteEquals) : null;
			
			if (rightSplit != null) {
				return new Split<K, P, V>(new ImmutableTreap<K, P, V>(this, this.left, rightSplit.getLesser()), rightSplit.getGreater(), rightSplit.getDeleted());
			}
			else {
				return new Split<K, P, V>(new ImmutableTreap<K, P, V>(this, this.left, null), null, null);
			}

		}
		else {
			
			Split<K, P, V> leftSplit = this.left != null ? this.left.split(x0, deleteEquals) : null;
			
			if (leftSplit != null) {
				return new Split<K, P, V>(leftSplit.getLesser(), new ImmutableTreap<K, P, V>(this, leftSplit.getGreater(), this.right), leftSplit.getDeleted());
			}
			else {
				return new Split<K, P, V>(null, new ImmutableTreap<K, P, V>(this, null, this.right), null);
			}
			
		}
	
	}
	
	public void print(PrintStream ps) {
		
		ps.println("digraph {");
		
		doPrint(ps);
		
		ps.println("}");
		
	}
	
	private String doPrint(PrintStream ps) {

		String title = "\"" + key + ":" + priority + ":" + value + "\"";

		String leftChild = left != null ? left.doPrint(ps) : null;
		String rightChild = right != null ? right.doPrint(ps) : null;

		if (leftChild != null) {
			ps.println(title + " -> " + leftChild);
		}

		if (rightChild != null) {
			ps.println(title + " -> " + rightChild);
		}

		return title;
	}
	
	public final static class Split<K extends Comparable<K>, P extends Comparable<P>, V> {

		private final ImmutableTreap<K, P, V> lesser;
		private final ImmutableTreap<K, P, V> greater;  
		private final ImmutableTreap<K, P, V> deleted;
		
		public Split(ImmutableTreap<K, P, V> lesser, ImmutableTreap<K, P, V> greater, ImmutableTreap<K, P, V> deleted) {
			this.lesser = lesser;
			this.greater = greater;
			this.deleted = deleted;
		}

		public ImmutableTreap<K, P, V> getLesser() {
			return lesser;
		}

		public ImmutableTreap<K, P, V> getGreater() {
			return greater;
		}

		public ImmutableTreap<K, P, V> getDeleted() {
			return deleted;
		}

	}
}
