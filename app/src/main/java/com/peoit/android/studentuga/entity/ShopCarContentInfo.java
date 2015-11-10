package com.peoit.android.studentuga.entity;

import java.io.Serializable;

/**
 * author:libo
 * time:2015/9/11
 * E-mail:boli_android@163.com
 * last: ...
 */
public class ShopCarContentInfo implements Serializable {

    private String realPrice = "8888.88";

    private String curentPrice = "6666.66";

    private String imageUrl = "http://img0.imgtn.bdimg.com/it/u=92564131,2407175771&fm=21&gp=0.jpg";

    private int shopCount = 1;

    private boolean isSelect = false;

    private String shopTitle = "AMX—13坦克";

    private String shopContent = "轻型坦克重13～23.5吨，乘员3～4人，火炮口径为75～100毫米，炮塔装甲最大厚度20～50毫米，发动机功率176～368千瓦，单位功率12.6～16千瓦/吨，最大速度50～64千米/时，最大行程260～350千米。PT—76坦克在水上使用喷水式推进装置，最大航行速度为10.2千米/时";

    /**
     * 增加商品数量
     */
    public void toAddShopCount() {
        ++shopCount;
    }

    /**
     * 减少商品的数量，并判断数量是否为零
     *
     * @return
     */
    public boolean toMinusShopCountAndCountIsZero() {
        --shopCount;
        return shopCount <= 0;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getCurentPrice() {
        return curentPrice;
    }

    public void setCurentPrice(String curentPrice) {
        this.curentPrice = curentPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public String getShopContent() {
        return shopContent;
    }

    public void setShopContent(String shopContent) {
        this.shopContent = shopContent;
    }
}
