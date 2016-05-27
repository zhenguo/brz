package com.brz.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.brz.basic.Basic;
import com.brz.mx5.R;
import com.brz.programme.ProgrammeContext;
import com.brz.programme.ProgrammeDefine;
import com.brz.view.ImagePlayer;
import com.brz.view.VideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by macro on 16/4/14.
 */
public class ProgrammePresenter implements ProgrammeContract.Presenter {
	private Logger mLogger = Logger.getLogger(ProgrammePresenter.class.getSimpleName());
	private Context mContext;
	private ProgrammeContract.View mProgrammeView;
	private ProgrammeContext mProgrammeContext;

	public ProgrammePresenter(Context context, @NonNull ProgrammeContext programmeContext,
			@NonNull ProgrammeContract.View view) {
		mContext = context;
		mProgrammeView = view;
		mProgrammeView.setPresenter(this);
		mProgrammeContext = programmeContext;

		inflateScreen(mProgrammeContext);
	}

	private String getFileSuffix(String fileName) {
		return fileName != null ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
	}

	private String getFilePath(String resourceType, String fileSigna, String suffix) {
		String filePath = null;
		switch (resourceType) {
		case ProgrammeDefine.BACKGROUND_REGION:
			String builder = Basic.RESOURCE_PATH + "BG/" + fileSigna + "." + suffix;
			filePath = builder.trim();
			break;
		case ProgrammeDefine.VIDEO_REGION:
			filePath = Basic.RESOURCE_PATH + "VIDEO/" + fileSigna + "." + suffix;
			break;
		case ProgrammeDefine.PICTURE_REGION:
			filePath = Basic.RESOURCE_PATH + "IMAGE/" + fileSigna + "." + suffix;
			break;
		default:
			break;
		}

		return filePath;
	}

	private void inflateScreen(ProgrammeContext programmeContext) {
		List<ProgrammeContext.ContentItem> list = programmeContext.getContent();
		ProgrammeContext.ContentItem item = null;

		for (int i = 0; i < list.size(); i++) {
			item = list.get(i);
			ProgrammeContext.Region region = item.getRegion();
			List<ProgrammeContext.Item> items;
			List<String> fileNames = new ArrayList<>();

			switch (region.getType()) {
			case ProgrammeDefine.BACKGROUND_REGION:
				mLogger.info("Add BACKGROUND_REGION");
				items = region.getItem();
				String suffix = getFileSuffix(items.get(0).getUrl());
				inflateBg(getFilePath(ProgrammeDefine.BACKGROUND_REGION, items.get(0)
						.getFileSigna(), suffix));
				break;
			case ProgrammeDefine.VIDEO_REGION:
				mLogger.info("Add VIDEO_REGION");
				items = region.getItem();
				fileNames.clear();
				for (ProgrammeContext.Item item1 : items) {
					fileNames.add(getFilePath(ProgrammeDefine.VIDEO_REGION, item1.getFileSigna(),
							getFileSuffix(item1.getSrc())));
				}
				ProgrammeContext.Coordinate coordinate = new ProgrammeContext.Coordinate(
						Float.parseFloat(region.getLeft()) * Basic.SCREEN_WIDTH / 100,
						Float.parseFloat(region.getWidth()) * Basic.SCREEN_WIDTH / 100,
						Float.parseFloat(region.getTop()) * Basic.SCREEN_HEIGHT / 100,
						Float.parseFloat(region.getHeight()) * Basic.SCREEN_HEIGHT / 100);
				inflateVideo(coordinate, fileNames);

				break;
			case ProgrammeDefine.PICTURE_REGION:
				mLogger.info("Add PICTURE_REGION");
				items = region.getItem();
				List<ImagePlayer.ImageOptions> imageOptionsList = new ArrayList<>();
				for (ProgrammeContext.Item item1 : items) {
					ImagePlayer.ImageOptions options = new ImagePlayer.ImageOptions();
					options.setFilePath(getFilePath(ProgrammeDefine.PICTURE_REGION,
							item1.getFileSigna(), getFileSuffix(item1.getSrc())));
					options.setTransmode(item1.getTransmode());
					options.setDuration(Integer.parseInt(item1.getDuration()));
					options.setTransittime(Double.parseDouble(item1.getTransittime()));
					imageOptionsList.add(options);
				}
				coordinate = new ProgrammeContext.Coordinate(Float.parseFloat(region.getLeft())
						* Basic.SCREEN_WIDTH / 100, Float.parseFloat(region.getWidth())
						* Basic.SCREEN_WIDTH / 100, Float.parseFloat(region.getTop())
						* Basic.SCREEN_HEIGHT / 100, Float.parseFloat(region.getHeight())
						* Basic.SCREEN_HEIGHT / 100);
				inflatePicture(coordinate, imageOptionsList);
				break;
			case ProgrammeDefine.TEXT_REGION:
				break;
			case ProgrammeDefine.WEATHER_REGION:
				break;
			case ProgrammeDefine.DATE_REGION:
				break;
			case ProgrammeDefine.TIME_REGION:
				break;
			default:
				break;
			}
		}
	}

	private void inflateBg(String url) {
		mProgrammeView.setBackground(url);
	}

	private void inflateVideo(ProgrammeContext.Coordinate coordinate, List<String> videoFiles) {
		VideoPlayer player = (VideoPlayer) View.inflate(mContext, R.layout.view_videoview, null);
		mProgrammeView.addView(player, coordinate);
		player.setVideoList(videoFiles);
	}

	private void inflatePicture(ProgrammeContext.Coordinate coordinate,
			List<ImagePlayer.ImageOptions> pictures) {
		ImagePlayer player = (ImagePlayer) View.inflate(mContext, R.layout.view_imageview, null);
		player.setImages(pictures);
		mProgrammeView.addView(player, coordinate);
	}
}
