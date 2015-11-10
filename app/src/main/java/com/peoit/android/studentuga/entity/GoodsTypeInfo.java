package com.peoit.android.studentuga.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author:libo
 * time:2015/10/10
 * E-mail:boli_android@163.com
 * last: ...
 */
public class GoodsTypeInfo implements Serializable {
    /**
     * id : 1
     * typeVos : [{"id":2,"typeVos":[],"flg":"Y","name":"科幻","img":"/upImg/201509301010432196522.jpg","pid":1},{"id":3,"typeVos":[],"flg":"Y","name":"网络小说","img":"/upImg/201509301010432196522.jpg","pid":1},{"id":8,"typeVos":[],"flg":"Y","name":"现代美食","img":"/upImg/201510071008118355864.jpg","pid":1}]
     * flg : Y
     * name : 图书
     * img : /upImg/201510071704052253910.jpg
     * pid : 0
     */
    private int id;
    private List<TypeVos> typeVos;
    private String flg;
    private String name;
    private String img;
    private int pid;

    public void setId(int id) {
        this.id = id;
    }

    public void setTypeVos(List<TypeVos> typeVos) {
        this.typeVos = typeVos;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public List<TypeVos> getTypeVos() {
        return typeVos;
    }

    public String getFlg() {
        return flg;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public int getPid() {
        return pid;
    }

    @Override
    public String toString() {
        return "GoodsTypeInfo{" +
                "id=" + id +
                ", typeVos=" + typeVos +
                ", flg='" + flg + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", pid=" + pid +
                '}';
    }

    public static class TypeVos implements Serializable {
        /**
         * id : 2
         * typeVos : []
         * flg : Y
         * name : 科幻
         * img : /upImg/201509301010432196522.jpg
         * pid : 1
         */
        private int id;
        private List<?> typeVos;
        private String flg;
        private String name;
        private String img;
        private int pid;

        public void setId(int id) {
            this.id = id;
        }

        public void setTypeVos(List<?> typeVos) {
            this.typeVos = typeVos;
        }

        public void setFlg(String flg) {
            this.flg = flg;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getId() {
            return id;
        }

        public List<?> getTypeVos() {
            return typeVos;
        }

        public String getFlg() {
            return flg;
        }

        public String getName() {
            return name;
        }

        public String getImg() {
            return img;
        }

        public int getPid() {
            return pid;
        }

        @Override
        public String toString() {
            return "TypeVosEntity{" +
                    "id=" + id +
                    ", typeVos=" + typeVos +
                    ", flg='" + flg + '\'' +
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    ", pid=" + pid +
                    '}';
        }
    }
}
