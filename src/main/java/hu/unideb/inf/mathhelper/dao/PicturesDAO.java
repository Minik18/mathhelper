package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.exception.ImageNotFoundException;
import javafx.scene.image.Image;

public interface PicturesDAO {

    Image loadPicture(String name) throws ImageNotFoundException;

}
