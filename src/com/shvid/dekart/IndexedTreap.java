package com.shvid.dekart;

import java.io.PrintStream;

/**
 * 
 * @author Alex Shvid
 *
 */

public class IndexedTreap<P extends Comparable<P>, V> {

	private final int size;
	private final P priority;
	private final V value;
	
	private final IndexedTreap<P, V> left;
	private final IndexedTreap<P, V> right;  
	
	public IndexedTreap(P priority, V value) {
		this.size = 1;
		this.priority = priority;
		this.value = value;
		this.left = null;
		this.right = null;
	}
	
	public IndexedTreap(IndexedTreap<P, V> top, IndexedTreap<P, V> left, IndexedTreap<P, V> right) {
		this.size = 1 + getSize(left) + getSize(right);
		this.priority = top.priority;
		this.value = top.value;
		this.left = left;
		this.right = right;
	}
	
	public static <Y extends Comparable<Y>, C> int getSize(IndexedTreap<Y, C> treap) {
		return treap != null ? treap.size : 0;
	}
	
	public IndexedTreap<P, V> get(int i) {
		
		int index = getSize(this.left);
		
		int c = index - i;
		
		if (c == 0) {
			return this;
		}
		else if (c < 0) {
			return this.right != null ? this.right.get(i - index) : null;
		}
		else {
			return this.left != null ? this.left.get(i) : null;
		}
		
	}

	public IndexedTreap<P, V> put(int i, P priority, V value, boolean replace) {
		
		IndexedTreap<P, V> el = new IndexedTreap<P, V>(priority, value);
		
		Split<P, V> split = split(i, replace);
		
		return merge(merge(split.getLesser(), el), split.getGreater());
		
	}
	
	public IndexedTreap<P, V> remove(int i) {

		Split<P, V> split = split(i, true);
		
		if (split.getDeleted() == null) {
			return this;
		}
		
		return merge(split.getLesser(), split.getGreater());
	}
	
	public P getPriority() {
		return priority;
	}
	
	public V getValue() {
		return value;
	}

	public int size() {
		return getSize(this);
	}
	
	public static <P extends Comparable<P>, V> IndexedTreap<P, V> merge(IndexedTreap<P, V> less, IndexedTreap<P, V> greater) {
	    if (less == null) return greater;
	    if (greater == null) return less;

	    int c = less.priority.compareTo(greater.priority);
	    
	    if (c >= 0) {
	        return new IndexedTreap<P, V>(less, less.left, merge(less.right, greater));
	    }
	    else {
	        return new IndexedTreap<P, V>(greater, merge(less, greater.left), greater.right);
	    }
	}
	
	
	public Split<P, V> split(int i, boolean deleteEquals) {
		
		int index = getSize(this.left);
		
		int c = index - i;

		if (c == 0 && deleteEquals) {
			System.out.println("delete");
			
			return new Split<P, V>(this.left, this.right, this);
		}
		if (c <= 0) {
			
			Split<P, V> rightSplit = this.right != null ? this.right.split(i - index, deleteEquals) : null;
			
			if (rightSplit != null) {
				return new Split<P, V>(new IndexedTreap<P, V>(this, this.left, rightSplit.getLesser()), rightSplit.getGreater(), rightSplit.getDeleted());
			}
			else {
				return new Split<P, V>(new IndexedTreap<P, V>(this, this.left, null), null, null);
			}

		}
		else {
			
			Split<P, V> leftSplit = this.left != null ? this.left.split(i, deleteEquals) : null;
			
			if (leftSplit != null) {
				return new Split<P, V>(leftSplit.getLesser(), new IndexedTreap<P, V>(this, leftSplit.getGreater(), this.right), leftSplit.getDeleted());
			}
			else {
				return new Split<P, V>(null, new IndexedTreap<P, V>(this, null, this.right), null);
			}
			
		}
	
	}
	
	public void print(PrintStream ps) {
		
		MutableInt index = new MutableInt();
		
		ps.println("digraph {");
		
		doPrint(ps, index);
		
		ps.println("}");
		
	}
	
	private String doPrint(PrintStream ps, MutableInt index) {

		String leftChild = left != null ? left.doPrint(ps, index) : null;
		
		int currentIndex = index.value++;
		String title = "\"" + currentIndex + ":" + priority + ":" + value + "\"";
		
		String rightChild = right != null ? right.doPrint(ps, index) : null;

		if (leftChild != null) {
			ps.println(title + " -> " + leftChild);
		}

		if (rightChild != null) {
			ps.println(title + " -> " + rightChild);
		}

		return title;
	}
	
	public final static class MutableInt {
		public int value = 0;
	}
	
	public final static class Split<P extends Comparable<P>, V> {

		private final IndexedTreap<P, V> lesser;
		private final IndexedTreap<P, V> greater;  
		private final IndexedTreap<P, V> deleted;
		
		public Split(IndexedTreap<P, V> lesser, IndexedTreap<P, V> greater, IndexedTreap<P, V> deleted) {
			this.lesser = lesser;
			this.greater = greater;
			this.deleted = deleted;
		}

		public IndexedTreap<P, V> getLesser() {
			return lesser;
		}

		public IndexedTreap<P, V> getGreater() {
			return greater;
		}

		public IndexedTreap<P, V> getDeleted() {
			return deleted;
		}

	}

	@Override
	public String toString() {
		return "IndexedTreap [size=" + size + ", priority=" + priority
				+ ", value=" + value + "]";
	}
	
	
	
}
