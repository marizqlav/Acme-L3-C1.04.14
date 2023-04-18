package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.banner.Banner;

@ControllerAdvice
public class BannerAdvisor {
    
    @Autowired
    protected BannerRepository repo;

    @ModelAttribute("banner")
    public Banner getBanner() {
        Banner res = null;

        try {
            res = repo.findRandomBanner();
        } catch (Throwable e) { }
        
        return res;
    }

}
