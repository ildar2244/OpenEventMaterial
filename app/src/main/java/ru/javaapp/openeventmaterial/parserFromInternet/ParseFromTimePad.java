package ru.javaapp.openeventmaterial.parserFromInternet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParseFromTimePad {
    int cityId, positionCategory;
    List<Bitmap> bitmapList;

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public Elements getName() {
        return name;
    }

    public Elements getDate() {
        return date;
    }

    public Elements getAddress() {
        return address;
    }

    public Elements getBuy() {
        return buy;
    }

    public Elements getDesscr() {
        return desscr;
    }

    public Elements getImg() {
        return img;
    }

    public Elements getOrganisator() {
        return organisator;
    }

    Elements name, date, address, buy, desscr, img, organisator;
    InputStream inp = null;
    Document doc = null;

    public ParseFromTimePad(int city, int position) {
        this.cityId = city;
        this.positionCategory = position;

        parsing();
    }

    public void parsing() {
        // Астрахань и Категории
        if (cityId == 1 && positionCategory == 0) {
            parse("https://my.timepad.ru/astrahan/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 2) {
            parse("https://my.timepad.ru/astrahan/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 3) {
            parse("https://my.timepad.ru/astrahan/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 4) {
            parse("https://my.timepad.ru/astrahan/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 5) {
            parse("https://my.timepad.ru/astrahan/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 6) {
            parse("https://my.timepad.ru/astrahan/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 8) {
            parse("https://my.timepad.ru/astrahan/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 10) {
            parse("https://my.timepad.ru/astrahan/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 11) {
            parse("https://my.timepad.ru/astrahan/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 13) {
            parse("https://my.timepad.ru/astrahan/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 1 && positionCategory == 14) {
            parse("https://my.timepad.ru/astrahan/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Волгоград и Категории
        if (cityId == 2 && positionCategory == 0) {
            parse("https://my.timepad.ru/volgograd/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 2) {
            parse("https://my.timepad.ru/volgograd/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 3) {
            parse("https://my.timepad.ru/volgograd/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 4) {
            parse("https://my.timepad.ru/volgograd/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 5) {
            parse("https://my.timepad.ru/volgograd/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 6) {
            parse("https://my.timepad.ru/volgograd/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 8) {
            parse("https://my.timepad.ru/volgograd/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 10) {
            parse("https://my.timepad.ru/volgograd/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 11) {
            parse("https://my.timepad.ru/volgograd/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 13) {
            parse("https://my.timepad.ru/volgograd/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 2 && positionCategory == 14) {
            parse("https://my.timepad.ru/volgograd/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Екатеринбург и Категории
        if (cityId == 3 && positionCategory == 0) {
            parse("https://my.timepad.ru/ekaterinburg/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 2) {
            parse("https://my.timepad.ru/ekaterinburg/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 3) {
            parse("https://my.timepad.ru/ekaterinburg/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 4) {
            parse("https://my.timepad.ru/ekaterinburg/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 5) {
            parse("https://my.timepad.ru/ekaterinburg/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 6) {
            parse("https://my.timepad.ru/ekaterinburg/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 8) {
            parse("https://my.timepad.ru/ekaterinburg/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 10) {
            parse("https://my.timepad.ru/ekaterinburg/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 11) {
            parse("https://my.timepad.ru/ekaterinburg/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 13) {
            parse("https://my.timepad.ru/ekaterinburg/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 3 && positionCategory == 14) {
            parse("https://my.timepad.ru/ekaterinburg/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Иркутск и Категории
        if (cityId == 4 && positionCategory == 0) {
            parse("https://my.timepad.ru/irkutsk/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 2) {
            parse("https://my.timepad.ru/irkutsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 3) {
            parse("https://my.timepad.ru/irkutsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 4) {
            parse("https://my.timepad.ru/irkutsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 5) {
            parse("https://my.timepad.ru/irkutsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 6) {
            parse("https://my.timepad.ru/irkutsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 8) {
            parse("https://my.timepad.ru/irkutsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 10) {
            parse("https://my.timepad.ru/irkutsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 11) {
            parse("https://my.timepad.ru/irkutsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 13) {
            parse("https://my.timepad.ru/irkutsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 4 && positionCategory == 14) {
            parse("https://my.timepad.ru/irkutsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Казань и Категории
        if (cityId == 5 && positionCategory == 0) {
            parse("https://my.timepad.ru/kazan/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 2) {
            parse("https://my.timepad.ru/kazan/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 3) {
            parse("https://my.timepad.ru/kazan/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 4) {
            parse("https://my.timepad.ru/kazan/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 5) {
            parse("https://my.timepad.ru/kazan/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 6) {
            parse("https://my.timepad.ru/kazan/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 8) {
            parse("https://my.timepad.ru/kazan/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 10) {
            parse("https://my.timepad.ru/kazan/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 11) {
            parse("https://my.timepad.ru/kazan/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 13) {
            parse("https://my.timepad.ru/kazan/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 5 && positionCategory == 14) {
            parse("https://my.timepad.ru/kazan/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Краснодар и Категории
        if (cityId == 6 && positionCategory == 0) {
            parse("https://my.timepad.ru/krasnodar/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 2) {
            parse("https://my.timepad.ru/krasnodar/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 3) {
            parse("https://my.timepad.ru/krasnodar/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 4) {
            parse("https://my.timepad.ru/krasnodar/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 5) {
            parse("https://my.timepad.ru/krasnodar/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 6) {
            parse("https://my.timepad.ru/krasnodar/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 8) {
            parse("https://my.timepad.ru/krasnodar/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 10) {
            parse("https://my.timepad.ru/krasnodar/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 11) {
            parse("https://my.timepad.ru/krasnodar/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 13) {
            parse("https://my.timepad.ru/krasnodar/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 6 && positionCategory == 14) {
            parse("https://my.timepad.ru/krasnodar/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Москва и Категории
        if (cityId == 7 && positionCategory == 0) {
            parse("https://my.timepad.ru/moskva/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 2) {
            parse("https://my.timepad.ru/moskva/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 3) {
            parse("https://my.timepad.ru/moskva/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 4) {
            parse("https://my.timepad.ru/moskva/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 5) {
            parse("https://my.timepad.ru/moskva/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 6) {
            parse("https://my.timepad.ru/moskva/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 8) {
            parse("https://my.timepad.ru/moskva/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 10) {
            parse("https://my.timepad.ru/moskva/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 11) {
            parse("https://my.timepad.ru/moskva/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 13) {
            parse("https://my.timepad.ru/moskva/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 7 && positionCategory == 14) {
            parse("https://my.timepad.ru/moskva/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Набережные Челны и Категории
        if (cityId == 8 && positionCategory == 0) {
            parse("https://my.timepad.ru/naberezhnye-chelny/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 2) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 3) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 4) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 5) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 6) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 8) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 10) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 11) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 13) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 8 && positionCategory == 14) {
            parse("https://my.timepad.ru/naberezhnye-chelny/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Нижний Новгород и Категории
        if (cityId == 9 && positionCategory == 0) {
            parse("https://my.timepad.ru/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 2) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 3) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 4) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 5) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 6) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 8) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 10) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 11) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 13) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 9 && positionCategory == 14) {
            parse("https://my.timepad.ru/nizhniy-novgorod/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Новосибирск и Категории
        if (cityId == 10 && positionCategory == 0) {
            parse("https://my.timepad.ru/novosibirsk/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 2) {
            parse("https://my.timepad.ru/novosibirsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 3) {
            parse("https://my.timepad.ru/novosibirsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 4) {
            parse("https://my.timepad.ru/novosibirsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 5) {
            parse("https://my.timepad.ru/novosibirsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 6) {
            parse("https://my.timepad.ru/novosibirsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 8) {
            parse("https://my.timepad.ru/novosibirsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 10) {
            parse("https://my.timepad.ru/novosibirsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 11) {
            parse("https://my.timepad.ru/novosibirsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 13) {
            parse("https://my.timepad.ru/novosibirsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 10 && positionCategory == 14) {
            parse("https://my.timepad.ru/novosibirsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Омск и Категории
        if (cityId == 11 && positionCategory == 0) {
            parse("https://my.timepad.ru/omsk/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 2) {
            parse("https://my.timepad.ru/omsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 3) {
            parse("https://my.timepad.ru/omsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 4) {
            parse("https://my.timepad.ru/omsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 5) {
            parse("https://my.timepad.ru/omsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 6) {
            parse("https://my.timepad.ru/omsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 8) {
            parse("https://my.timepad.ru/omsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 10) {
            parse("https://my.timepad.ru/omsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 11) {
            parse("https://my.timepad.ru/omsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 13) {
            parse("https://my.timepad.ru/omsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 11 && positionCategory == 14) {
            parse("https://my.timepad.ru/omsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Санкт-Петербург и Категории
        if (cityId == 12 && positionCategory == 0) {
            parse("https://my.timepad.ru/sankt-peterburg/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 2) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 3) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 4) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 5) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 6) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 8) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 10) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 11) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 13) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 12 && positionCategory == 14) {
            parse("https://my.timepad.ru/sankt-peterburg/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Ульяновск и Категории
        if (cityId == 13 && positionCategory == 0) {
            parse("https://my.timepad.ru/ulyanovsk/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 2) {
            parse("https://my.timepad.ru/ulyanovsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 3) {
            parse("https://my.timepad.ru/ulyanovsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 4) {
            parse("https://my.timepad.ru/ulyanovsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 5) {
            parse("https://my.timepad.ru/ulyanovsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 6) {
            parse("https://my.timepad.ru/ulyanovsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 8) {
            parse("https://my.timepad.ru/ulyanovsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 10) {
            parse("https://my.timepad.ru/ulyanovsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 11) {
            parse("https://my.timepad.ru/ulyanovsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 13) {
            parse("https://my.timepad.ru/ulyanovsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 13 && positionCategory == 14) {
            parse("https://my.timepad.ru/ulyanovsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

        // Челябинск и Категории
        if (cityId == 14 && positionCategory == 0) {
            parse("https://my.timepad.ru/chelyabinsk/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 2) {
            parse("https://my.timepad.ru/chelyabinsk/categories/ekskursii-i-puteshestviya/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 3) {
            parse("https://my.timepad.ru/chelyabinsk/categories/biznes/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 4) {
            parse("https://my.timepad.ru/chelyabinsk/categories/vystavki/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 5) {
            parse("https://my.timepad.ru/chelyabinsk/categories/dlya-detey/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 6) {
            parse("https://my.timepad.ru/chelyabinsk/categories/kontserty/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 8) {
            parse("https://my.timepad.ru/chelyabinsk/categories/it-i-internet/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 10) {
            parse("https://my.timepad.ru/chelyabinsk/categories/psihologiya-i-samopoznanie/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 11) {
            parse("https://my.timepad.ru/chelyabinsk/categories/sport/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 13) {
            parse("https://my.timepad.ru/chelyabinsk/categories/hobbi-i-tvorchestvo/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }
        if (cityId == 14 && positionCategory == 14) {
            parse("https://my.timepad.ru/chelyabinsk/categories/teatry/events?approved=true&date=&mode=&online=false&paid=true");
            return;
        }

    }

    public void parse(String url) {
        String imgSrc;
        bitmapList = new ArrayList<Bitmap>();

        try {
            doc = Jsoup.connect(url).get();
            name = doc.select("h2[class=b-unit__header_size_small b-event__header] > a[href]");
            date = doc.select("span[class=b-unit__text_size_small b-unit__text_color_black]:contains(2015)");
            organisator = doc.select("span[class=b-unit__text_size_small b-unit__text_color_black] > a[href]");
            address = doc.select("span[class=b-unit__text_size_small b-unit__text_color_black]:not(:contains(2015)):not(:has(span a)):not(:contains(руб.))");
            desscr = doc.select("div[class=b-event__info] > p[class = b-unit__text b-event__description]:not(:contains(Зарегистрируйтесь и опубликуйте))");
            buy = doc.select("h2[class=b-unit__header_size_small b-event__header] > a[href]");
            img = doc.select("div[class=b-event__pic] > img[src*=https]");

            if (bitmapList.isEmpty()) {
                for (int i = 0; i < img.size(); i++) {
                    imgSrc = img.get(i).attr("src");
                    inp = new java.net.URL(imgSrc).openStream();
                    bitmapList.add(BitmapFactory.decodeStream(inp));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
