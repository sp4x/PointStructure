package points;

import java.awt.geom.Rectangle2D;

public class Region  {

	public final Integer minX;
	public final Integer minY;
	public final Integer maxX;
	public final Integer maxY;
	

	public Region() {
		minX = Integer.MIN_VALUE;
		minY = Integer.MIN_VALUE;
		maxX = Integer.MAX_VALUE;
		maxY = Integer.MAX_VALUE;
	}

	Region(Integer minX, Integer minY, Integer maxX, Integer maxY) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public Region[] splitOnXAxis(Integer splitValue) {
		return new Region[] { new Region(minX, minY, splitValue, maxY), new Region(splitValue, minY, maxX, maxY) };
	}
	
	public Region[] splitOnYAxis(Integer splitValue) {
		return new Region[] { new Region(minX, minY, maxX, splitValue), new Region(minX, splitValue, maxX, maxY) };
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (minX>Integer.MIN_VALUE) {
			builder.append(minX);
		}
		builder.append(",");
		if (maxX<Integer.MAX_VALUE) {
			builder.append(maxX);
		}
		builder.append("}");
		builder.append("{");
		if (minY>Integer.MIN_VALUE) {
			builder.append(minY);
		}
		builder.append(",");
		if (maxY<Integer.MAX_VALUE) {
			builder.append(maxY);
		}
		builder.append("}");
		return builder.toString();
	}

}
