package se.fearless.salix;

import javafx.geometry.Point3D;

public interface EntryView<T> {
    T getValue();

    Point3D getPosition();

    double getRadius();
}
