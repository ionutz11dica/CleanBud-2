package ro.disertatie.cleanbud.View.Models.ApiModels.Weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("cod")
    int cod = 0;
    @SerializedName("name")
    String name = null;
    @SerializedName("id")
    int ıd = 0;
    @SerializedName("timezone")
    int timezone = 0;
    @SerializedName("sys")
    Sys sys = null;
    @SerializedName("dt")
    int dt = 0;
    @SerializedName("clouds")
    Clouds clouds = null;
    @SerializedName("wind")
    Wind wind = null;
    @SerializedName("visibility")
    int visibility = 0;
    @SerializedName("main")
    Main main = null;
    @SerializedName("base")
    String base = null;
    @SerializedName("weather")
    List<Weather> weather = null;
    @SerializedName("coord")
    Coord coord = null;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return ıd;
    }

    public void setId(int ıd) {
        this.ıd = ıd;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public class Sys {
        @SerializedName("sunset")
        long sunset = 0;
        @SerializedName("sunrise")
        long sunrise = 0;
        @SerializedName("country")
        String country = null;
        @SerializedName("id")
        int ıd = 0;
        @SerializedName("type")
        int type = 0;

        public long getSunset() {
            return sunset;
        }

        public void setSunset(long sunset) {
            this.sunset = sunset;
        }

        public long getSunrise() {
            return sunrise;
        }

        public void setSunrise(long sunrise) {
            this.sunrise = sunrise;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getId() {
            return ıd;
        }

        public void setId(int ıd) {
            this.ıd = ıd;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

   public class Clouds {
        @SerializedName("all")
        int all = 0;

       public int getAll() {
           return all;
       }

       public void setAll(int all) {
           this.all = all;
       }
   }

    public class Wind {
        @SerializedName("deg")
        int deg = 0;
        @SerializedName("speed")
        double speed = 0.0;

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    public class Main {
        @SerializedName("humidity")
        int humidity = 0;
        @SerializedName("pressure")
        int pressure = 0;
        @SerializedName("temp_max")
        double tempMax = 0.0;
        @SerializedName("temp_min")
        double tempMin = 0.0;
        @SerializedName("feels_like")
        double feelsLike = 0.0;
        @SerializedName("temp")
        double temp = 0.0;

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public double getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

   public class Weather {
        @SerializedName("icon")
        String icon = null;
        @SerializedName("description")
        String description = null;
        @SerializedName("main")
        String main= null;
        @SerializedName("id")
        int ıd = 0;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public int getId() {
            return ıd;
        }

        public void setId(int ıd) {
            this.ıd = ıd;
        }
    }

    public  class Coord {
        @SerializedName("lat")
        double lat = 0.0;
        @SerializedName("lon")
        double lon = 0.0;

    }
}