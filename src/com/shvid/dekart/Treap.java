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
	
	public static <X extends Comparable<X>, Y extends Comparable<Y>, C> Treap<X, Y, C> merge(Treap<X, Y, C> less, Treap<X, Y, C> greater) {
	    if (less == null) return greater;
	    if (greater == null) return less;

	    if (less.y.compareTo(greater.y) > 0) {
	        return new Treap<X, Y, C>(less, less.left, merge(less.right, greater));
	    }
	    else {
	        return new Treap<X, Y, C>(greater, merge(less, greater.left), less.right);
	    }
	}
	
	public Split<X, Y, C> split(X x0) {
		
		if (this.x.compareTo(x0) <= 0) {
			
			Split<X, Y, C> rightSplit = this.right != null ? this.right.split(x0) : null;
			
			Treap<X, Y, C> rightLesserX = rightSplit != null ? rightSplit.getLesser() : null;
			Treap<X, Y, C> rightGreaterX = rightSplit != null ? rightSplit.getGreater() : null;

			return new Split<X, Y, C>(new Treap<X, Y, C>(this, this.left, rightLesserX), rightGreaterX);

		}
		else {
			
			Split<X, Y, C> leftSplit = this.left != null ? this.left.split(x0) : null;
			
			Treap<X, Y, C> leftLesserX = leftSplit != null ? leftSplit.getLesser() : null;
			Treap<X, Y, C> leftGreaterX = leftSplit != null ? leftSplit.getGreater() : null;
			
			return new Split<X, Y, C>(leftLesserX, new Treap<X, Y, C>(this, leftGreaterX, this.right));
			
		}
	
	}
	
	public void print(PrintStream ps, String prefix) {
		ps.println(prefix + "Treap " + this.hashCode() + ", x = " + x + ", y = " + y + ", c = " + c);

		if (left != null) {
			left.print(ps, "l    ");
		}

		if (right != null) {
			right.print(ps, "r    ");
		}

	}
}
