package drinkMachine;
import java.io.Serializable;

public class ItemBean implements Serializable
{
    private String code;
    private String name;
    private String price;
    private String count;
    private String isPR;
    private String image;
    private String isSoldout;
    private String hot;
    private String cool;

    public ItemBean()
    {
        code      = "";
        name      = "";
        price     = "";
        count     = "";
        isPR      = "";
        image     = "";
        isSoldout = "";
    }

    public String getHot()
    {
        return hot;
    }

    public void setHot(String hot)
    {
        this.hot = hot;
    }

    public String getCool()
    {
        return cool;
    }

    public void setCool(String cool)
    {
        this.cool = cool;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }

    public String getIsPR()
    {
        return isPR;
    }

    public void setIsPR(String isPR)
    {
        this.isPR = isPR;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getIsSoldout()
    {
        return isSoldout;
    }

    public void setIsSoldout(String isSoldout)
    {
        this.isSoldout = isSoldout;
    }
}
