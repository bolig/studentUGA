package com.peoit.android.studentuga.config;

/**
 * 应用配置文件
 * <p>
 * author:libo
 * time:2015/9/18
 * E-mail:boli_android@163.com
 * last: ...
 */
public class NetConstants {

    /**
     * 数据操作类型
     */
    public final static String RESPONSE_NORMAL = "RESPONSE_NORMAL"; // ----------------- 若为此数据操作类型, 返回数据自定义解析
    public final static String RESPONSE_NORMAL_STRING = "RESPONSE_NORMAL_STRING"; // ----若为此数据操作类型, 返回数据自定义解析
    public final static String RESPONSE_MODEL = "RESPONSE_MODEL"; // ------------------- 若为此数据操作类型, 返回数据将被解析为单个实体
    public final static String RESPONSE_LIST_MODEL = "RESPONSE_LIST_MODEL"; // --------- 若为此数据操作类型, 返回数据将被解析为实体集合
    public final static String RESPONSE_LIST_MODEL_PAGE = "RESPONSE_LIST_MODEL_PAGE"; // 若为此数据操作类型, 返回数据将被解析为实体集合, 并带分页...

//    public final static String HOST = "http://120.24.165.95:8081/cyshop/api/"; // ------ 域名
    public final static String HOST = "http://123.57.240.52:8080/cyshop/api/"; // ------ 域名

//    public final static String IMG_HOST = "http://120.24.165.95:8081"; // ------ 域名
    public final static String IMG_HOST = "http://123.57.240.52:8080/"; // ------ 域名

    public final static String $_ADD_$ = "_ADD_"; // --------------------------------- 请求连接符

    /**
     * 服务端接口(请求方式(默认为post) + 路径 + 返回数据类(默认为带个实体))
     */
    public final static String NET_LOGIN = HOST + "login.do"; // ----------------------------------- 登录接口

    public final static String NET_QUERY_USERINFO_BYID = HOST + "queryByuid.do"; // 查询用户信息

    public final static String NET_QUERY_SCHOOL_INFO = HOST + "queryAreaInfos.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // -------------- 查询学校信息

    public final static String NET_SEND_CODE = HOST + "sendCode.do"; // ---------------------------- 发送验证码

    public final static String NET_CHECK_CODE = HOST + "checkCode.do"; // -------------------------- 检查验证码

    public final static String NET_REGISTOR = HOST + "Register.do"; // ----------------------------- 注册接口

    public final static String NET_UPLOAD_FILE = HOST + "uploadImg.do" + $_ADD_$ + RESPONSE_NORMAL_STRING; //  上传图片

    public final static String NET_MODIFY_NICKNAME = HOST + "updatename.do";  // 修改昵称

    public final static String NET_MODIFY_USERSIGN = HOST + "updateQm.do";  // 修改签名

    public final static String NET_ADDRESS_UPDATA = HOST + "updateAddress.do"; // 修改收货地址

    public final static String NET_ADDRESS_DEL = HOST + "delAddress.do"; // 删除收货地址

    public final static String NET_ADDRESS_ADD = HOST + "addAddress.do"; // 添加收货地址

    public final static String NET_DEFAULT_ADDRESS = HOST + "queryAddressDefault.do"; // 添加收货地址

    public final static String NET_ADDRESS_QUERY_LIST = HOST + "queryAddress.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 请求收货地址列表

    public final static String NET_LOGINPASS_MOD = HOST + "updatepassword.do"; // 修改登录密码

    public final static String NET_LOGINPASS_FIND = HOST + "findpassword.do";  // 找回登录密码

    public final static String NET_PAYPASS_SETUP = HOST + "setpaypassword.do"; // 设置支付密码

    public final static String NET_PAYPASS_MOD = HOST + "updatepaypassword.do";// 修改支付密码

    public final static String NET_PAYPASS_FIND = HOST + "updatepaypassword.do";// 找回支付密码

    public final static String NET_GOODS_TYPE = HOST + "queryType.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 获取商品类型列表

    public final static String NET_GOODS_LIST = HOST + "queryProduct.do" + $_ADD_$ + RESPONSE_LIST_MODEL; //获取某个类型的商品

    //public final static String NET_QUERYPRODUCT_LIST = HOST + "queryProduct.do"; //获取商品列表
    public final static String NET_QUERYUSERSTU_LIST = HOST + "queryUserStu.do"+$_ADD_$ + RESPONSE_LIST_MODEL; //查询学生信息列表

    public final static String NET_GOODS_DET = HOST + "queryProductByid.do"; // 获取商品详情

    public final static String NET_GOODS_ADDSHOPCAR = HOST + "addShopCar.do"; // 添加商品到购物车

    public final static String NET_GOODS_UPDATASHOPCAR = HOST + "updateShopCar.do"; // 修改购物车

    public final static String NET_GOODS_DELSHOPCAR = HOST + "delShopCar.do"; // 删除购物车

    public final static String NET_GOODS_QUERYSHOPCAR = HOST + "queryShopCars.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 查询购物车

    public final static String NET_AT_ONCE_ORDER = HOST + "addPayOrder.do"; // 直接购买的订单

    public final static String NET_ADD_SHOPCAR_ORDER = HOST + "addPayShopOrder.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 生成购物车订单

    public final static String NET_PAY_ORDER = HOST + "payOrder.do"; //支付接口

    public final static String NET_QUERY_HOLD_ORDER = HOST + "queryOrder.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 获取用户购买商品的订单列表

    public final static String NET_QUERY_SACLE_MY_ORDER = HOST + "queryOrderZy.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 获取用户自己售卖的商品的订单列表

    public final static String NET_QUERY_SACLE_HE_ORDER = HOST + "queryOrderDx.do" + $_ADD_$ + RESPONSE_LIST_MODEL; // 获取用户代销的商品的订单列表

    public final static String NET_ORDER_CANCEL = HOST + "delOrder.do"; // 取消订单

    public final static String NET_ORDER_FINISH = HOST + "overOrder.do"; // 确认收货完成订单

    public final static String NET_QUERY_USER = HOST + "queryByuidNoSign.do"; //查询用户(不需要账号和签名，用于未登陆的情况)

    public final static String NET_QUERY_TRADE_LOG = HOST + "querystreams.do" + $_ADD_$ + RESPONSE_LIST_MODEL; //交易流水

    public enum Response_ {
        NORMAL(RESPONSE_NORMAL),
        NORMAL_STRING(RESPONSE_NORMAL_STRING),
        MODEL(RESPONSE_MODEL),
        LIST_MODEL(RESPONSE_LIST_MODEL),
        LIST_MODEL_PAGE(RESPONSE_LIST_MODEL_PAGE);
        private final String mResponse;

        Response_(String response) {
            this.mResponse = response;
        }

        public Response_ getResponse_(String response) {
            for (Response_ res :
                    Response_.values()) {
                if (res.equals(response))
                    return res;
            }
            return NORMAL;
        }

        public boolean equals(String response) {
            return mResponse.equals(response);
        }
    }
}
