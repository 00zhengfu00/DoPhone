package com.example.pinyinlib;
/***
 * ����ӿڣ�����V value����K key
 *
 * @param <K>
 * @param <V>
 */
public interface KeySort<K, V> {
	public K getKey(V v);
}
