package acme.features.administrator.banner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acme.entities.banner.Banner;

@Component
public class BannerUtility {

    @Autowired AdministratorBannerRepository repo;

    public void loadAllBanners() {

        try {

            String dirPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\META-INF\\resources\\banners-data\\";

            Set<String> files = Stream.of(new File(dirPath).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());

            for (String f : files) {
                if (!f.contains("ids")) {
                    String path = System.getProperty("user.dir") + "\\src\\main\\webapp\\META-INF\\resources\\banners-data\\" + f;
                    File file = new File(path);
                    file.delete();
                }
            }
      
            String idPath = System.getProperty("user.dir") + "\\src\\main\\webapp\\META-INF\\resources\\banners-data\\banners-ids.txt";
    
            PrintWriter writer = new PrintWriter(idPath);
            writer.print("");
            writer.close();
        
            for (Banner banner : repo.findAllBanners()) {
                writeBannerData(banner);
            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing banner files");
            e.printStackTrace();
        }

    }
    
    public void writeBannerData(Banner banner) {

        try {
            writeBannerFile(banner);
            writeBannerIds(banner);
        } catch (IOException e) {
            System.out.println("An error occurred while writing banner files");
            e.printStackTrace();
        }
    }

    private void writeBannerFile(Banner banner) throws IOException {
        String filename = "banner-" + banner.getId();
        String path = System.getProperty("user.dir") + "\\src\\main\\webapp\\META-INF\\resources\\banners-data\\" + filename + ".txt";

        File file = new File(path);
        file.createNewFile();

        PrintWriter writer = new PrintWriter(path);

        writer.print(banner.getSlogan() + "," + banner.getLinkWeb() + "," + banner.getLinkPicture());

        writer.close();
    }

    private void writeBannerIds(Banner banner) throws IOException {

        String path = System.getProperty("user.dir") + "\\src\\main\\webapp\\META-INF\\resources\\banners-data\\banners-ids.txt";

        FileReader readStream = new FileReader(path);
        BufferedReader reader = new BufferedReader(readStream);

        Integer id = banner.getId();
        String txt = reader.readLine();
        if (txt == null) {

            reader.close();

            PrintWriter writer = new PrintWriter(path);
    
            writer.print(id.toString() + ",");

            writer.close();

        } else if (!txt.contains(id.toString())) {

            reader.close();

            PrintWriter writer = new PrintWriter(path);
    
            writer.print(txt + id.toString() + ",");

            writer.close();

        } else {
            reader.close();
        }
    }
}
