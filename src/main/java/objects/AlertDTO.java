package objects;

public class AlertDTO {

    private String id;
    private String desc;
    private boolean hasRfi;



    public AlertDTO() {

    }


    public AlertDTO(String id, String desc, boolean hasRfi) {
        this.id = id;
        this.desc = desc;
        this.hasRfi = hasRfi;

    }


    public AlertDTO(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isHasRfi() {
        return hasRfi;
    }

    public void setHasRfi(boolean hasRfi) {
        this.hasRfi = hasRfi;
    }

}