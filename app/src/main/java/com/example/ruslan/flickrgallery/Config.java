package com.example.ruslan.flickrgallery;

public class Config {

    public final static boolean DEBUD_MODE = true;
    //public final static String PROJECT_NAME = "AndroidDemo";
    public final static String PROJECT_NAME = "FlickrGallery";

    public final static String FLICKR_KEY = "50006c3b65583abc031a89524d35ee5d";
    public final static String FLICKR_SCERT = "69c7fb478fc3a158";

    public final static int MEMORY_CACHE_SIZE = 5 * 1024 * 1024;

    //	public final static String USER_ID = "38039613@N08";
    //public final static String API_URL = "https://api.flickr.com/services/rest/?method=%s&api_key=%s&user_id=%s&format=json&nojsoncallback=1";
    public final static String API_URL = "https://api.flickr.com/services/rest/?method=%s&api_key=%s&format=json&nojsoncallback=1";

    //public final static String FETCH_IMAGE = "flickr.people.getPublicPhotos";
    public final static String FETCH_IMAGE = "flickr.photos.getRecent";

    /* http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg */
    public final static String IMAGE_URL = "http://farm%s.staticflickr.com/%s/%s_%s_m.jpg";
}

