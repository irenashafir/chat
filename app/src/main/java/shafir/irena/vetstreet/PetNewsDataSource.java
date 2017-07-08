package shafir.irena.vetstreet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by irena on 19/06/2017.
 */

public class PetNewsDataSource {

    private static final String ARG_URL_LATEST = "url latest news";
    private static final String ARG_URL = "url";

    public interface onLatestNewsArrivedListener{
        public void onLatestNewsArrived(List<petNews> data, Exception e);
    }


    public static void getNews(final onLatestNewsArrivedListener listener, final String address){
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(address);
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    String xml = StreamIO.read(in, "utf-8");
                    List<petNews> petNewsList = null;
                        petNewsList = parse(xml);
                    listener.onLatestNewsArrived(petNewsList, null);

                } catch (Exception e) {
                    listener.onLatestNewsArrived(null, e);
                }
            }
        });
    }


    private static List<petNews> parse(String xml) {
        List<petNews> data = new ArrayList<>();
        Document document = Jsoup.parse(xml);
        Elements items = document.getElementsByTag("item");

        for (Element e : items) {
            String title = e.getElementsByTag("title").get(0).text();
            String link = e.getElementsByTag("guid").get(0).text();
            String description = e.getElementsByTag("description").get(0).text();

            String image;

            if (e.getElementsByTag("enclosure").attr("url") != null) {
                image = e.getElementsByTag("enclosure").attr("url");
            }
            else {
                image = String.valueOf(R.mipmap.vetstreet_logo);
            }


            petNews ln = new petNews(title, link, description, image);
            data.add(ln);
        }
        return data;
    }
    public static class petNews {
    private String title;
        private String link;
        private String description;
        private String image;


        public petNews(String title, String link, String description, String image) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        @Override
        public String toString() {
            return "petNews{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", description='" + description + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }


}
