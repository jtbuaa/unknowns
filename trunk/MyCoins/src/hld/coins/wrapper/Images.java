package hld.coins.wrapper;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public abstract class Images {
	/**
	 * ��һ��ͼƬ�����������ֳ�����֡
	 * @param image
	 * @param col
	 * @param row
	 * @return
	 */
	public static Images createImages(Bitmap image, int col, int row) {
		return new SingleImages(image, col, row);
	}
	
	/**
	 * ��ÿ��ͼƬ����һ֡
	 * @param image
	 * @param images
	 * @return
	 */
	public static Images createImages(Bitmap image, Bitmap... images) {
		return new MultiImages(image, images);
	}
	
	/**
	 * ����ͼƬ����ֻ����֡ͼƬ�������
	 * @param graphics
	 * @param x
	 * @param y
	 * @param flag false��ʾ���Ƶ�һ֡ͼƬ��true��ʾ���Ƶڶ�֡ͼƬ
	 */
	public void draw(Graphics graphics, Point point, boolean flag) {
		draw(graphics, point.x, point.y, flag?1:0);
	}
	
	/**
	 * ����ָ��������ͼƬ��������0��ʼ
	 * @param graphics
	 * @param x
	 * @param y
	 * @param index
	 */
	public abstract void draw(Graphics graphics, int x, int y, int index);
	
	/**
	 * ��ָ�������ڻ���ͼƬ����ֻ����֡ͼƬ�������
	 * @param graphics
	 * @param rect
	 * @param flag false��ʾ���Ƶ�һ֡ͼƬ��true��ʾ���Ƶڶ�֡ͼƬ
	 */
	public void draw(Graphics graphics, Rect rect, boolean flag) {
		draw(graphics, rect, flag?1:0);
	}
	
	/**
	 * ��ָ�������ڻ���ָ��������ͼƬ��������0��ʼ
	 * @param graphics
	 * @param rect
	 * @param index
	 */
	public abstract void draw(Graphics graphics, Rect rect, int index);
	
	/**
	 * ��ȡ��֡�Ŀ��
	 * @return
	 */
	public int getWidth() {
		return getWidth(0);
	}
	
	/**
	 * ��ȡ��֡�ĸ߶�
	 * @return
	 */
	public int getHeight() {
		return getHeight(0);
	}
	
	/**
	 * ��ȡָ��֡�Ŀ�ȣ�������0��ʼ
	 * @param index
	 * @return
	 */
	public abstract int getWidth(int index);
	
	/**
	 * ��ȡָ��֡�ĸ߶ȣ�������0��ʼ
	 * @param index
	 * @return
	 */
	public abstract int getHeight(int index);
}

class SingleImages extends Images {
	private Bitmap sourceImage;
	
	private int framesWidth;
	
	private int framesHeight;
	
	private int[] framesX;
	
	private int[] framesY;
	
	public SingleImages(Bitmap image, int col, int row) {
		sourceImage = image;
		int imageWidth = sourceImage.getWidth();
		int imageHeight = sourceImage.getHeight();
		framesWidth = imageWidth / col;
		if(imageWidth%col!=0) framesWidth++;
		framesHeight = imageHeight / row;
		if(imageHeight%row!=0) framesHeight++;
		int count = row * col;
		framesX = new int[count];
		framesY = new int[count];
		int i = 0;
		for(int y = 0; y < imageHeight; y += framesHeight) {
			for(int x = 0; x < imageWidth; x += framesWidth) {
				framesX[i] = x;
				framesY[i] = y;
				i++;
			}
		}
	}
	
	@Override
	public void draw(Graphics graphics, int x, int y, int index) {
		graphics.drawImage(x, y, sourceImage, framesX[index], framesY[index], framesWidth, framesHeight);
	}
	
	@Override
	public int getWidth(int index) {
		return framesWidth;
	}
	
	@Override
	public int getHeight(int index) {
		return framesHeight;
	}
	
	@Override
	public void draw(Graphics graphics, Rect rect, int index) {
		graphics.drawImage(rect.left, rect.top, sourceImage, framesX[index], framesY[index], framesWidth, framesHeight);
	}
}

class MultiImages extends Images {
	private Bitmap[] sourceImage;
	
	private int[] imageWidth;
	
	private int[] imageHeight;
	
	public MultiImages(Bitmap image, Bitmap... images) {
		sourceImage = new Bitmap[images.length + 1];
		imageWidth = new int[sourceImage.length];
		imageHeight = new int[sourceImage.length];
		sourceImage[0] = image;
		imageWidth[0] = image.getWidth();
		imageHeight[0] = image.getHeight();
		for(int i = 1; i < sourceImage.length; i++) {
			image = images[i - 1];
			sourceImage[i] = image;
			imageWidth[i] = image.getWidth();
			imageHeight[i] = image.getHeight();
		}
	}
	
	@Override
	public void draw(Graphics graphics, int x, int y, int index) {
		graphics.drawImage(sourceImage[index], x, y);
	}
	
	@Override
	public int getWidth(int index) {
		return imageWidth[index];
	}
	
	@Override
	public int getHeight(int index) {
		return imageHeight[index];
	}
	
	@Override
	public void draw(Graphics graphics, Rect rect, int index) {
		graphics.drawImage(rect.left, rect.top, sourceImage[index], 0, 0, imageWidth[index], imageHeight[index]);
	}
}
