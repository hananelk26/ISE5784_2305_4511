package primitives;

public class Ray {
    final private Point head;
    final private Vector direction;

    Ray(Point head, Vector direction) {
        this.head = head;
        this.direction =  direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head.toString() +
                ", direction=" + direction.toString() +
                '}';
    }
}
