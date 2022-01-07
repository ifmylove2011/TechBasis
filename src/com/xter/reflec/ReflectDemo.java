package com.xter.reflec;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/10/21
 * 描述:
 */
public class ReflectDemo {
	public static void main(String[] args) {
		try {
			KeepSampleSdkHolder holder = (KeepSampleSdkHolder) ReflectUtils.newObject("com.xter.reflec.KeepSampleSdkHolder", new String[]{}, new Object[]{});
//			KeepSampleSdkHolder holder = (KeepSampleSdkHolder) ReflectUtils.newObjectNotParam("com.xter.reflec.KeepSampleSdkHolder");
			ReflectUtils.callMethod(holder,"openSerialPort",new String[]{"java.lang.String","int"},new Object[]{"usb",5});
			System.out.println(Class.forName("int").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
