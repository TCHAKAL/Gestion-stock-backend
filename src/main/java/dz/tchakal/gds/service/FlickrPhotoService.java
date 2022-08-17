package dz.tchakal.gds.service;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface FlickrPhotoService {

    String savePhoto(InputStream photo , String titre) throws FlickrException;
}
