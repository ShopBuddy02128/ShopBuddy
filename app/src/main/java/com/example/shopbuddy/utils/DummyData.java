package com.example.shopbuddy.utils;

public abstract class DummyData {
    public static String[] imageUrls = {"https://dsdam.imgix.net/services/assets.img/id/6e19999e-aea3-45b5-93f6-755cff2adf9c/size/FULLSCALE.jpg?w=500&auto=format&q=60&h=500&fit=fill&fill=solid&fill-color=ffffff&trim=color&&trim-tol=10",
            "https://www.naturoghelse.dk/images/Havredrik-Naturli-1-liter-%C3%B8kologisk-p.png",
            "https://dsdam.imgix.net/services/assets.img/id/a7689c4c-c228-47c8-9fdd-a63be286e356/size/FULLSCALE.jpg?w=500&auto=format&q=60&h=500&fit=fill&fill=solid&fill-color=ffffff&trim=color&&trim-tol=10",
            "https://www.benjerry.dk/files/live/sites/systemsite/files/flavors/products/eu/pints/harmony/cookie-dough-detail.png",
            "https://www.markys.com/images/shop/product/2777/010101-markys-beluga-caviar-huso-huso--m10907436-original.jpg"};
    public static String[] names = {"Øko smør", "Havredrik", "Lasagneplader", "Cookie dough", "Kaviar"};
    public static String[] brands = {"Arla", "Naturli", "Il Fornaio", "Ben & Jerry's", "Beluga"};
    public static String[] prices = {"11,95 kr", "13,95 kr","19,95 kr","64,95 kr","2691,90 kr"};
    public static String[] qtys = {"2","3","1","1337","69"};

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
