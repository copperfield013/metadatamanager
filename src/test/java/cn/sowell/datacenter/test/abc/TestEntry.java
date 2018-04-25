package cn.sowell.datacenter.test.abc;

import cn.sowell.copframe.jedica.CommonRedisEntry;

@SuppressWarnings("serial")
class TestEntry extends CommonRedisEntry<JedicaValue>{
	private String key;
	public TestEntry(String key, JedicaValue value) {
		this.key = key;
		this.value = value;
	}
	@Override
	public String getKey() {
		return key;
	}
}
