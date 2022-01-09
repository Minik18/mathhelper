package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.ImageNotFoundException;

import java.awt.*;

public interface PicturesDAO {

    Image loadPicture(String name) throws ImageNotFoundException;

}
