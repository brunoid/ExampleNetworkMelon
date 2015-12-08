package com.example.examplenetworkmelon;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageRequest extends NetworkRequest<Bitmap> {

	String url;
	int width, height;
	ImageView imageView;
	ImageLoader mLoader;

	public ImageRequest(String url) {
		this(url, 0, 0);
	}

	public ImageRequest(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}

	public ImageRequest(ImageLoader loader, String url) {
		mLoader = loader;
		this.url = url;
	}
	
	public String getUrlText() {
		return url;
	}
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	@Override
	public void cancel() {
		super.cancel();
		mLoader.processCancel(this);
	}
	
	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(url);
	}

	@Override
	public Bitmap parse(InputStream is) throws IOException {
		Bitmap bm = Utils.getBitmap(is, width, height);
		return bm;
	}

}
