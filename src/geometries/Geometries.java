package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        for(Intersectable body : geometries){
            this.geometries.add(body);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> listOfPoint = null;
        List<Point> temp;

        for (var body : geometries) {
            temp = body.findIntersections(ray);
            if (temp != null) {
                if (listOfPoint == null)
                    listOfPoint = new ArrayList<>(temp);
                else
                    listOfPoint.addAll(temp);
            }
        }
        return listOfPoint;
    }
}
