
//credits to https://stackoverflow.com/questions/14446447/how-to-read-a-local-text-file-in-the-browser
function readTextFile(file)
{
    var res = null;
    var rawFile = new XMLHttpRequest();
    rawFile.open("GET", file, false);
    rawFile.onreadystatechange = function ()
    {
        if(rawFile.readyState === 4)
        {
            if(rawFile.status === 200 || rawFile.status == 0)
            {
                var allText = rawFile.responseText;
                res = allText;
            }
        }
    }
    rawFile.send(null);

    return res;
}

function chooseBanner(bannerIds) {

    var bannerIdsArray = bannerIds.split(',');

    var n = Math.floor(Math.random() * (bannerIdsArray.length - 1));
    if (n == bannerIdsArray.length) {
        n = 0;
    }

    return readTextFile("/acme-l3-d01/banners-data/banner-" + bannerIdsArray[n] + ".txt");

}

function updateBannerHtml(bannerData) {

    var dataArray = bannerData.split(',');
    var slogan = dataArray[0];
    var link = dataArray[1];
    var img = dataArray[2];

    if (img == 'null') {
        img = "images/banner.png";
    }

    document.getElementById("banner-text-box").innerHTML += 
        "<h1 style='color: white; font-size: 30px; text-align: center; word-break: break-all;'>" + slogan + "</h1>"
    
    if (link != 'null') {
        document.getElementById("banner-text-box").innerHTML += 
            "<a style='color: white; font-size: 20px; text-align: center; display: block;' href='" + link + "'>" + link + "</a>"
    }

    document.getElementById("banner-img").src = img;

}

function showDefaultBannerWarning() {

    document.getElementById("banner-text-box").innerHTML += 
    "<h1 style='color: white; font-size: 30px; text-align: center;'>No banner loaded</h1>"

}


var txt = readTextFile("/acme-l3-d01/banners-data/banners-ids.txt");

if (txt) {
    updateBannerHtml(chooseBanner(txt));
} else {
    showDefaultBannerWarning();
}
