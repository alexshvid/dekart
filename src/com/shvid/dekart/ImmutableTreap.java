package com.shvid.dekart;

import java.io.PrintStream;

public final class ImmutableTreap<X extends Comparable<X>, Y extends Comparable<Y>, C> {

	private final X x;
	private final Y y;
	private final C c;
	
	private final ImmutableTreap<X, Y, C> left;
	private final ImmutableTreap<X, Y, C> right;  
	
	public ImmutableTreap(X x, Y y, C c) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.left = null;
		this.right = null;
	}
	
	public ImmutableTreap(ImmutableTreap<X, Y, C> top, ImmutableTreap<X, Y, C> left, ImmutableTreap<X, Y, C> right) {
		this.x = top.x;
		this.y = top.y;
		this.c = top.c;
		this.left = left;
		this.right = right;
	}
	
	public ImmutableTreap<X, Y, C> get(X x) {
		return search(x);
	}
	
	public ImmutableTreap<X, Y, C> put(X x, Y y, C c, boolean unique) {
		
		ImmutableTreap<X, Y, C> el = new ImmutableTreap<X, Y, C>(x, y, c);
		
		Split<X, Y, C> split = split(x, unique);
		
		return merge(merge(split.getLesser(), el), split.getGreater());
		
	}
	
	public ImmutableTreap<X, Y, C> remove(X x) {

		Split<X, Y, C> split = split(x, true);
		
		if (split.getDeleted() == null) {
			return this;
		}
		
		return merge(split.getLesser(), split.getGreater());
	}
	
	public static <X extends Comparable<X>, Y extends Comparable<Y>, C> ImmutableTreap<X, Y, C> merge(ImmutableTreap<X, Y, C> less, ImmutableTreap<X, Y, C> greater) {
	    if (less == null) return greater;
	    if (greater == null) return less;

	    int c = less.y.compareTo(greater.y);
	    
	    if (c >= 0) {
	        return new ImmutableTreap<X, Y, C>(less, less.left, merge(less.right, greater));
	    }
	    else {
	        return new ImmutableTreap<X, Y, C>(greater, merge(less, greater.left), greater.right);
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
				return new Split<X, Y, C>(new ImmutableTreap<X, Y, C>(this, this.left, rightSplit.getLesser()), rightSplit.getGreater(), rightSplit.getDeleted());
			}
			else {
				return new Split<X, Y, C>(new ImmutableTreap<X, Y, C>(this, this.left, null), null, null);
			}

		}
		else {
			
			Split<X, Y, C> leftSplit = this.left != null ? this.left.split(x0, deleteEquals) : null;
			
			if (leftSplit != null) {
				return new Split<X, Y, C>(leftSplit.getLesser(), new ImmutableTreap<X, Y, C>(this, leftSplit.getGreater(), this.right), leftSplit.getDeleted());
			}
			else {
				return new Split<X, Y, C>(null, new ImmutableTreap<X, Y, C>(this, null, this.right), null);
			}
			
		}
	
	}
	
	public ImmutableTreap<X, Y, C> search(X x0) {
		
		int c = this.x.compareTo(x0);
		
		if (c == 0) {
			return this;
		}
		else if (c < 0) {
			return this.right != null ? this.right.search(x0) : null;
		}
		else {
			return this.left != null ? this.left.search(x0) : null;
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
