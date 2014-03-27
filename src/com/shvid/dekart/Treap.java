package com.shvid.dekart;

import java.io.PrintStream;

public final class Treap<X extends Comparable<X>, Y extends Comparable<Y>, C> {

	private final X x;
	private final Y y;
	private final C c;
	
	private final Treap<X, Y, C> left;
	private final Treap<X, Y, C> right;  
	
	public Treap(X x, Y y, C c) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.left = null;
		this.right = null;
	}
	
	public Treap(Treap<X, Y, C> top, Treap<X, Y, C> left, Treap<X, Y, C> right) {
		this.x = top.x;
		this.y = top.y;
		this.c = top.c;
		this.left = left;
		this.right = right;
	}
	
	public Treap<X, Y, C> get(X x) {
		return null;
	}
	
	public Treap<X, Y, C> put(X x, Y y, C c, boolean unique) {
		
		Treap<X, Y, C> el = new Treap<X, Y, C>(x, y, c);
		
		Split<X, Y, C> split = split(x, unique);
		
		return merge(merge(split.getLesser(), el), split.getGreater());
		
	}
	
	public Treap<X, Y, C> remove(X x) {

		Split<X, Y, C> split = split(x, true);
		
		if (split.getDeleted() == null) {
			return this;
		}
		
		return merge(split.getLesser(), split.getGreater());
	}
	
	public static <X extends Comparable<X>, Y extends Comparable<Y>, C> Treap<X, Y, C> merge(Treap<X, Y, C> less, Treap<X, Y, C> greater) {
	    if (less == null) return greater;
	    if (greater == null) return less;

	    int c = less.y.compareTo(greater.y);
	    
	    if (c >= 0) {
	        return new Treap<X, Y, C>(less, less.left, merge(less.right, greater));
	    }
	    else {
	        return new Treap<X, Y, C>(greater, merge(less, greater.left), greater.right);
	    }
	}
	
	public Split<X, Y, C> split(X x0, boolean deleteEquals) {
		
		int c = this.x.compareTo(x0);

		if (c == 0 && deleteEquals) {
			return new Split<X, Y, C>(this.left, this.right, this);
		}
		if (c <= 0) {
			
			Split<X, Y, C> rightSplit = this.right != null ? this.right.split(x0, deleteEquals) : null;
			
			if (rightSplit != null) {
				return new Split<X, Y, C>(new Treap<X, Y, C>(this, this.left, rightSplit.getLesser()), rightSplit.getGreater(), rightSplit.getDeleted());
			}
			else {
				return new Split<X, Y, C>(new Treap<X, Y, C>(this, this.left, null), null, null);
			}

		}
		else {
			
			Split<X, Y, C> leftSplit = this.left != null ? this.left.split(x0, deleteEquals) : null;
			
			if (leftSplit != null) {
				return new Split<X, Y, C>(leftSplit.getLesser(), new Treap<X, Y, C>(this, leftSplit.getGreater(), this.right), leftSplit.getDeleted());
			}
			else {
				return new Split<X, Y, C>(null, new Treap<X, Y, C>(this, null, this.right), null);
			}
			
		}
	
	}
	
	public void print(PrintStream ps) {
		
		ps.println("digraph {");
		
		doPrint(ps);
		
		ps.println("}");
		
	}
	
	private String doPrint(PrintStream ps) {

		String title = "\"" + x + ":" + y + ":" + c + "\"";

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
}
