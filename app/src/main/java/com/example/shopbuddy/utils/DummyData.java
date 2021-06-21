package com.example.shopbuddy.utils;

import com.example.shopbuddy.models.ShopListItem;

import java.util.ArrayList;

public abstract class DummyData {
    public static ArrayList<ShopListItem> getDummyItems() {
        ArrayList<ShopListItem> items = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            items.add(new ShopListItem(
                    names[i],
                    brands[i],
                    prices[i],
                    qtys[i],
                    imageUrls[i],
                    "dummy"
            ));
        }

        return items;
    }

    public static String[] names = {"blåbær","skrabeæg","hamburgerryg","løg","bacon","hakket oksekød 8-12%","øl","øl","øl","kaffe","tomatpuré","pommes frites","kartofler","chips","cola 0,33 cl","spareribs","sukker","hvedemel","æbler","fløde","isbar","roast beef","bønne spread","kirsebær","skyr vanilje","rævesovs","skyr naturel","toiletpapir","model S","ananas","vandmelon","chips"};
    public static String[] brands = {"Driscolls","Dava","Xtra","Levevis","Tulip","Coop","Harboe","Tuborg","Carlsberg","Nescafé","Salling","Vores","Nye Danske","Kims","Coca Cola","Jensens","Dan Sukker","Budget","Pink Lady","Arla","Mars","Slagterens","Naturli","Danske","Cheasy","Slagterens","Cheasy","Lambi","Tesla","Budget","Budget","Nettos"};
    public static String[] prices = {"20.95","21.95","13.95","8.95","34.95","44.95","4.95","7.95","7.95","50.95","19.95","12.95","8.95","22.95","7.95","34.95","11.95","6.95","11.95","22.95","14.95","38.95","14.95","27.95","18.95","34.95","16.95","37.95","1195000.00","17.95","12.95","6.95"};
    public static String[] qtys = {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
    public static String[] imageUrls = {"https://live.nemligstatic.com/scommerce/images/blaabaer.jpg?i=XLzti3Wb/5049874&w=623&h=623&mode=pad",
            "https://live.nemligstatic.com/scommerce/images/skrabeaeg-str-m-l.jpg?i=ZHJDoFwV/5003310",
            "https://www.kodbilen.dk/wp-content/uploads/2018/10/%C3%98kologisk-hamburgerryg.jpg",
            "https://frugtkurven.dk/img/QCnBMB3.png",
            "https://www.bestiloghent.dk/images/media/Products/374-8514.jpg",
            "https://osuma.dk/custgfx/5700381638551.jpg",
            "http://4.bp.blogspot.com/-mkIG3wnAeo8/UY_PDQrcQ1I/AAAAAAAADcc/oajmbfWVhVU/s320/Harboe+Pilsner.jpg",
            "https://www.bevco.dk/media/catalog/product/cache/1/image/1000x/9df78eab33525d08d6e5fb8d27136e95/0/0/00000057008004_c1n1.png",
            "https://www.bevco.dk/media/catalog/product/cache/1/image/360x360/0dc2d03fe217f8c83829496872af24a0/s/k/sk_rmbillede_2020-07-21_kl._09.32.31.png",
            "https://www.papirladen.dk/images/billeder/3358-p.jpg",
            "https://dsdam.imgix.net/services/assets.img/id/348aa049-46ed-4503-8b34-ecf6091742af/size/FULLSCALE.jpg?w=500&auto=format&q=40&h=500&fit=fill&fill=solid&fill-color=ffffff&trim=color&&trim-tol=10",
            "http://www.testuniverset.dk/wp-content/uploads/2017/02/Vores.jpg",
            "https://drupaller-prod.imgix.net/letliv/s3fs-public/media/spis_bare_kartofler.jpg?ixlib=imgixjs-3.4.0",
            "https://www.kims.dk/wp-content/uploads/2019/03/5650007844-KiMs-Chips-Sour-Cream-Onion-20-x-175g-1-240x370.png",
            "https://www.esbjergøl.dk/wp-content/uploads/2020/10/coca-cola-daase.jpg",
            "https://www.jensensfoods.dk/wp-content/uploads/2019/04/Spareribs-425x330.png",
            "https://www.netpris.dk/media/catalog/product/cache/14/image/330x/9df78eab33525d08d6e5fb8d27136e95/d/a/dan_sukker.jpg",
            "https://dsdam.imgix.net/services/assets.img/id/310777de-c5a1-46e8-9d29-002ff14b648c/size/FULLSCALE.jpg?w=500&auto=format&q=40&h=500&fit=fill&fill=solid&fill-color=ffffff&trim=color&&trim-tol=10",
            "https://www.bestiloghent.dk/images/media/Products/322-7294.jpg",
            "https://d7vyj4bt6i7jd.cloudfront.net/products/5790000000876/05711953077746/05711953077746_103249_1602201112.png",
            "https://hjem-is.dk/wp-content/uploads/2020/04/Mars-Ice-Bar_2020-300x300.png",
            "https://emmamartiny.dk/wp-content/uploads/2019/11/Roastbeef.jpg",
            "https://jensdrejer.files.wordpress.com/2012/10/bc3b8nne-spread-001.jpg?w=640",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Cherry_Stella444.jpg/220px-Cherry_Stella444.jpg",
            "https://www.fleggaard.dk/Services/ImageHandler.ashx?imgId=1933373&sizeId=0",
            "https://dalsgaardiskivholme.files.wordpress.com/2015/06/billede116.jpg",
            "https://images.arla.com/filename/05760466996686_C1N1.PNG?width=400&height=500&mode=max",
            "https://dyncdn.thg.dk/img/350010011_0_m_719_1200.JPG",
            "https://www.tesla.com/sites/default/files/images/blogs/p100d_social.jpg",
            "https://www.floristik24.dk/media/images/popup/Ananas_kuenstlich_21cm_Gelb_805826.jpg",
            "https://madkurven.dk/wp-content/uploads/vandmelon5.png",
            "https://cdn.spiir.dk/blog/content/images/2018/10/IMG_1628.jpg"};

    public static String[] listNames = {"Magnus' liste", "Emilies liste", "Amalies liste", "Niels liste", "Massimos liste"};

    public static String jsonExample = "[" +
            "{" +
            "\"clearances\": [" +
            "{" +
            "\"offer\": {" +
            "\"currency\": \"DKK\"," +
            "\"discount\": 6," +
            "\"ean\": \"20002275\"," +
            "\"endTime\": \"2019-11-14T23:00:00.000Z\"," +
            "\"lastUpdate\": \"2019-10-28T15:34:39.000Z\"," +
            "\"newPrice\": 18," +
            "\"originalPrice\": 22," +
            "\"percentDiscount\": 18.18," +
            "\"startTime\": \"2019-10-18T08:41:17.000Z\"," +
            "\"stock\": 88," +
            "\"stockUnit\": \"each\"" +
            "}," +
            "\"product\": {" +
            "\"description\": \"WASA HUSMAN 520 G\"," +
            "\"ean\": \"5710405175931\"," +
            "\"image\": \"https://dam.dsg.dk/services/assets.img/id/24b501d6-7c9f-42ed-bb7a-9b932f49d46d/size/WEB1024x1024.jpg\"" +
            "}" +
            "}" +
            "]," +
            "\"store\": {" +
            "\"address\": {" +
            "\"city\": \"København K\"," +
            "\"country\": \"DK\",\n" +
            "\"street\": \"Vimmelskaftet 45\"," +
            "\"zip\": \"1161\"" +
            "},\n" +
            "\"brand\": \"bilka\"," +
            "\"coordinates\": [" +
            "10.104849," +
            "56.182413" +
            "]," +
            "\"hours\": [" +
            "{" +
            "\"close\": \"2019-04-04T18:00:00\"," +
            "\"closed\": false," +
            "\"date\": \"2019-04-04\"," +
            "\"open\": \"2019-04-04T10:00:00\"," +
            "\"type\": \"store\"" +
            "}\n" +
            "],\n" +
            "\"name\": \"Bilka Esbjerg\",\n" +
            "\"id\": \"efba0457-090e-4132-81ba-c72b4c8e7fee\",\n" +
            "\"type\": \"Point\"\n" +
            "}\n" +
            "}\n" +
            "]";
}
