package com.peoit.android.studentuga.alipays;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.peoit.android.studentuga.uitl.MyLogger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AliPayBase {
	// 商户PID
	protected static final String PARTNER = "2088121043034974";

	// 商户收款账号
	protected static final String SELLER = "2377386905@qq.com";

	// 商户私钥	
//	public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALQdjnLdBOvQ488KwtGGXvaws6oQ0YinlB3V64dvaER6ETDuvx/Qg8s/CqjRHCyGwzlC3cwlt6WjCV1BUjY/1c+uZyOi747Rvxk6DpCCYcl/aluezp3mPPM1Apb2T3rRjq/LD51oHWC7mwEDJny7jTr31wXn7LjWfkpmoWoM98oxAgMBAAECgYAtMW1YrNRbRyKiBJU1dX3GcDfkaCvrGgE0K0TZyr5i0C4YFQ+nr+4hxUOrcCydj4LUj06PtrcJvIrQ917ldcbz1Ya5ztbESAefjmNdcgd86LBQpglDsmMyhUe9Fqm9VWUwyVlqmOfS9pYbSus+z/CCmFDamxRt4qEYx0jFsn8HMQJBANyKjsPDattVwtWGqPDydL4uS3mEo8lOCYpfaDS/KNcgEAjKrrK2rLAT3d9C56GIyJYCv5afiatmjqLHEZgDjoMCQQDRExRxUNUVLOC6y1kBgocBav7MJeUCHHtQLvJArdZR0plOKPkiVuNlfoO1QSZ1AdqswD1p6fhSRxHBtiZ2v6Y7AkAQjZnPmcBQfCxmiHfvtdMLX0As+8arWl8e8rBInTx8gRyS/FuGcG2fva3+jvAB0Nl1YPluXcUgh08XaqeoaEPvAkBjky/ATFw/6pDZxjGM64q7HSdfOYkZeVEtvj44mdKiQ6gqNo95UGKbGydFc1MKlSh98E0PnZRcM2b8mHE3S02zAkAvt3MGPt/ENzhXcFo7HEODGL9Q4n6gV+CWOC9tVbuztPuT/9Is+MXfNmO3aX27ahXI5cj9BdU6oSu9eSFKjdfW";
	public static final String RSA_PRIVATE = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBANejWOIZuoJL7f3vJ6W3kFN1tS5IHGahtMNXqCbKoGBYJv48SgNPtC6eKgz8z2wtPC9baWad1SzItGd81Zv/Dfibo7MMVqsdqOEBQxmSQXl4qcPH+PbNUuyIlSrAZeIZs3N6+RzMcbX1xsdQw7EdBsAQUQNTiyzdew1dvQML77pJAgMBAAECgYEAgWYNLUihKhUkUHBVy1TW764/tfEh0diRn3MXfYe0K3xakc9d3pTSm/LfawG9+r0+U3C4VBK8XG5oC33WtRCdsYqsV9COBKUGu1sfuXx+wRdVWxnOCvleavfe85WleNi8ux+Rg+b7nFpFDN/4l2sh6tWlgpgAuoNjJTVDYtuF+/0CQQDwh733m5C6gB4V3Fo6U9S2LUGvD6LyxDjwV1O7LGeI/YmcqvTx0yG8HDj2MIzyOiRL1eeLcoh+1+Ty59tC/SATAkEA5YHDWvuUHyOGd28GCROucf/tFjcu0YGzKBvqdDYmMalK4LqsQSJ0B6n5zMMEKw0KOVMXybcMRh/sCfDnm7AfswJBANqNrNo4+1fLJAsfDski9QuXsbA82AYYr9gKsWVIoscCATFe0XSOMH/5cPSaBufuVp6lvV+i8U24JCtm639B+wsCQQDcN1rf3i4OguPvjdRYuU3VjSBNBL4jwTPK9J5Oy/ZyCGMUp4nJJen3wJ00YJuLQ4Pf5V8Z6k4Yq2CtWT6jIKgJAkEAovo/5NG+xhN+nM0mz5rXdd/ql0IufkGpAi/qnLk55cIlxeOavny6yAWsxFbl5gLXCgIbEomHUYXn9zf7siitfQ==";

	protected static final int SDK_PAY_FLAG = 0x00000001;
	protected static final int SDK_CHECK_FLAG = 0x000000002;

	/**
	 * 及时支付，及时到账 ()
	 * 
	 * @param order 订单
	 * @param l
	 */
	protected static void pay(final Activity mAc, String order, final OnPayListener l) {
		if (l != null) {
			final Handler payhandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg != null) {
						PayResult payResult = new PayResult((String) msg.obj);
						MyLogger.i((String) msg.obj);
						// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
						String resultInfo = payResult.getResult();
						String resultStatus = payResult.getResultStatus();
						// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
						if (TextUtils.equals(resultStatus, "9000")) {
							l.onSuccess(resultInfo);
						} else {
							// 判断resultStatus 为非“9000”则代表可能支付失败
							// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
							if (TextUtils.equals(resultStatus, "8000")) {
								l.on8000();
							} else {
								l.onFailed("支付失败");
							}
						}
					} else {
						l.onFailed("支付失败");
					}
				}
			};
			// 对订单做RSA 签名
			String sign = SignUtils.sign(order, RSA_PRIVATE);

			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = order + "&sign=\"" + sign + "\"&"
					+ getSignType();
			Runnable payRunnable = new Runnable() {
				@Override
				public void run() {
					try {
						// 构造PayTask 对象
						PayTask alipay = new PayTask(mAc);
						// 调用支付接口，获取支付结果
						String result = alipay.pay(payInfo);
						Message msg = new Message();
						msg.what = SDK_PAY_FLAG;
						msg.obj = result;
						payhandler.sendMessage(msg);
					} catch (Exception e) {
						payhandler.sendMessage(new Message());
					}
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	}

	/**
	 * 获取签名方式
	 * 
	 * @return
	 */
	public static String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 * @param l
	 */
	public static void check(final Activity mAc, final OnCheckListener l) {
		final Handler checkHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				int waht = msg.what;
				if (waht == SDK_CHECK_FLAG && l != null) {
					l.onCheck((boolean) msg.obj);
				}
			}
		};
		Runnable checkRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(mAc);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();
				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				checkHandler.sendMessage(msg);
			}
		};
		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();
	}

	/**
	 * get the sdk version. 
	 * 获取SDK版本号
	 */
	public String getSDKVersion(Activity mAc) {
		PayTask payTask = new PayTask(mAc);
		String version = payTask.getVersion();
		// Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
		return version;
	}
	
	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		MyLogger.i(key);
		return key;
	}

	public interface OnCheckListener {
		void onCheck(boolean isCheck);
	}

	public interface OnPayListener {
		void onSuccess(String res);

		void onFailed(String msg);

		void on8000();
	}
}
