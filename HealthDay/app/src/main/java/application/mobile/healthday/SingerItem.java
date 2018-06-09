package application.mobile.healthday;

public class SingerItem {

    String name;
    String Id;
    int resId;


    public SingerItem(String name, String Id, int resId) {
        this.name = name;
        this.Id = Id;
        this.resId = resId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String mobile) {
        this.Id = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId(){ return resId; }

    public void setResId(int resId){ this.resId = resId; }
}