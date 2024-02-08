package com.kaen.service;

import java.util.Date;

import com.loserstar.utils.date.LoserStarDateUtils;

public class TimerService implements Runnable {

	@Override
	public void run() {
		try {
			System.out.println("-----------定时任务调用-------------"+LoserStarDateUtils.format(new Date()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
