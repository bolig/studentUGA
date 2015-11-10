package com.peoit.android.studentuga.alipays;

import android.app.Activity;

/**
 * 支付宝支付接口
 * 
 * @author libo
 * @time 2015/4/25
 */
public class AliPayServer extends AliPayBase{
	/**
	 * 支付宝及时支付
	 * 
	 * @param order_id
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 * @param l           
	 */
	public static void toPay(Activity mAc, String order_id, String good_name, String good_det,
			String good_price, OnPayListener l){
		String order = AliPayBuilder.createAliPayOrder(order_id, good_name, good_det, good_price).toSign();
		pay(mAc, order, l);
	}
	
	/**
	 * 支付宝及时支付
	 * 
	 * @param order_id
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 * @param l           
	 */
	public static void toPay(Activity mAc, String order_id, String good_name, String good_det,
			String good_price){
		String order = AliPayBuilder.createAliPayOrder(order_id, good_name, good_det, good_price).toSign();
		pay(mAc, order, null);
	}

	
	/**
	 * 支付宝充值
	 * 
	 * @param
	 *            // 订单号(不为空)
	 * @param good_name
	 *            // 商品名(不为空)
	 * @param good_det
	 *            // 商品详情(不为空)
	 * @param good_price
	 *            // 商品价格(不为空)
	 * @param l           
	 */
	public static void toRecharge(Activity mAc, String good_name, String good_det,
			String good_price, OnPayListener l){
		String order = AliPayBuilder.createAliPayOrder(getOutTradeNo(), good_name, good_det, good_price).toSign();
		pay(mAc, order, l);
	}
	
}
