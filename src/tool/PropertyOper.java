package tool;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

//����Properties�ೣ�õĲ���
public class PropertyOper {
	// ����Key��ȡValue
	public static String GetValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			//System.out.println(key + " = " + value);
			return value;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡProperties��ȫ����Ϣ
	public static Map<String, String> GetAllProperties(String filePath) throws IOException {
		Properties pps = new Properties();
		InputStream in = PropertyOper.class.getClassLoader().getResourceAsStream("msg" + File.separator +filePath );
		pps.load(in);
		Enumeration<?> en = pps.propertyNames(); // �õ������ļ�������
		Map<String, String> result = new HashMap<>();
		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			result.put(strKey, strValue);
			//System.out.println(strKey + "=" + strValue);
		}
		return result;
	}

	// д��Properties��Ϣ
	public static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();

		InputStream in = new FileInputStream(filePath);
		// ���������ж�ȡ�����б�����Ԫ�ضԣ�
		pps.load(in);
		// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
		// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
		// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
		pps.store(out, "Update " + pKey + " name");
	}

	// д��Properties��Ϣͨ��HashMap
	public static void WriteProperties(String filePath, Map<String, String> map) throws IOException {
		Properties pps = new Properties();

		InputStream in = new FileInputStream(filePath);
		// ���������ж�ȡ�����б�����Ԫ�ضԣ�
		pps.load(in);
//		String filename = "anime/"+filePath;  
//		System.out.println(filename);
//        pps.load(PropertyOper.class.getClassLoader().getResourceAsStream(filename));  
		// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
		// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
		OutputStream out = new FileOutputStream(filePath);
		for (Entry<String, String> entry : map.entrySet()) {
			pps.setProperty(entry.getKey(), entry.getValue());

		}
		// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
		// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
		pps.store(out, "Update " + "" + " name");		
		GetAllProperties(filePath);

	}
	// public static void main(String [] args) throws IOException{
	// String value = GetValueByKey("Test.properties", "name");
	// System.out.println(value);
	// GetAllProperties("Test.properties");
	// WriteProperties("Test.properties","long", "212");
	// }
}