package com.example.pinyinlib;



import net.sourceforge.pinyin4j.PinyinHelper;

public class AssortPinyinList {

	private HashList<String, PinyingBean> hashList = new HashList<String, PinyingBean>(
			new KeySort<String, PinyingBean>() {
		
				@Override
				public String getKey(PinyingBean v) {
					// TODO Auto-generated method stub
					return getFirstChar(v.getName());
				}
			});

	// ����ַ���������ĸ ���ַ� ת����ƴ��
	public String getFirstChar(String value) {
		// ���ַ�
		char firstChar = value.charAt(0);
		// ����ĸ����
		String first = null;
		// �Ƿ��ǷǺ���
		String[] print = PinyinHelper.toHanyuPinyinStringArray(firstChar);

		if (print == null) {

			// ��Сд��ĸ�ĳɴ�д
			if ((firstChar >= 97 && firstChar <= 122)) {
				firstChar -= 32;
			}
			if (firstChar >= 65 && firstChar <= 90) {
				first = String.valueOf((char) firstChar);
			} else {
				// ��Ϊ���ַ�Ϊ���ֻ��������ַ�
				first = "#";
			}
		} else {
			// ��������� �����д��ĸ
			first = String.valueOf((char) (print[0].charAt(0) - 32));
		}
		if (first == null) {
			first = "?";
		}
		return first;
	}

	public HashList<String, PinyingBean> getHashList() {

		return hashList;
	}

}
