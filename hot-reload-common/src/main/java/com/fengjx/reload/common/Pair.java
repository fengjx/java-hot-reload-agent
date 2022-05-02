package com.fengjx.reload.common;

public class Pair<X, Y> {
    private final X x;
    private final Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getFirst() {
        return x;
    }

    public Y getSecond() {
        return y;
    }

    public static <A, B> Pair<A, B> make(A a, B b) {
        return new Pair<A, B>(a, b);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }

        Pair<X, Y> other = (Pair) o;

        if (x == null) {
            if (other.x != null)
                return false;
        } else {
            if (!x.equals(other.x))
                return false;
        }
        if (y == null) {
            return other.y == null;
        } else {
            return y.equals(other.y);
        }
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        if (x != null) {
            hashCode = x.hashCode();
        }
        if (y != null) {
            hashCode = (hashCode * 31) + y.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return "P[" + x + "," + y + "]";
    }
}
