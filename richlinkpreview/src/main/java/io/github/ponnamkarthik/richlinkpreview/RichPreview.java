package io.github.ponnamkarthik.richlinkpreview;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by ponna on 16-01-2018.
 */

public class RichPreview {

    MetaData metaData;
    ResponseListener responseListener;
    String url;

    public RichPreview(ResponseListener responseListener) {
        this.responseListener = responseListener;
        metaData = new MetaData();
    }

    public void getPreview(String url) {
        this.url = url;
        new getData().execute();
    }

    private class getData extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            Document doc = null;
            try {
                doc = Jsoup.connect(url)
                        .timeout(30 * 1000)
                        .ignoreHttpErrors(true)
                        .get();

                Elements elements = doc.getElementsByTag("meta");

                // getTitle doc.select("meta[property=og:title]")
                String title = doc.select("meta[property=og:title]").attr("content");

                if (title == null || title.isEmpty()) {
                    title = doc.title();
                }
                metaData.setTitle(title);

                //getDescription
                String description = doc.select("meta[name=description]").attr("content");
                if (description.isEmpty() || description == null) {
                    description = doc.select("meta[name=Description]").attr("content");
                }
                if (description.isEmpty() || description == null) {
                    description = doc.select("meta[property=og:description]").attr("content");
                }
                if (description.isEmpty() || description == null) {
                    description = "";
                }
                metaData.setDescription(description);


                // getMediaType
                Elements mediaTypes = doc.select("meta[name=medium]");
                String type = "";
                if (mediaTypes.size() > 0) {
                    String media = mediaTypes.attr("content");

                    type = media.equals("image") ? "photo" : media;
                } else {
                    type = doc.select("meta[property=og:type]").attr("content");
                }
                metaData.setMediatype(type);


                //getImages
                Elements imageElements = doc.select("meta[property=og:image]");
                if (imageElements.size() > 0) {
                    String image = imageElements.attr("content");
                    if (!image.isEmpty()) {
                        metaData.setImageurl(resolveURL(url, image));
                    }
                }
                if (metaData.getImageurl().isEmpty()) {
                    String src = doc.select("link[rel=image_src]").attr("href");
                    if (!src.isEmpty()) {
                        metaData.setImageurl(resolveURL(url, src));
                    } else {
                        src = doc.select("link[rel=apple-touch-icon]").attr("href");
                        if (!src.isEmpty()) {
                            //     metaData.setImageurl(resolveURL(url, src));
                            metaData.setFavicon(resolveURL(url, src));
                        } else {
                            src = doc.select("link[rel=icon]").attr("href");
                            if (!src.isEmpty()) {
                                //         metaData.setImageurl(resolveURL(url, src));
                                metaData.setFavicon(resolveURL(url, src));
                            }
                        }
                    }
                }

                //Favicon
                String src = doc.select("link[rel=apple-touch-icon]").attr("href");
                if (!src.isEmpty()) {
                    metaData.setFavicon(resolveURL(url, src));
                } else {
                    src = doc.select("link[rel=icon]").attr("href");
                    if (!src.isEmpty()) {
                        metaData.setFavicon(resolveURL(url, src));
                    }
                }

                for (Element element : elements) {
                    if (element.hasAttr("property")) {
                        String str_property = element.attr("property").trim();
                        if (str_property.equals("og:url")) {
                            Log.d("Url:", element.attr("content"));
                            metaData.setUrl(element.attr("content"));
                        }
                        if (str_property.equals("og:site_name")) {
                            Log.d("SiteName:", element.attr("content"));
                            metaData.setSitename(element.attr("content"));
                        }
                    }
                }

                if (metaData.getUrl().equals("") || metaData.getUrl().isEmpty()) {
                    URI uri = null;
                    try {
                        uri = new URI(url);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if (uri == null) {
                        metaData.setUrl(url);
                    } else {
                        metaData.setUrl(uri.getHost());
                    }
                }
                setBaseUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
                responseListener.onError(new Exception("No Html Received from " + url + " Check your Internet " + e.getLocalizedMessage()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            responseListener.onData(metaData);
        }
    }

    private void setBaseUrl(String url) {
        try {
            URI uri = new URI(url);
            metaData.setBaseUrl(uri.getHost());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String resolveURL(String url, String part) {
        if (URLUtil.isValidUrl(part)) {
            return part;
        } else {
            URI base_uri = null;
            try {
                base_uri = new URI(url);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            base_uri = base_uri.resolve(part);
            return base_uri.toString();
        }
    }

}
