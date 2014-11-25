package util;

import java.io.Serializable;

public class PageBean implements Serializable{
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("@PageBean{");
		sb.append("\n page:").append(page);
		sb.append("}");
		return sb.toString();
	}
}
